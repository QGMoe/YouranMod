
package com.youranserver.mod.item;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import com.youranserver.mod.creativetab.TabYouRanFuWuQi;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ItemIcekuijia extends ElementsYouranmodMod.ModElement {
	@GameRegistry.ObjectHolder("youranmod:icekuijiahelmet")
	public static final Item helmet = null;
	@GameRegistry.ObjectHolder("youranmod:icekuijiabody")
	public static final Item body = null;
	@GameRegistry.ObjectHolder("youranmod:icekuijialegs")
	public static final Item legs = null;
	@GameRegistry.ObjectHolder("youranmod:icekuijiaboots")
	public static final Item boots = null;
	public ItemIcekuijia(ElementsYouranmodMod instance) {
		super(instance, 1);
	}

	@Override
	public void initElements() {
		ItemArmor.ArmorMaterial enuma = EnumHelper.addArmorMaterial("ICEKUIJIA", "youranmod:ice", 50, new int[]{2, 5, 6, 2}, 10,
				(net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("")), 0.2f);
		elements.items.add(() -> new ItemArmor(enuma, 0, EntityEquipmentSlot.HEAD).setUnlocalizedName("icekuijiahelmet")
				.setRegistryName("icekuijiahelmet").setCreativeTab(TabYouRanFuWuQi.tab));
		elements.items.add(() -> new ItemArmor(enuma, 0, EntityEquipmentSlot.CHEST).setUnlocalizedName("icekuijiabody")
				.setRegistryName("icekuijiabody").setCreativeTab(TabYouRanFuWuQi.tab));
		elements.items.add(() -> new ItemArmor(enuma, 0, EntityEquipmentSlot.LEGS).setUnlocalizedName("icekuijialegs")
				.setRegistryName("icekuijialegs").setCreativeTab(TabYouRanFuWuQi.tab));
		elements.items.add(() -> new ItemArmor(enuma, 0, EntityEquipmentSlot.FEET).setUnlocalizedName("icekuijiaboots")
				.setRegistryName("icekuijiaboots").setCreativeTab(TabYouRanFuWuQi.tab));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(helmet, 0, new ModelResourceLocation("youranmod:icekuijiahelmet", "inventory"));
		ModelLoader.setCustomModelResourceLocation(body, 0, new ModelResourceLocation("youranmod:icekuijiabody", "inventory"));
		ModelLoader.setCustomModelResourceLocation(legs, 0, new ModelResourceLocation("youranmod:icekuijialegs", "inventory"));
		ModelLoader.setCustomModelResourceLocation(boots, 0, new ModelResourceLocation("youranmod:icekuijiaboots", "inventory"));
	}
}
