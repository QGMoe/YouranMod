package moe.qingu.youran.world;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 主城维度的区块生成：新区块完全是空的（服主 11-07 定）。
 * <p>
 * 新区块全是空气、群系全是平原，生成时就标成“已装饰”（{@link Chunk#setTerrainPopulated}），于是原版的
 * {@code Chunk.populate} 只会走到 {@link #generateStructures}（这里什么都不做），不再调用 {@link #populate}、
 * 也不再调用 Forge 的 {@code GameRegistry.generateWorld}——别的模组的世界生成器（如 SCP 的矿石）、装饰事件、
 * 生成时的动物都不会在主城维度出现。主城里原有区块里还没装饰过的（1.3.4 时已生成、旁边的区块还没生成的边缘区块），
 * 由 {@code server.MainCityChunks} 在加载时同样标成已装饰。光照由区块第一次被 tick 时补算（{@code Chunk.onTick}），
 * 之后照常发给客户端。
 * <p>
 * 1.3.4 用的是 MCreator 的“末地式浮岛”模板，模板里的石头写成了空气，所以地形本来就是空的，但装饰还在跑：
 * 挖“空气浮岛”（会挖掉旁边已有区块里的方块）、平原群系的装饰（矿石、花草等会长进旁边已有的区块）、生成时的动物、
 * 别的模组的世界生成器。这些现在都没有了（与 1.3.4 的差异，见 README）。
 * <p>
 * 生物的自然刷新不受影响：仍按平原群系的刷怪表（{@link #getPossibleCreatures}），同 1.3.4。
 */
public class MainCityChunkGenerator implements IChunkGenerator {
    private static final int SEA_LEVEL = 63;

    private final World world;

    public MainCityChunkGenerator(World world) {
        world.setSeaLevel(SEA_LEVEL);
        this.world = world;
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        Biome[] biomes = this.world.getBiomeProvider().getBiomesForGeneration(null, x * 16, z * 16, 16, 16);
        Chunk chunk = new Chunk(this.world, new ChunkPrimer(), x, z);
        byte[] biomeArray = chunk.getBiomeArray();
        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = (byte) Biome.getIdForBiome(biomes[i]);
        }
        chunk.generateSkylightMap();
        chunk.setTerrainPopulated(true);
        return chunk;
    }

    /** 不会被调用（区块生成时已标成装饰过）；留空 */
    @Override
    public void populate(int x, int z) {
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Override
    public boolean generateStructures(Chunk chunk, int x, int z) {
        return false;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {
    }

    @Override
    public boolean isInsideStructure(World world, String structureName, BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World world, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }
}
