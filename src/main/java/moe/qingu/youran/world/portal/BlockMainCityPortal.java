package moe.qingu.youran.world.portal;

import com.google.common.cache.LoadingCache;
import moe.qingu.youran.registry.ModBlocks;
import moe.qingu.youran.world.MainCity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * 主城传送门（youranmod:home_portal）：悠然 Logo 块的框架，用“奇怪的工具”点燃。
 * 玩家（不骑乘、不被骑乘）走进去：不在主城就去主城，在主城就回主世界；目的地附近 128 格内没有传送门就用 Logo 块造一个。
 * 只传送玩家。不可破坏，亮度 15，不会像下界传送门那样刷僵尸猪人。
 */
public class BlockMainCityPortal extends BlockPortal {
    /** EntityPlayerMP.invulnerableDimensionChange（换维度时暂时不受伤，原版下界传送门也这样设） */
    private static Field invulnerableDimensionChange;

    public BlockMainCityPortal() {
        setHardness(-1.0F);
        setRegistryName("home_portal");
        setTranslationKey("home_portal");
        setLightLevel(1F);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        // 不刷僵尸猪人
    }

    /** “奇怪的工具”点燃：先试南北向（X 轴）框架，再试东西向（Z 轴）；框架里已有传送门方块时不点 */
    public void tryIgnite(World world, BlockPos pos) {
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

    @Override
    public BlockPattern.PatternHelper createPatternHelper(World world, BlockPos pos) {
        EnumFacing.Axis axis = EnumFacing.Axis.Z;
        PortalSize size = new PortalSize(world, pos, EnumFacing.Axis.X);
        LoadingCache<BlockPos, BlockWorldState> cache = BlockPattern.createLoadingCache(world, true);
        if (!size.isValid()) {
            axis = EnumFacing.Axis.X;
            size = new PortalSize(world, pos, EnumFacing.Axis.Z);
        }
        if (!size.isValid()) {
            return new BlockPattern.PatternHelper(pos, EnumFacing.NORTH, EnumFacing.UP, cache, 1, 1, 1);
        }
        int[] counts = new int[EnumFacing.AxisDirection.values().length];
        EnumFacing facing = size.rightDir.rotateYCCW();
        BlockPos topLeft = size.bottomLeft.up(size.getHeight() - 1);
        for (EnumFacing.AxisDirection direction : EnumFacing.AxisDirection.values()) {
            BlockPattern.PatternHelper helper = new BlockPattern.PatternHelper(
                    facing.getAxisDirection() == direction ? topLeft : topLeft.offset(size.rightDir, size.getWidth() - 1),
                    EnumFacing.getFacingFromAxis(direction, axis), EnumFacing.UP, cache, size.getWidth(), size.getHeight(), 1);
            for (int i = 0; i < size.getWidth(); ++i) {
                for (int j = 0; j < size.getHeight(); ++j) {
                    BlockWorldState state = helper.translateOffset(i, j, 1);
                    if (state.getBlockState() != null && state.getBlockState().getMaterial() != Material.AIR) {
                        ++counts[direction.ordinal()];
                    }
                }
            }
        }
        EnumFacing.AxisDirection best = EnumFacing.AxisDirection.POSITIVE;
        for (EnumFacing.AxisDirection direction : EnumFacing.AxisDirection.values()) {
            if (counts[direction.ordinal()] < counts[best.ordinal()]) {
                best = direction;
            }
        }
        return new BlockPattern.PatternHelper(
                facing.getAxisDirection() == best ? topLeft : topLeft.offset(size.rightDir, size.getWidth() - 1),
                EnumFacing.getFacingFromAxis(best, axis), EnumFacing.UP, cache, size.getWidth(), size.getHeight(), 1);
    }

    /** 框架不完整、或传送门没填满时，这一格消失 */
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        EnumFacing.Axis axis = state.getValue(AXIS);
        if (axis == EnumFacing.Axis.X || axis == EnumFacing.Axis.Z) {
            PortalSize size = new PortalSize(world, pos, axis);
            if (!size.isValid() || size.portalBlockCount < size.width * size.height) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
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
        if (world.isRemote || entity.isRiding() || entity.isBeingRidden() || !(entity instanceof EntityPlayerMP)) {
            return;
        }
        if (!MainCity.isAvailable()) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) entity;
        if (player.timeUntilPortal > 0) {
            player.timeUntilPortal = 10;
            return;
        }
        player.timeUntilPortal = 10;
        int target = player.dimension != MainCity.DIM_ID ? MainCity.DIM_ID : 0;
        setInvulnerableDimensionChange(player);
        player.server.getPlayerList().transferPlayerToDimension(player, target, teleporterFor(player, pos, target));
    }

    /** 记下玩家在传送门里的相对位置与朝向，到目的地的传送门里放到对应的位置（同原版 Entity.setPortal） */
    private MainCityTeleporter teleporterFor(EntityPlayerMP player, BlockPos pos, int dimension) {
        BlockPattern.PatternHelper helper = ModBlocks.mainCityPortal.createPatternHelper(player.world, pos);
        double edge = helper.getForwards().getAxis() == EnumFacing.Axis.X
                ? (double) helper.getFrontTopLeft().getZ()
                : (double) helper.getFrontTopLeft().getX();
        double across = helper.getForwards().getAxis() == EnumFacing.Axis.X ? player.posZ : player.posX;
        across = Math.abs(MathHelper.pct(
                across - (double) (helper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0),
                edge, edge - (double) helper.getWidth()));
        double up = MathHelper.pct(player.posY - 1, (double) helper.getFrontTopLeft().getY(),
                (double) (helper.getFrontTopLeft().getY() - helper.getHeight()));
        return new MainCityTeleporter(player.server.getWorld(dimension), new Vec3d(across, up, 0), helper.getForwards());
    }

    private static void setInvulnerableDimensionChange(EntityPlayerMP player) {
        if (invulnerableDimensionChange == null) {
            invulnerableDimensionChange = ReflectionHelper.findField(EntityPlayerMP.class, "invulnerableDimensionChange", "field_184851_cj");
        }
        try {
            invulnerableDimensionChange.setBoolean(player, true);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
