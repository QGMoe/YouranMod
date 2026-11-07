
package com.youranserver.mod.creativetab;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

import com.youranserver.mod.item.ItemYouranserver;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class TabYouRanFuWuQi extends ElementsYouranmodMod.ModElement {
	public TabYouRanFuWuQi(ElementsYouranmodMod instance) {
		super(instance, 14);
	}

	@Override
	public void initElements() {
		tab = new CreativeTabs("tabyou_ran_fu_wu_qi") {
			@SideOnly(Side.CLIENT)
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ItemYouranserver.block, (int) (1));
			}

			@SideOnly(Side.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static CreativeTabs tab;
}
