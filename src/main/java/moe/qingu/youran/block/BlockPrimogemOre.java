package moe.qingu.youran.block;

import moe.qingu.youran.registry.YouranTab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/** 奇怪的矿石（youranmod:primogemore），矿物词典 primogem_ore；熔炼得奇怪的石头。 */
public class BlockPrimogemOre extends Block {
    public BlockPrimogemOre() {
        super(Material.ROCK);
        setRegistryName("primogemore");
        setTranslationKey("primogemore");
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 3);
        setHardness(50F);
        setResistance(100F);
        // 亮度 1（0.0667×15 取整）
        setLightLevel(0.066666666667F);
        setLightOpacity(255);
        setCreativeTab(YouranTab.tab);
    }
}
