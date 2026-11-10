package moe.qingu.youran.worldgen;

import moe.qingu.youran.config.WorldGenConfig;
import moe.qingu.youran.logic.LakePlacement;
import moe.qingu.youran.registry.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * 矿泉水湖（默认只在暮色森林，每区块 0.2%）。与矿石分开登记，用重生成模组只补矿时不会连湖一起补。
 * <p>
 * 与 1.3.4 的两处不同（服主 11-07 定）：
 * <ul>
 *   <li>湖整个落在正在装饰的这个区块里（起点取区块角 +8，原版的湖会先减 8，16×16 的范围正好是这个区块），
 *   不写进旁边的区块——旁边的可能是玩家早已看过、盖了房子的区块；正在装饰的区块还没发给客户端。
 *   1.3.4 的起点是区块角 +8 再加 0–15 的随机数，湖会伸进 +x、+z 方向的邻居；</li>
 *   <li>概率用 Forge 传进来的随机源（与世界种子、区块坐标有关）；1.3.4 用的是 Math.random()。</li>
 * </ul>
 * 高度照旧在 0–255 里随机取，再由原版的湖往下找到地面。
 */
public class MineralWaterLakeGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider) {
        WorldGenConfig.Lake rule = WorldGenConfig.mineralWaterLake;
        if (!OreGenerator.contains(rule.dimensions, world.provider.getDimension())) {
            return;
        }
        if (!(random.nextDouble() < rule.chance)) {
            return;
        }
        BlockPos origin = new BlockPos(LakePlacement.origin(chunkX), random.nextInt(256), LakePlacement.origin(chunkZ));
        new WorldGenLakes(ModBlocks.mineralWater).generate(world, random, origin);
    }
}
