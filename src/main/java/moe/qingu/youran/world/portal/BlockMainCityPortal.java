package moe.qingu.youran.world.portal;

import moe.qingu.youran.server.Teleports;
import moe.qingu.youran.world.MainCity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * 主城传送门（youranmod:home_portal）：悠然 Logo 块的框架，用“奇怪的工具”点燃。不可破坏，亮度 15，不会像下界传送门那样刷僵尸猪人。
 * <p>
 * 服主 11-08 定：
 * <ul>
 *   <li>在主城以外的维度走进去，效果与 /hub 完全一样（{@link Teleports#toMainCity}：主城传送点、欢迎语与进度、先下坐骑、
 *   主城维度没启用时的提示）。不再像 1.3.4 那样按坐标比例换算、在目的地找门或造门。只传送玩家；同 1.3.4 走进去就传，
 *   站在门里时冷却一直刷新（{@code timeUntilPortal}），不会反复传。</li>
 *   <li>主城维度里不会有传送门：在主城里点不燃；放下的、被更新的、被随机 tick 到的、被实体碰到的传送门方块直接消失
 *   （框架不动）。1.3.4 时留在主城里的门由 {@code server.MainCityChunks} 在区块加载时清掉。</li>
 * </ul>
 * 骑着东西走进去：骑在上面的玩家也会碰到传送门（原版生物每 tick 都在原地做一次碰撞），于是同 /hub 先下坐骑再传送，
 * 坐骑留在门里（1.3.4 骑乘、被骑时不传送）。
 */
public class BlockMainCityPortal extends BlockPortal {
    public BlockMainCityPortal() {
        setHardness(-1.0F);
        setRegistryName("home_portal");
        setTranslationKey("home_portal");
        setLightLevel(1F);
    }

    /** 这个世界是主城维度（主城维度启用时） */
    static boolean inMainCity(World world) {
        return MainCity.isAvailable() && world.provider.getDimension() == MainCity.DIM_ID;
    }

    /** 主城维度里的传送门方块直接消失（服务端；客户端随后收到方块变化） */
    private static boolean removedInMainCity(World world, BlockPos pos) {
        if (!inMainCity(world)) {
            return false;
        }
        if (!world.isRemote) {
            world.setBlockToAir(pos);
        }
        return true;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        // 不刷僵尸猪人；主城维度里被随机 tick 到时消失
        removedInMainCity(world, pos);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        // 主城维度里放下（物品形式、/setblock 等）就消失
        removedInMainCity(world, pos);
    }

    /** “奇怪的工具”点燃：先试南北向（X 轴）框架，再试东西向（Z 轴）；框架里已有传送门方块时不点。主城维度里点不燃 */
    public void tryIgnite(World world, BlockPos pos) {
        if (inMainCity(world)) {
            return;
        }
        PortalSize size = new PortalSize(world, pos, EnumFacing.Axis.X);
        if (size.isValid() && size.portalBlockCount == 0) {
            size.placePortalBlocks();
            return;
        }
        size = new PortalSize(world, pos, EnumFacing.Axis.Z);
        if (size.isValid() && size.portalBlockCount == 0) {
            size.placePortalBlocks();
        }
    }

    /** 框架不完整、或传送门没填满时，这一格消失；主城维度里一被更新就消失 */
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (removedInMainCity(world, pos)) {
            return;
        }
        EnumFacing.Axis axis = state.getValue(AXIS);
        if (axis == EnumFacing.Axis.X || axis == EnumFacing.Axis.Z) {
            PortalSize size = new PortalSize(world, pos, axis);
            if (!size.isValid() || size.portalBlockCount < size.width * size.height) {
                world.setBlockToAir(pos);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 4; i++) {
            double px = pos.getX() + random.nextFloat();
            double py = pos.getY() + random.nextFloat();
            double pz = pos.getZ() + random.nextFloat();
            double vx = (random.nextFloat() - 0.5) / 2f;
            double vy = (random.nextFloat() - 0.5) / 2f;
            double vz = (random.nextFloat() - 0.5) / 2f;
            int j = random.nextInt(4) - 1;
            if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
                px = pos.getX() + 0.5 + 0.25 * j;
                vx = random.nextFloat() * 2 * j;
            } else {
                pz = pos.getZ() + 0.5 + 0.25 * j;
                vz = random.nextFloat() * 2 * j;
            }
            world.spawnParticle(EnumParticleTypes.SPELL, px, py, pz, vx, vy, vz);
        }
        if (random.nextInt(110) == 0) {
            world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_AMBIENT,
                    SoundCategory.BLOCKS, 0.5f, random.nextFloat() * 0.4F + 0.8F, false);
        }
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (world.isRemote || removedInMainCity(world, pos) || !(entity instanceof EntityPlayerMP)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) entity;
        if (player.timeUntilPortal > 0) {
            player.timeUntilPortal = 10;
            return;
        }
        player.timeUntilPortal = 10;
        Teleports.toMainCity(player);
    }
}
