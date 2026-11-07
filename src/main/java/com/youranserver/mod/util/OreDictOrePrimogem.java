
package com.youranserver.mod.util;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.item.ItemStack;

import com.youranserver.mod.block.BlockPrimogemore;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class OreDictOrePrimogem extends ElementsYouranmodMod.ModElement {
	public OreDictOrePrimogem(ElementsYouranmodMod instance) {
		super(instance, 48);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("primogemOre", new ItemStack(BlockPrimogemore.block, (int) (1)));
	}
}
