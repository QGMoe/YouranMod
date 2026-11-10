package moe.qingu.youran.logic;

/**
 * 矿泉水湖在区块里的位置。原版的湖（WorldGenLakes）先把起点减 8，在 16×8×16 的范围里画湖；
 * 起点取区块角 +8，这个范围就正好是这个区块，不会写进旁边的区块。
 */
public final class LakePlacement {
    /** WorldGenLakes 先把起点的 x、z 各减这么多 */
    static final int WORLD_GEN_LAKES_SHIFT = 8;
    /** WorldGenLakes 用的范围是 0–15 */
    static final int WORLD_GEN_LAKES_SIZE = 16;

    private LakePlacement() {
    }

    /** 传给 WorldGenLakes 的起点 x（或 z） */
    public static int origin(int chunkCoord) {
        return chunkCoord * 16 + WORLD_GEN_LAKES_SHIFT;
    }

    /** 湖实际占用的最小方块坐标 */
    public static int minBlock(int chunkCoord) {
        return origin(chunkCoord) - WORLD_GEN_LAKES_SHIFT;
    }

    /** 湖实际占用的最大方块坐标 */
    public static int maxBlock(int chunkCoord) {
        return minBlock(chunkCoord) + WORLD_GEN_LAKES_SIZE - 1;
    }
}
