package moe.qingu.youran.worldgen;

import com.google.common.base.Predicate;
import moe.qingu.youran.config.WorldGenConfig;
import moe.qingu.youran.logic.IntRange;
import moe.qingu.youran.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * 矿石的生成（1.3.4 各方块元素的 generateWorld）。四种矿石共用 Forge 给这个生成器的随机源，顺序与 1.3.4 相同
 * （坚冰、奇怪的矿石、闪锌矿、汞），每次先取 x、再取 y、再取 z，所以同一种子下与 1.3.4 生成在同样的位置。
 * <p>
 * 唯一的不同：汞不再替换基岩（服主 11-07）。
 * <p>
 * 矿脉位置是“区块角 + 0–15”，没有原版的 +8 偏移（同 1.3.4），矿脉可能伸进 -x、-z 方向的邻居区块（已知问题，见 README）。
 */
public class OreGenerator implements IWorldGenerator {
    private static final Predicate<IBlockState> HARD_ICE_TARGET = s -> is(s, Blocks.STONE) || is(s, Blocks.END_STONE);
    private static final Predicate<IBlockState> PRIMOGEM_TARGET = s -> is(s, Blocks.STONE) || is(s, Blocks.END_STONE)
            || is(s, Blocks.NETHERRACK) || is(s, Blocks.COAL_ORE) || is(s, Blocks.IRON_ORE) || is(s, Blocks.REDSTONE_ORE)
            || is(s, Blocks.LIT_REDSTONE_ORE) || is(s, Blocks.GRAVEL) || is(s, Blocks.MAGMA) || is(s, Blocks.QUARTZ_ORE)
            || isPlainDirt(s) || is(s, Blocks.BEDROCK);
    private static final Predicate<IBlockState> SPHALERITE_TARGET = s -> is(s, Blocks.STONE);
    /** 1.3.4 还能替换基岩，汞（流体）被桶装走后基岩层出洞；服主 11-07 去掉基岩 */
    private static final Predicate<IBlockState> HYDRARGYRUM_TARGET = s -> is(s, Blocks.STONE) || isPlainDirt(s);
    // 奇怪的矿石替换基岩（含 y=0 那一层）是服主特意写的，保持不变

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider) {
        int x = chunkX * 16;
        int z = chunkZ * 16;
        int dimension = world.provider.getDimension();
        generate(world, random, dimension, x, z, WorldGenConfig.hardIce, ModBlocks.hardIce, HARD_ICE_TARGET);
        generate(world, random, dimension, x, z, WorldGenConfig.primogemOre, ModBlocks.primogemOre, PRIMOGEM_TARGET);
        generate(world, random, dimension, x, z, WorldGenConfig.sphaleriteOre, ModBlocks.sphaleriteOre, SPHALERITE_TARGET);
        generate(world, random, dimension, x, z, WorldGenConfig.hydrargyrum, ModBlocks.hydrargyrum, HYDRARGYRUM_TARGET);
    }

    private static void generate(World world, Random random, int dimension, int x, int z, WorldGenConfig.Ore rule,
                                 Block block, Predicate<IBlockState> target) {
        if (!contains(rule.dimensions, dimension)) {
            return;
        }
        if (rule.biomes.length > 0 && !inBiome(world, new BlockPos(x, 128, z), rule.biomes)) {
            return;
        }
        WorldGenMinable vein = new WorldGenMinable(block.getDefaultState(), rule.veinSize, target);
        for (int i = 0; i < rule.perChunk; i++) {
            int bx = x + random.nextInt(16);
            int by = IntRange.pick(random, rule.minY, rule.maxY);
            int bz = z + random.nextInt(16);
            vein.generate(world, random, new BlockPos(bx, by, bz));
        }
    }

    private static boolean is(IBlockState state, Block block) {
        return state.getBlock() == block;
    }

    /** 普通泥土（元数据 0；砂土、灰化土不算），同 1.3.4 */
    private static boolean isPlainDirt(IBlockState state) {
        return state.getBlock() == Blocks.DIRT && Blocks.DIRT.getMetaFromState(state) == 0;
    }

    static boolean contains(int[] values, int value) {
        for (int v : values) {
            if (v == value) {
                return true;
            }
        }
        return false;
    }

    private static boolean inBiome(World world, BlockPos pos, String[] biomes) {
        ResourceLocation id = Biome.REGISTRY.getNameForObject(world.getBiome(pos));
        if (id == null) {
            return false;
        }
        for (String b : biomes) {
            if (id.equals(new ResourceLocation(b))) {
                return true;
            }
        }
        return false;
    }
}
