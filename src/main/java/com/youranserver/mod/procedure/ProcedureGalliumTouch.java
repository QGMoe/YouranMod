package com.youranserver.mod.procedure;

import java.util.Map;

import com.youranserver.mod.ElementsYouranmodMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureGalliumTouch extends ElementsYouranmodMod.ModElement {

	public ProcedureGalliumTouch(ElementsYouranmodMod instance) {
		super(instance, 64);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure GalliumTouch!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof EntityLivingBase)
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 3, false, false));
		if (entity instanceof EntityItem) {
			EntityItem entity1 = (EntityItem) entity;
			ProcedureHydrargyrumPoison.CheckItem(dependencies, entity1);
		}
	}
}
