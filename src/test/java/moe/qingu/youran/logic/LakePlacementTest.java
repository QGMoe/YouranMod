package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LakePlacementTest {
    /** 湖占用的 16 格（WorldGenLakes 先减 8，再用 0–15）正好是这个区块，负坐标的区块也一样 */
    @Test
    void lakeStaysInsideItsChunk() {
        for (int chunk = -40; chunk <= 40; chunk++) {
            assertEquals(chunk * 16, LakePlacement.minBlock(chunk));
            assertEquals(chunk * 16 + 15, LakePlacement.maxBlock(chunk));
            assertEquals(chunk, LakePlacement.minBlock(chunk) >> 4);
            assertEquals(chunk, LakePlacement.maxBlock(chunk) >> 4);
            assertEquals(LakePlacement.origin(chunk) - 8, LakePlacement.minBlock(chunk));
        }
    }
}
