package moe.qingu.youran.logic;

import java.util.Random;

/** 两端都含的整数范围里取随机数 */
public final class IntRange {
    private IntRange() {
    }

    /**
     * [min, max] 里取一个；与 1.3.4 的 {@code random.nextInt(max - min + 1) + min} 相同（只调用一次 nextInt）。
     * min &gt; max 时两者互换。
     */
    public static int pick(Random random, int min, int max) {
        if (min > max) {
            int t = min;
            min = max;
            max = t;
        }
        return random.nextInt(max - min + 1) + min;
    }
}
