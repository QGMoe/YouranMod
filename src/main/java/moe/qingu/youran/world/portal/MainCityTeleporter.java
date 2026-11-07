package moe.qingu.youran.world.portal;

import moe.qingu.youran.registry.ModBlocks;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * 走主城传送门时用的传送器：与原版下界传送门的 Teleporter 相同（找附近 128 格内的传送门，找不到就造一个），
 * 只是传送门方块换成主城传送门、框架换成悠然 Logo 块，在门里的相对位置与朝向由 {@link BlockMainCityPortal} 算好传进来
 * （原版是记在实体上的）。目的地只会是主城或主世界。
 */
public class MainCityTeleporter extends Teleporter {
    /** 玩家在出发的传送门里的相对位置：x 横向（0–1），y 纵向（0–1） */
    private final Vec3d lastPortalVec;
    /** 出发的传送门的朝向 */
    private final EnumFacing teleportDirection;

    public MainCityTeleporter(WorldServer world, Vec3d lastPortalVec, EnumFacing teleportDirection) {
        super(world);
        this.lastPortalVec = lastPortalVec;
        this.teleportDirection = teleportDirection;
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw) {
        if (!this.placeInExistingPortal(entity, rotationYaw)) {
            this.makePortal(entity);
            this.placeInExistingPortal(entity, rotationYaw);
        }
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        double bestDistance = -1.0D;
        int x = MathHelper.floor(entity.posX);
        int z = MathHelper.floor(entity.posZ);
        boolean newEntry = true;
        BlockPos found = BlockPos.ORIGIN;
        long key = ChunkPos.asLong(x, z);
        if (this.destinationCoordinateCache.containsKey(key)) {
            Teleporter.PortalPosition cached = this.destinationCoordinateCache.get(key);
            bestDistance = 0.0D;
            found = cached;
            cached.lastUpdateTime = this.world.getTotalWorldTime();
            newEntry = false;
        } else {
            BlockPos center = new BlockPos(entity);
            for (int dx = -128; dx <= 128; ++dx) {
                BlockPos next;
                for (int dz = -128; dz <= 128; ++dz) {
                    for (BlockPos pos = center.add(dx, this.world.getActualHeight() - 1 - center.getY(), dz); pos.getY() >= 0; pos = next) {
                        next = pos.down();
                        if (this.world.getBlockState(pos).getBlock() == ModBlocks.mainCityPortal) {
                            for (next = pos.down(); this.world.getBlockState(next).getBlock() == ModBlocks.mainCityPortal; next = next.down()) {
                                pos = next;
                            }
                            double distance = pos.distanceSq(center);
                            if (bestDistance < 0.0D || distance < bestDistance) {
                                bestDistance = distance;
                                found = pos;
                            }
                        }
                    }
                }
            }
        }
        if (bestDistance < 0.0D) {
            return false;
        }
        if (newEntry) {
            this.destinationCoordinateCache.put(key, new Teleporter.PortalPosition(found, this.world.getTotalWorldTime()));
        }
        double px = (double) found.getX() + 0.5D;
        double pz = (double) found.getZ() + 0.5D;
        BlockPattern.PatternHelper helper = ModBlocks.mainCityPortal.createPatternHelper(this.world, found);
        boolean flipped = helper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
        double edge = helper.getForwards().getAxis() == EnumFacing.Axis.X
                ? (double) helper.getFrontTopLeft().getZ()
                : (double) helper.getFrontTopLeft().getX();
        double py = (double) (helper.getFrontTopLeft().getY() + 1) - this.lastPortalVec.y * (double) helper.getHeight();
        if (flipped) {
            ++edge;
        }
        double across = edge + (1.0D - this.lastPortalVec.x) * (double) helper.getWidth()
                * (double) helper.getForwards().rotateY().getAxisDirection().getOffset();
        if (helper.getForwards().getAxis() == EnumFacing.Axis.X) {
            pz = across;
        } else {
            px = across;
        }
        // 按两个门的朝向转动速度与视角
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        float f3 = 0.0F;
        if (helper.getForwards().getOpposite() == this.teleportDirection) {
            f = 1.0F;
            f1 = 1.0F;
        } else if (helper.getForwards().getOpposite() == this.teleportDirection.getOpposite()) {
            f = -1.0F;
            f1 = -1.0F;
        } else if (helper.getForwards().getOpposite() == this.teleportDirection.rotateY()) {
            f2 = 1.0F;
            f3 = -1.0F;
        } else {
            f2 = -1.0F;
            f3 = 1.0F;
        }
        double motionX = entity.motionX;
        double motionZ = entity.motionZ;
        entity.motionX = motionX * (double) f + motionZ * (double) f3;
        entity.motionZ = motionX * (double) f2 + motionZ * (double) f1;
        entity.rotationYaw = rotationYaw - (float) (this.teleportDirection.getOpposite().getHorizontalIndex() * 90)
                + (float) (helper.getForwards().getHorizontalIndex() * 90);
        if (entity instanceof EntityPlayerMP) {
            ((EntityPlayerMP) entity).connection.setPlayerLocation(px, py, pz, entity.rotationYaw, entity.rotationPitch);
        } else {
            entity.setLocationAndAngles(px, py, pz, entity.rotationYaw, entity.rotationPitch);
        }
        return true;
    }

