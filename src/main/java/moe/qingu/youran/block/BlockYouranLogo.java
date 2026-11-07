package moe.qingu.youran.block;

import moe.qingu.youran.registry.YouranTab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** 悠然服务器 Logo 块（youranmod:youran_serverlogoblock）。也是主城传送门的框架。 */
public class BlockYouranLogo extends Block {
    public BlockYouranLogo() {
        super(Material.ROCK);
        setRegistryName("youran_serverlogoblock");
        setTranslationKey("youran_serverlogoblock");
        setSoundType(SoundType.STONE);
        // 先硬度后抗性（顺序同 1.3.4：setHardness 会把抗性抬到硬度的 5 倍，之后的 setResistance 再覆盖）
        setHardness(150F);
        setResistance(500000F);
        setLightLevel(0F);
        setLightOpacity(255);
        setCreativeTab(YouranTab.tab);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.YELLOW;
    }
}
