package moe.qingu.youran.logic;

import moe.qingu.youran.config.TeleportConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TruncatedAreaTest {
    /** 默认配置里的回收站岛屿解锁区域（1.3.4 写死的范围） */
    private final TruncatedArea recycle = TeleportPoints.parseUnlockAreas(TeleportConfig.unlockAreas,
            TeleportPoints.parsePoints(TeleportConfig.points).values).values.get(0).area;

    @Test
    void truncatesTowardZeroLike134() {
        // (int) -275.9 = -275（floor 会是 -276）
        assertTrue(recycle.contains(-275.9, 67, 160));
        assertFalse(recycle.contains(-276.0, 67, 160));
        // (int) -267.5 = -267，超出 -268
        assertFalse(recycle.contains(-267.5, 67, 160));
        assertTrue(recycle.contains(-268.9, 67, 160));
        assertTrue(recycle.contains(-270, 67, 159.0));
        assertTrue(recycle.contains(-270, 67, 165.99));
        assertFalse(recycle.contains(-270, 67, 166.0));
        assertFalse(recycle.contains(-270, 66.99, 160));
        assertTrue(recycle.contains(-270, 255, 160), "y 只有下限");
    }
}
