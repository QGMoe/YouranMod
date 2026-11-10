package moe.qingu.youran.world.portal;

import moe.qingu.youran.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 主城传送门的框架尺寸（同原版下界传送门的 BlockPortal.Size，框架换成悠然 Logo 块）：
 * 内部宽 2–21、高 3–21，四个角不需要；里面只能是空气、火或已有的传送门方块。
 */
final class PortalSize {
    private final World world;
    private final EnumFacing.Axis axis;
    final EnumFacing rightDir;
    private final EnumFacing leftDir;
    int portalBlockCount;
    BlockPos bottomLeft;
    int height;
    int width;

    PortalSize(World world, BlockPos start, EnumFacing.Axis axis) {
        this.world = world;
        this.axis = axis;
        if (axis == EnumFacing.Axis.X) {
            this.leftDir = EnumFacing.EAST;
            this.rightDir = EnumFacing.WEST;
        } else {
            this.leftDir = EnumFacing.NORTH;
            this.rightDir = EnumFacing.SOUTH;
        }
        // 往下找到框架底边上面那一格（最多 21 格）
        BlockPos pos = start;
        while (pos.getY() > start.getY() - 21 && pos.getY() > 0 && isEmptyBlock(world.getBlockState(pos.down()).getBlock())) {
            pos = pos.down();
        }
        int i = getDistanceUntilEdge(pos, this.leftDir) - 1;
        if (i >= 0) {
            this.bottomLeft = pos.offset(this.leftDir, i);
            this.width = getDistanceUntilEdge(this.bottomLeft, this.rightDir);
            if (this.width < 2 || this.width > 21) {
                this.bottomLeft = null;
                this.width = 0;
            }
        }
        if (this.bottomLeft != null) {
            this.height = calculatePortalHeight();
        }
    }

    private static boolean isFrame(Block block) {
        return block == ModBlocks.youranLogo;
    }

    private int getDistanceUntilEdge(BlockPos pos, EnumFacing facing) {
        int i;
        for (i = 0; i < 22; ++i) {
            BlockPos p = pos.offset(facing, i);
            if (!isEmptyBlock(this.world.getBlockState(p).getBlock()) || !isFrame(this.world.getBlockState(p.down()).getBlock())) {
                break;
            }
        }
        return isFrame(this.world.getBlockState(pos.offset(facing, i)).getBlock()) ? i : 0;
    }

    int getHeight() {
        return this.height;
    }

    int getWidth() {
        return this.width;
    }

    private int calculatePortalHeight() {
        outer:
        for (this.height = 0; this.height < 21; ++this.height) {
            for (int i = 0; i < this.width; ++i) {
                BlockPos pos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                Block block = this.world.getBlockState(pos).getBlock();
                if (!isEmptyBlock(block)) {
                    break outer;
                }
                if (block == ModBlocks.mainCityPortal) {
                    ++this.portalBlockCount;
                }
                if (i == 0) {
                    if (!isFrame(this.world.getBlockState(pos.offset(this.leftDir)).getBlock())) {
                        break outer;
                    }
                } else if (i == this.width - 1) {
                    if (!isFrame(this.world.getBlockState(pos.offset(this.rightDir)).getBlock())) {
                        break outer;
                    }
                }
            }
        }
        for (int j = 0; j < this.width; ++j) {
            if (!isFrame(this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock())) {
                this.height = 0;
                break;
            }
        }
        if (this.height <= 21 && this.height >= 3) {
            return this.height;
        }
        this.bottomLeft = null;
        this.width = 0;
        this.height = 0;
        return 0;
    }

    private static boolean isEmptyBlock(Block block) {
        return block.getDefaultState().getMaterial() == Material.AIR || block == Blocks.FIRE || block == ModBlocks.mainCityPortal;
    }

    boolean isValid() {
        return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    void placePortalBlocks() {
        for (int i = 0; i < this.width; ++i) {
            BlockPos pos = this.bottomLeft.offset(this.rightDir, i);
            for (int j = 0; j < this.height; ++j) {
                this.world.setBlockState(pos.up(j), ModBlocks.mainCityPortal.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), 2);
            }
        }
    }
}
