
package com.youranserver.mod.util;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.item.ItemStack;

import com.youranserver.mod.item.ItemPrimogem;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class OreDictIngotPrimogem extends ElementsYouranmodMod.ModElement {
	public OreDictIngotPrimogem(ElementsYouranmodMod instance) {
		super(instance, 47);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("primogem", new ItemStack(ItemPrimogem.block, (int) (1)));
	}
}
