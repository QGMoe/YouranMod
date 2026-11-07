package moe.qingu.youran.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * /youran tp 的传送点与解锁区域（服主 11-07：配置化，默认值等于 1.3.4 的 fw）。
 * <p>
 * 传送点一行：{@code 名字;维度;x;y;z;朝向;列表里的说明;传送提示里的名字;要先完成的进度}，
 * 朝向写“yaw,pitch”或留空（不改朝向），进度写注册名或留空（不用解锁）。
 * <p>
 * 解锁区域一行：{@code 维度;最小x;最大x;最小z;最大z;最小y;给的进度;解锁的传送点名;提示里的叫法}，
 * 玩家走进这个范围（坐标向 0 取整，两端都含，y 只有下限）时给这个进度并提示“可通过 /youran tp 名字 来直接到达<叫法>”。
 */
public final class TeleportPoints {
    private TeleportPoints() {
    }

    /** 一个传送点 */
    public static final class Point {
        public final String name;
        public final int dimension;
        public final double x;
        public final double y;
        public final double z;
        /** 没写朝向时为 null */
        public final Float yaw;
        public final Float pitch;
        public final String description;
        public final String title;
        /** 不用解锁时为 null */
        public final String advancement;

        Point(String name, int dimension, double x, double y, double z, Float yaw, Float pitch, String description,
              String title, String advancement) {
            this.name = name;
            this.dimension = dimension;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.description = description;
            this.title = title;
            this.advancement = advancement;
        }
    }

    /** 一个解锁区域 */
    public static final class UnlockArea {
        public final int dimension;
        public final TruncatedArea area;
        public final String advancement;
        public final String pointName;
        public final String place;

        UnlockArea(int dimension, TruncatedArea area, String advancement, String pointName, String place) {
            this.dimension = dimension;
            this.area = area;
            this.advancement = advancement;
            this.pointName = pointName;
            this.place = place;
        }
    }

    /** 解析传送点；名字重复、是 help / list、有空白的跳过 */
    public static KeyValues.Result<Point> parsePoints(String[] lines) {
        List<Point> points = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        Set<String> names = new HashSet<>();
        for (String raw : lines) {
            String line = raw == null ? "" : raw.trim();
            try {
                String[] f = split(line, 9);
                String name = f[0];
                if (name.isEmpty() || !name.matches("\\S+")) {
                    throw new IllegalArgumentException("名字不能为空、不能有空白");
                }
                String lower = name.toLowerCase(Locale.ROOT);
                if (lower.equals("help") || lower.equals("list")) {
                    throw new IllegalArgumentException("名字不能是 help 或 list");
                }
                if (!names.add(name)) {
                    throw new IllegalArgumentException("名字重复");
                }
                Float yaw = null;
                Float pitch = null;
                if (!f[5].isEmpty()) {
                    String[] r = f[5].split(",", -1);
                    if (r.length != 2) {
                        throw new IllegalArgumentException("朝向要写成 yaw,pitch");
                    }
                    yaw = (float) number(r[0]);
                    pitch = (float) number(r[1]);
                }
                points.add(new Point(name, integer(f[1]), number(f[2]), number(f[3]), number(f[4]), yaw, pitch,
                        f[6], f[7], advancement(f[8])));
            } catch (IllegalArgumentException e) {
                errors.add("“" + line + "”：" + e.getMessage());
            }
        }
        return new KeyValues.Result<>(points, errors);
    }

    /** 解析解锁区域；解锁的传送点要在 points 里 */
    public static KeyValues.Result<UnlockArea> parseUnlockAreas(String[] lines, List<Point> points) {
        List<UnlockArea> areas = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        for (String raw : lines) {
            String line = raw == null ? "" : raw.trim();
            try {
                String[] f = split(line, 9);
                String advancement = advancement(f[6]);
                if (advancement == null) {
                    throw new IllegalArgumentException("要写给哪个进度");
                }
                if (find(points, f[7]) == null) {
                    throw new IllegalArgumentException("没有叫“" + f[7] + "”的传送点");
                }
                TruncatedArea area = new TruncatedArea(integer(f[1]), integer(f[2]), integer(f[3]), integer(f[4]), integer(f[5]));
                areas.add(new UnlockArea(integer(f[0]), area, advancement, f[7], f[8]));
            } catch (IllegalArgumentException e) {
                errors.add("“" + line + "”：" + e.getMessage());
            }
        }
        return new KeyValues.Result<>(areas, errors);
    }

    /** 按名字找（区分大小写，同 1.3.4 的子命令）；找不到返回 null */
    public static Point find(List<Point> points, String name) {
        for (Point p : points) {
            if (p.name.equals(name)) {
                return p;
            }
        }
        return null;
    }

    public static List<String> names(List<Point> points) {
        List<String> names = new ArrayList<>();
        for (Point p : points) {
            names.add(p.name);
        }
        return Collections.unmodifiableList(names);
    }

    private static String[] split(String line, int count) {
        String[] f = line.split(";", -1);
        if (f.length != count) {
            throw new IllegalArgumentException("要用分号分成 " + count + " 段，这里有 " + f.length + " 段");
        }
        for (int i = 0; i < f.length; i++) {
            f[i] = f[i].trim();
        }
        return f;
    }

    private static int integer(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("“" + s + "”不是整数");
        }
    }

    private static double number(String s) {
        try {
            double d = Double.parseDouble(s);
            if (Double.isNaN(d) || Double.isInfinite(d)) {
                throw new NumberFormatException();
            }
            return d;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("“" + s + "”不是数");
        }
    }

    private static String advancement(String s) {
        if (s.isEmpty()) {
            return null;
        }
        if (!s.matches("[a-z0-9_.-]+:[a-z0-9_./-]+")) {
            throw new IllegalArgumentException("“" + s + "”不是进度的注册名（如 youranmod:a_spcial_village）");
        }
        return s;
    }
}
