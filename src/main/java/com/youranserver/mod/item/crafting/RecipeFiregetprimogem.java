
package com.youranserver.mod.item.crafting;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.item.ItemStack;

import com.youranserver.mod.item.ItemPrimogem;
import com.youranserver.mod.block.BlockPrimogemore;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class RecipeFiregetprimogem extends ElementsYouranmodMod.ModElement {
	public RecipeFiregetprimogem(ElementsYouranmodMod instance) {
		super(instance, 33);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		GameRegistry.addSmelting(new ItemStack(BlockPrimogemore.block, (int) (1)), new ItemStack(ItemPrimogem.block, (int) (1)), 1F);
	}
}
