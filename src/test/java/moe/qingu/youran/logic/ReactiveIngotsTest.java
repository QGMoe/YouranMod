package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReactiveIngotsTest {
    /** 默认的表（同 1.3.4） */
    private static float strength(List<String> names) {
        return ReactiveIngots.explosionStrength(names, SharedSettings.DEFAULTS.reactiveIngots);
    }

    @Test
    void strengthsAsIn134() {
        assertEquals(2.0f, strength(Collections.singletonList("ingotSodium")));
        assertEquals(2.0f, strength(Collections.singletonList("ingotMagnesium")));
        assertEquals(1.5f, strength(Collections.singletonList("ingotPotassium")));
        assertEquals(1.5f, strength(Collections.singletonList("ingotCalcium")));
        assertEquals(1.0f, strength(Collections.singletonList("ingotAluminum")));
    }

    @Test
    void noOreNameNoExplosion() {
        assertEquals(-1, strength(Collections.emptyList()));
        assertEquals(-1, strength(Collections.singletonList("ingotIron")));
        assertEquals(-1, strength(Collections.singletonList("ingotsodium")), "区分大小写");
    }

    @Test
    void onlyFirstOreNameCounts() {
        // 1.3.4 的循环在第一个名字不匹配时就 return 了
        assertEquals(-1, strength(Arrays.asList("ingotAluminium", "ingotAluminum")));
        assertEquals(1.0f, strength(Arrays.asList("ingotAluminum", "ingotAluminium")));
    }
}