    /** 造一个传送门（同原版 Teleporter.makePortal，黑曜石换成悠然 Logo 块） */
    @Override
    public boolean makePortal(Entity entity) {
        double bestDistance = -1.0D;
        int x0 = MathHelper.floor(entity.posX);
        int y0 = MathHelper.floor(entity.posY);
        int z0 = MathHelper.floor(entity.posZ);
        int bestX = x0;
        int bestY = y0;
        int bestZ = z0;
        int bestOrientation = 0;
        int startOrientation = this.random.nextInt(4);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        // 先找能放下 3×4 的落脚台（含门两侧）的地方
        for (int x = x0 - 16; x <= x0 + 16; ++x) {
            double dx = (double) x + 0.5D - entity.posX;
            for (int z = z0 - 16; z <= z0 + 16; ++z) {
                double dz = (double) z + 0.5D - entity.posZ;
                column:
                for (int y = this.world.getActualHeight() - 1; y >= 0; --y) {
                    if (this.world.isAirBlock(mutable.setPos(x, y, z))) {
                        while (y > 0 && this.world.isAirBlock(mutable.setPos(x, y - 1, z))) {
                            --y;
                        }
                        for (int o = startOrientation; o < startOrientation + 4; ++o) {
                            int ox = o % 2;
                            int oz = 1 - ox;
                            if (o % 4 >= 2) {
                                ox = -ox;
                                oz = -oz;
                            }
                            for (int a = 0; a < 3; ++a) {
                                for (int b = 0; b < 4; ++b) {
                                    for (int c = -1; c < 4; ++c) {
                                        int px = x + (b - 1) * ox + a * oz;
                                        int py = y + c;
                                        int pz = z + (b - 1) * oz - a * ox;
                                        mutable.setPos(px, py, pz);
                                        if (c < 0 && !this.world.getBlockState(mutable).getMaterial().isSolid()
                                                || c >= 0 && !this.world.isAirBlock(mutable)) {
                                            continue column;
                                        }
                                    }
                                }
                            }
                            double dy = (double) y + 0.5D - entity.posY;
                            double distance = dx * dx + dy * dy + dz * dz;
                            if (bestDistance < 0.0D || distance < bestDistance) {
                                bestDistance = distance;
                                bestX = x;
                                bestY = y;
                                bestZ = z;
                                bestOrientation = o % 4;
                            }
                        }
                    }
                }
            }
        }
        // 找不到时退一步：只要能放下门本身（1×4）
        if (bestDistance < 0.0D) {
            for (int x = x0 - 16; x <= x0 + 16; ++x) {
                double dx = (double) x + 0.5D - entity.posX;
                for (int z = z0 - 16; z <= z0 + 16; ++z) {
                    double dz = (double) z + 0.5D - entity.posZ;
                    column:
                    for (int y = this.world.getActualHeight() - 1; y >= 0; --y) {
                        if (this.world.isAirBlock(mutable.setPos(x, y, z))) {
                            while (y > 0 && this.world.isAirBlock(mutable.setPos(x, y - 1, z))) {
                                --y;
                            }
                            for (int o = startOrientation; o < startOrientation + 2; ++o) {
                                int ox = o % 2;
                                int oz = 1 - ox;
                                for (int b = 0; b < 4; ++b) {
                                    for (int c = -1; c < 4; ++c) {
                                        int px = x + (b - 1) * ox;
                                        int py = y + c;
                                        int pz = z + (b - 1) * oz;
                                        mutable.setPos(px, py, pz);
                                        if (c < 0 && !this.world.getBlockState(mutable).getMaterial().isSolid()
                                                || c >= 0 && !this.world.isAirBlock(mutable)) {
                                            continue column;
                                        }
                                    }
                                }
                                double dy = (double) y + 0.5D - entity.posY;
                                double distance = dx * dx + dy * dy + dz * dz;
                                if (bestDistance < 0.0D || distance < bestDistance) {
                                    bestDistance = distance;
                                    bestX = x;
                                    bestY = y;
                                    bestZ = z;
                                    bestOrientation = o % 2;
                                }
                            }
                        }
                    }
                }
            }
        }
        int x = bestX;
        int y = bestY;
        int z = bestZ;
        int ox = bestOrientation % 2;
        int oz = 1 - ox;
        if (bestOrientation % 4 >= 2) {
            ox = -ox;
            oz = -oz;
        }
        IBlockState frame = ModBlocks.youranLogo.getDefaultState();
        // 哪里都放不下：在 y 70 到顶部往下 10 格之间悬空造，带一块落脚台
        if (bestDistance < 0.0D) {
            bestY = MathHelper.clamp(bestY, 70, this.world.getActualHeight() - 10);
            y = bestY;
            for (int a = -1; a <= 1; ++a) {
                for (int b = 1; b < 3; ++b) {
                    for (int c = -1; c < 3; ++c) {
                        int px = x + (b - 1) * ox + a * oz;
                        int py = y + c;
                        int pz = z + (b - 1) * oz - a * ox;
                        boolean floor = c < 0;
                        this.world.setBlockState(new BlockPos(px, py, pz), floor ? frame : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        IBlockState portal = ModBlocks.mainCityPortal.getDefaultState()
                .withProperty(BlockPortal.AXIS, ox == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
        for (int round = 0; round < 4; ++round) {
            for (int b = 0; b < 4; ++b) {
                for (int c = -1; c < 4; ++c) {
                    int px = x + (b - 1) * ox;
                    int py = y + c;
                    int pz = z + (b - 1) * oz;
                    boolean isFrame = b == 0 || b == 3 || c == -1 || c == 3;
                    this.world.setBlockState(new BlockPos(px, py, pz), isFrame ? frame : portal, 2);
                }
            }
            for (int b = 0; b < 4; ++b) {
                for (int c = -1; c < 4; ++c) {
                    int px = x + (b - 1) * ox;
                    int py = y + c;
                    int pz = z + (b - 1) * oz;
                    BlockPos pos = new BlockPos(px, py, pz);
                    this.world.notifyNeighborsOfStateChange(pos, this.world.getBlockState(pos).getBlock(), false);
                }
            }
        }
        return true;
    }
}
