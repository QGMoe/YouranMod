package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChlorineProtectionTest {
    @Test
    void hazmatHelmetOrHazardSuit() {
        assertTrue(ChlorineProtection.isProtected("ic2:hazmat_helmet", "minecraft:air"));
        assertTrue(ChlorineProtection.isProtected("minecraft:air", "scp:hazard_suit"));
        assertTrue(ChlorineProtection.isProtected("ic2:hazmat_helmet", "scp:hazard_suit"));
    }

    @Test
    void wrongSlotDoesNotProtect() {
        // 1.3.4 只看头盔栏的 IC2 防化头盔与胸甲栏的 SCP 防护服
        assertFalse(ChlorineProtection.isProtected("scp:hazard_suit", "ic2:hazmat_helmet"));
        assertFalse(ChlorineProtection.isProtected("minecraft:air", "minecraft:air"));
        assertFalse(ChlorineProtection.isProtected("ic2:hazmat_chestplate", "minecraft:diamond_chestplate"));
    }
}
