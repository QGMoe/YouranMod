package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChlorineProtectionTest {
    /** 默认的表（同 1.3.4） */
    private static boolean isProtected(String head, String chest) {
        return SharedSettings.DEFAULTS.protectsFromChlorine(head, chest);
    }

    @Test
    void configuredTables() {
        assertTrue(ChlorineProtection.isProtected("a:helmet", "minecraft:air", Arrays.asList("a:helmet"), Collections.emptyList()));
        assertFalse(ChlorineProtection.isProtected("ic2:hazmat_helmet", "scp:hazard_suit", Collections.emptyList(), Collections.emptyList()));
    }

    @Test
    void hazmatHelmetOrHazardSuit() {
        assertTrue(isProtected("ic2:hazmat_helmet", "minecraft:air"));
        assertTrue(isProtected("minecraft:air", "scp:hazard_suit"));
        assertTrue(isProtected("ic2:hazmat_helmet", "scp:hazard_suit"));
    }

    @Test
    void wrongSlotDoesNotProtect() {
        // 1.3.4 只看头盔栏的 IC2 防化头盔与胸甲栏的 SCP 防护服
        assertFalse(isProtected("scp:hazard_suit", "ic2:hazmat_helmet"));
        assertFalse(isProtected("minecraft:air", "minecraft:air"));
        assertFalse(isProtected("ic2:hazmat_chestplate", "minecraft:diamond_chestplate"));
    }
}
