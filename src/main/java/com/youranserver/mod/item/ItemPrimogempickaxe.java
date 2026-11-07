
package com.youranserver.mod.item;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import java.util.Set;
import java.util.HashMap;

import com.youranserver.mod.creativetab.TabYouRanFuWuQi;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ItemPrimogempickaxe extends ElementsYouranmodMod.ModElement {
	@GameRegistry.ObjectHolder("youranmod:primogem_pickaxe")
	public static final Item block = null;
	public ItemPrimogempickaxe(ElementsYouranmodMod instance) {
		super(instance, 14);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemPickaxe(EnumHelper.addToolMaterial("PRIMOGEM_PICKAXE", 4, 4000, 20f, 5f, 50)) {
			{
				this.attackSpeed = -2.8f;
			}
			public Set<String> getToolClasses(ItemStack stack) {
				HashMap<String, Integer> ret = new HashMap<String, Integer>();
				ret.put("pickaxe", 4);
				return ret.keySet();
			}
		}.setUnlocalizedName("primogem_pickaxe").setRegistryName("primogem_pickaxe").setCreativeTab(TabYouRanFuWuQi.tab));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation("youranmod:primogem_pickaxe", "inventory"));
	}
}
