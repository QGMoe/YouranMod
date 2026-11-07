
package com.youranserver.mod.item;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;

import java.util.List;

import com.youranserver.mod.creativetab.TabYouRanFuWuQi;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ItemPrimogem extends ElementsYouranmodMod.ModElement {
	@GameRegistry.ObjectHolder("youranmod:primogem")
	public static final Item block = null;
	public ItemPrimogem(ElementsYouranmodMod instance) {
		super(instance, 13);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation("youranmod:primogem", "inventory"));
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			setMaxDamage(0);
			maxStackSize = 64;
			setUnlocalizedName("primogem");
			setRegistryName("primogem");
			setCreativeTab(TabYouRanFuWuQi.tab);
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getMaxItemUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, IBlockState par2Block) {
			return 1F;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add(
					"\u636E\u8BF4\u8FD9\u662F\u5F88\u4E45\u4EE5\u524D\u7531\u6D41\u661F\u5E26\u6765\u7684\u4E1C\u897F\uFF0C\u7ECF\u8FC7\u6F2B\u957F\u7684\u5730\u8D28\u6F14\u53D8\u800C\u6DF1\u57CB\u5730\u5E95");
		}
	}
}
