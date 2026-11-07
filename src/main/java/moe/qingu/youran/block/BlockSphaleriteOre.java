package moe.qingu.youran.block;

import moe.qingu.youran.registry.YouranTab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/** 闪锌矿（youranmod:sphalerite_ore）。CraftTweaker 脚本把它加进 oreZinc。 */
public class BlockSphaleriteOre extends Block {
    public BlockSphaleriteOre() {
        super(Material.ROCK);
        setRegistryName("sphalerite_ore");
        setTranslationKey("sphalerite_ore");
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 1);
        setHardness(3F);
        setResistance(3F);
        setLightLevel(0F);
        setLightOpacity(255);
        setCreativeTab(YouranTab.tab);
    }
}
