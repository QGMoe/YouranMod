package moe.qingu.youran.logic;

import java.util.List;
import java.util.OptionalInt;

/**
 * D 级人员的定时回血：世界时间 → 瞬间治疗等级（1.3.4：23000 → 100，6000 → 2）。
 * 照 1.3.4 与世界的总时间（不取余）直接比较；同一时刻有多项时用第一项（1.3.4 是 if / else if）。
 */
public final class HealSchedule {
    /** 一项 */
    public static final class Point {
        public final long worldTime;
        public final int amplifier;

        Point(long worldTime, int amplifier) {
            this.worldTime = worldTime;
            this.amplifier = amplifier;
        }
    }

    private final List<Point> points;

    private HealSchedule(List<Point> points) {
        this.points = points;
    }

    /** 从配置的“时间=等级”列表解析；无效项在结果的 errors 里 */
    public static KeyValues.Result<Point> parse(String[] lines) {
        return KeyValues.parse(lines, (k, v) -> new Point(KeyValues.parseLong(k), KeyValues.parseInt(v, 0)));
    }

    public static HealSchedule of(List<Point> points) {
        return new HealSchedule(points);
    }

    /** 这个世界时间要回血时返回等级 */
    public OptionalInt amplifierAt(long worldTime) {
        for (Point p : points) {
            if (p.worldTime == worldTime) {
                return OptionalInt.of(p.amplifier);
            }
        }
        return OptionalInt.empty();
    }
}
