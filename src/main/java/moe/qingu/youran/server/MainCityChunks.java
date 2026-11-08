package moe.qingu.youran.server;

import io.netty.buffer.Unpooled;
import moe.qingu.youran.YouranMod;
import moe.qingu.youran.logic.PaletteHeader;
import moe.qingu.youran.registry.ModBlocks;
import moe.qingu.youran.world.MainCity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.IntPredicate;

/**
 * 主城维度里加载的区块：
 * <ul>
 *   <li>一律当作已装饰（服主 11-07：主城维度拒绝所有地形生成）。新区块在生成时就标好了（见 {@code world.MainCityChunkGenerator}）；
 *   这里补的是 1.3.4 时已经生成、但因为旁边的区块还没生成而一直没装饰过的边缘区块——不补的话，旁边生成新区块时原版会去装饰它，
 *   跑别的模组的世界生成器。只改这个标记，不动方块。</li>
 *   <li>清掉里面的主城传送门方块（服主 11-08：主城维度里不会有传送门；1.3.4 走传送门进主城时会在主城造回程的门）。
 *   先看各段的调色板里有没有传送门方块，没有就跳过（{@link PaletteHeader}），有才逐格找；找到的在这个世界 tick 结束时换成空气
 *   （加载区块的过程中不改方块），每个区块记一条日志。框架不动。</li>
 * </ul>
 */
public final class MainCityChunks {
    /** 状态 ID 是不是主城传送门 */
    private static final IntPredicate PORTAL_ID = id -> {
        IBlockState state = Block.BLOCK_STATE_IDS.getByValue(id);
        return state != null && state.getBlock() == ModBlocks.mainCityPortal;
    };

    /** 等着清掉的：每项是一个区块里找到的传送门方块 */
    private final Queue<List<BlockPos>> pending = new ConcurrentLinkedQueue<>();

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (!isMainCity(event.getWorld())) {
            return;
        }
        Chunk chunk = event.getChunk();
        if (!chunk.isTerrainPopulated()) {
            chunk.setTerrainPopulated(true);
            chunk.markDirty();
        }
        List<BlockPos> portals = findPortals(chunk);
        if (!portals.isEmpty()) {
            pending.add(portals);
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || pending.isEmpty() || !isMainCity(event.world)) {
            return;
        }
        List<BlockPos> portals;
        while ((portals = pending.poll()) != null) {
            List<BlockPos> removed = new ArrayList<>();
            for (BlockPos pos : portals) {
                if (event.world.isBlockLoaded(pos) && event.world.getBlockState(pos).getBlock() == ModBlocks.mainCityPortal) {
                    // 只通知客户端，不触发旁边方块的更新（旁边的传送门方块也在这张表里）
                    event.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                    removed.add(pos);
                }
            }
            if (!removed.isEmpty()) {
                ChunkPos chunk = new ChunkPos(removed.get(0));
                YouranMod.LOG.info("主城维度区块 ({}, {}) 里有 {} 格主城传送门方块（1.3.4 留下的），已清掉：{}",
                        chunk.x, chunk.z, removed.size(), describe(removed));
            }
        }
    }

    private static boolean isMainCity(World world) {
        return !world.isRemote && world.provider.getDimension() == MainCity.DIM_ID && MainCity.isAvailable();
    }

    /** 区块里的传送门方块；调色板里没有传送门的段直接跳过 */
    static List<BlockPos> findPortals(Chunk chunk) {
        List<BlockPos> found = new ArrayList<>();
        for (ExtendedBlockStorage section : chunk.getBlockStorageArray()) {
            if (section == Chunk.NULL_BLOCK_STORAGE || section.isEmpty()) {
                continue;
            }
            BlockStateContainer data = section.getData();
            PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
            data.write(buf);
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            if (!PaletteHeader.mayContain(bytes, PORTAL_ID)) {
                continue;
            }
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    for (int x = 0; x < 16; x++) {
                        if (data.get(x, y, z).getBlock() == ModBlocks.mainCityPortal) {
                            found.add(new BlockPos(chunk.x * 16 + x, section.getYLocation() + y, chunk.z * 16 + z));
                        }
                    }
                }
            }
        }
        return found;
    }

    /** 不多时列出每一格，多时只写范围 */
    private static String describe(List<BlockPos> list) {
        if (list.size() <= 16) {
            StringBuilder sb = new StringBuilder();
            for (BlockPos p : list) {
                sb.append(sb.length() == 0 ? "" : " ").append('(').append(p.getX()).append(", ").append(p.getY()).append(", ").append(p.getZ()).append(')');
            }
            return sb.toString();
        }
        int x0 = Integer.MAX_VALUE, y0 = Integer.MAX_VALUE, z0 = Integer.MAX_VALUE, x1 = Integer.MIN_VALUE, y1 = Integer.MIN_VALUE, z1 = Integer.MIN_VALUE;
        for (BlockPos p : list) {
            x0 = Math.min(x0, p.getX());
            y0 = Math.min(y0, p.getY());
            z0 = Math.min(z0, p.getZ());
            x1 = Math.max(x1, p.getX());
            y1 = Math.max(y1, p.getY());
            z1 = Math.max(z1, p.getZ());
        }
        return "(" + x0 + ", " + y0 + ", " + z0 + ") 到 (" + x1 + ", " + y1 + ", " + z1 + ") 之间";
    }
}
