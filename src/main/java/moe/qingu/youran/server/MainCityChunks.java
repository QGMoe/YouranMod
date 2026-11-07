package moe.qingu.youran.server;

import moe.qingu.youran.world.MainCity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 主城维度里加载的区块一律当作已装饰（服主 11-07：主城维度拒绝所有地形生成）。
 * <p>
 * 新区块在生成时就标好了（见 {@code world.MainCityChunkGenerator}）；这里补的是 1.3.4 时已经生成、但因为旁边的区块还没生成而
 * 一直没装饰过的边缘区块——不补的话，旁边生成新区块时原版会去装饰它，跑别的模组的世界生成器。只改这个标记，不动方块。
 */
public final class MainCityChunks {
    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (event.getWorld().isRemote || event.getWorld().provider.getDimension() != MainCity.DIM_ID || !MainCity.isAvailable()) {
            return;
        }
        Chunk chunk = event.getChunk();
        if (!chunk.isTerrainPopulated()) {
            chunk.setTerrainPopulated(true);
            chunk.markDirty();
        }
    }
}
