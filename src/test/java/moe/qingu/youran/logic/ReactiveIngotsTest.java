package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReactiveIngotsTest {
    @Test
    void strengthsAsIn134() {
        assertEquals(2.0f, ReactiveIngots.explosionStrength(Collections.singletonList("ingotSodium")));
        assertEquals(2.0f, ReactiveIngots.explosionStrength(Collections.singletonList("ingotMagnesium")));
        assertEquals(1.5f, ReactiveIngots.explosionStrength(Collections.singletonList("ingotPotassium")));
        assertEquals(1.5f, ReactiveIngots.explosionStrength(Collections.singletonList("ingotCalcium")));
        assertEquals(1.0f, ReactiveIngots.explosionStrength(Collections.singletonList("ingotAluminum")));
    }

    @Test
    void noOreNameNoExplosion() {
        assertEquals(-1, ReactiveIngots.explosionStrength(Collections.emptyList()));
        assertEquals(-1, ReactiveIngots.explosionStrength(Collections.singletonList("ingotIron")));
        assertEquals(-1, ReactiveIngots.explosionStrength(Collections.singletonList("ingotsodium")), "区分大小写");
    }

    @Test
    void onlyFirstOreNameCounts() {
        // 1.3.4 的循环在第一个名字不匹配时就 return 了
        assertEquals(-1, ReactiveIngots.explosionStrength(Arrays.asList("ingotAluminium", "ingotAluminum")));
        assertEquals(1.0f, ReactiveIngots.explosionStrength(Arrays.asList("ingotAluminum", "ingotAluminium")));
    }
}
