package moe.qingu.youran.block;

import moe.qingu.youran.registry.YouranTab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** 坚冰（youranmod:hardice）。生成于末地，见 {@code worldgen.OreGenerator}。 */
public class BlockHardIce extends Block {
    public BlockHardIce() {
        super(Material.PACKED_ICE);
        setRegistryName("hardice");
        setTranslationKey("hardice");
        setSoundType(SoundType.GLASS);
        setHarvestLevel("pickaxe", 3);
        // 先硬度后抗性（顺序同 1.3.4，结果抗性为 10×3=30）
        setHardness(30F);
        setResistance(10F);
        setLightLevel(0F);
        setLightOpacity(255);
        setCreativeTab(YouranTab.tab);
        setDefaultSlipperiness(0.988f);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.ICE;
    }
}
