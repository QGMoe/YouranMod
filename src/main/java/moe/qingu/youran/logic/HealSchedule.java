package moe.qingu.youran.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * D 级人员的定时回血：一天内的时间（0–23999）→ 瞬间治疗等级（默认 23000 → 100，6000 → 2，时刻与等级同 1.3.4）。
 * <p>
 * 服主 11-07 改：按一天内的时间（世界时间对 24000 取余），每天到点一次。1.3.4 拿总时间直接比，只有第一天对得上。
 * 判断方式是“从上次检查到这次检查之间经过了这个时刻”，所以睡觉、{@code /time add} 跳过去的那一段里的时刻也算到了，
 * 一次跳过好几天也只回一次；时间停住（关了日夜交替）不会反复触发；时间往回调（如 {@code /time set day}）不触发。
 * 同一时刻有多项时用第一项（1.3.4 是 if / else if）。
 */
public final class HealSchedule {
    public static final long DAY = 24000;

    /** 一项 */
    public static final class Point {
        /** 一天内的时间，0–23999 */
        public final long timeOfDay;
        public final int amplifier;

        Point(long timeOfDay, int amplifier) {
            this.timeOfDay = Math.floorMod(timeOfDay, DAY);
            this.amplifier = amplifier;
        }
    }

    private final List<Point> points;

    private HealSchedule(List<Point> points) {
        this.points = points;
    }

    /** 从配置的“时间=等级”列表解析（时间超出 0–23999 的按对 24000 取余算）；无效项在结果的 errors 里 */
    public static KeyValues.Result<Point> parse(String[] lines) {
        return KeyValues.parse(lines, (k, v) -> new Point(KeyValues.parseLong(k), KeyValues.parseInt(v, 0)));
    }

    public static HealSchedule of(List<Point> points) {
        return new HealSchedule(points);
    }

    /**
     * 世界时间从 previous 走到 current（previous 不含、current 含）之间到了的回血等级，按配置顺序；
     * current ≤ previous（时间没走或往回调）时为空。
     */
    public List<Integer> reached(long previous, long current) {
        List<Integer> result = new ArrayList<>();
        if (current <= previous) {
            return result;
        }
        List<Long> seen = new ArrayList<>();
        for (Point p : points) {
            if (seen.contains(p.timeOfDay)) {
                continue;
            }
            seen.add(p.timeOfDay);
            long next = previous + Math.floorMod(p.timeOfDay - previous - 1, DAY) + 1;
            if (next <= current) {
                result.add(p.amplifier);
            }
        }
        return result;
    }
}
