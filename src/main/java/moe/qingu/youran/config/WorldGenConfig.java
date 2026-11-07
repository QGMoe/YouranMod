package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/**
 * 世界生成（只影响之后新生成的区块）。矿石与矿泉水湖是两个独立的生成器（见 worldgen 包）。
 */
@Config(modid = ServerConfig.KEY, name = ServerConfig.FILE, category = "worldgen")
public final class WorldGenConfig {
    @Config.Comment("坚冰：末地（The End 群系）的石头、末地石里。")
    public static final Ore hardIce = new Ore(new int[]{1}, 0, 99, 3, 7, new String[]{"minecraft:sky"});

    @Config.Comment("奇怪的矿石：主世界、下界、末地的底层，可替换石头、末地石、下界岩、煤矿、铁矿、红石矿、沙砾、岩浆块、石英矿、泥土、基岩（含 y=0 那一层，服主特意写的）。")
    public static final Ore primogemOre = new Ore(new int[]{0, -1, 1}, 1, 11, 1, 3, new String[0]);

    @Config.Comment("闪锌矿：主世界的石头里。")
    public static final Ore sphaleriteOre = new Ore(new int[]{0}, 1, 39, 3, 5, new String[0]);

    @Config.Comment("汞（流体方块）：主世界的石头、泥土里（1.3.4 还能替换基岩，服主 11-07 去掉）。")
    public static final Ore hydrargyrum = new Ore(new int[]{0}, 1, 40, 1, 4, new String[0]);

    @Config.Comment({"矿泉水湖（与原版的湖相同的形状，16×8×16 以内）。",
            "湖整个落在正在生成的那个区块里，不会写进旁边已经生成的区块（1.3.4 会伸进旁边的区块，并且概率用的是与种子无关的随机数）。"})
    public static final Lake mineralWaterLake = new Lake();

    private WorldGenConfig() {
    }

    /** 一种矿石的生成规则。默认值都是 1.3.4 的。 */
    public static final class Ore {
        @Config.Comment("在哪些维度生成（维度 ID）。")
        @Config.RequiresMcRestart
        public int[] dimensions;

        @Config.Comment("矿脉中心的最低 y（含）。")
        @Config.RequiresMcRestart
        public int minY;

        @Config.Comment("矿脉中心的最高 y（含）。")
        @Config.RequiresMcRestart
        public int maxY;

        @Config.Comment("每个区块试几次（每次一条矿脉）。")
        @Config.RequiresMcRestart
        public int perChunk;

        @Config.Comment("每条矿脉最多多少块（原版 WorldGenMinable 的大小）。")
        @Config.RequiresMcRestart
        public int veinSize;

        @Config.Comment("只在这些群系生成（群系注册名，看区块角上 y=128 处的群系）；空 = 不限。")
        @Config.RequiresMcRestart
        public String[] biomes;

        Ore(int[] dimensions, int minY, int maxY, int perChunk, int veinSize, String[] biomes) {
            this.dimensions = dimensions;
            this.minY = minY;
            this.maxY = maxY;
            this.perChunk = perChunk;
            this.veinSize = veinSize;
            this.biomes = biomes;
        }
    }

    /** 矿泉水湖的生成规则。默认值都是 1.3.4 的。 */
    public static final class Lake {
        @Config.Comment("在哪些维度生成（维度 ID）。1.3.4：7（暮色森林）。")
        @Config.RequiresMcRestart
        public int[] dimensions = {7};

        @Config.Comment("每个区块生成的概率（0–1）。1.3.4：0.002（0.2%）。")
        @Config.RequiresMcRestart
        @Config.RangeDouble(min = 0, max = 1)
        public double chance = 0.002;

        Lake() {
        }
    }
}
