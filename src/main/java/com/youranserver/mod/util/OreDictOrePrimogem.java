
package com.youranserver.mod.util;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.item.ItemStack;

import com.youranserver.mod.block.BlockPrimogemore;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class OreDictOrePrimogem extends ElementsYouranmodMod.ModElement {
	public OreDictOrePrimogem(ElementsYouranmodMod instance) {
		super(instance, 51);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("primogem_ore", new ItemStack(BlockPrimogemore.block, (int) (1)));
	}
}
