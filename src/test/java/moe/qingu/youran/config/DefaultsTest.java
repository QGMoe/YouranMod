package moe.qingu.youran.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** 配置的默认值等于 1.3.4 写死的值 */
class DefaultsTest {
    @Test
    void teleports() {
        assertEquals(-258, TeleportConfig.mainCityX);
        assertEquals(70, TeleportConfig.mainCityY);
        assertEquals(118, TeleportConfig.mainCityZ);
        assertEquals(-291, TeleportConfig.backX);
        assertEquals(64, TeleportConfig.backY);
        assertEquals(108, TeleportConfig.backZ);
        assertTrue(TeleportConfig.backToWorldSpawn);
        assertEquals(0, TeleportConfig.voidRescueY);
    }

    @Test
    void links() {
        assertEquals("https://youran.qingu.moe/", LinkConfig.websiteUrl);
        assertEquals("youran.qingu.moe", LinkConfig.websiteText);
        assertEquals("https://docs.youran.qingu.moe/", LinkConfig.manualUrl);
        assertEquals("docs.youran.qingu.moe", LinkConfig.manualText);
        assertEquals("https://play.mcmod.cn/sv20185890.html", LinkConfig.mcmodUrl);
        assertEquals("play.mcmod.cn/sv20185890.html", LinkConfig.mcmodText);
    }

    @Test
    void worldGen() {
        assertOre(WorldGenConfig.hardIce, new int[]{1}, 0, 99, 3, 7);
        assertArrayEquals(new String[]{"minecraft:sky"}, WorldGenConfig.hardIce.biomes);
        assertOre(WorldGenConfig.primogemOre, new int[]{0, -1, 1}, 1, 11, 1, 3);
        assertOre(WorldGenConfig.sphaleriteOre, new int[]{0}, 1, 39, 3, 5);
        assertOre(WorldGenConfig.hydrargyrum, new int[]{0}, 1, 40, 1, 4);
        assertArrayEquals(new int[]{7}, WorldGenConfig.mineralWaterLake.dimensions);
        assertEquals(0.002, WorldGenConfig.mineralWaterLake.chance);
    }

    private static void assertOre(WorldGenConfig.Ore ore, int[] dims, int minY, int maxY, int perChunk, int vein) {
        assertArrayEquals(dims, ore.dimensions);
        assertEquals(minY, ore.minY);
        assertEquals(maxY, ore.maxY);
        assertEquals(perChunk, ore.perChunk);
        assertEquals(vein, ore.veinSize);
    }

    @Test
    void others() {
        assertEquals("scp:dpersonnel", DPersonnelConfig.entity);
        assertEquals(100, DPersonnelConfig.saveDAmplifier);
        assertEquals(0.3, ItemConfig.swordXpChance);
        assertEquals(3, ItemConfig.swordXpMax);
        assertEquals(0.1, ItemConfig.swordHealChance);
        assertEquals(10, ItemConfig.swordHealRadius);
        assertEquals(1, ItemConfig.swordHealAmplifier);
        assertEquals(5, ItemConfig.redBloodGemSelfDamage);
    }
}
