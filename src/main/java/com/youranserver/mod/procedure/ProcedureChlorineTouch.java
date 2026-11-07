package com.youranserver.mod.procedure;

import java.util.Map;

import com.youranserver.mod.ElementsYouranmodMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureChlorineTouch extends ElementsYouranmodMod.ModElement {

	public ProcedureChlorineTouch(ElementsYouranmodMod instance) {
		super(instance, 65);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure ChlorineTouch!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof EntityLivingBase){
			EntityPlayer player = null;
			if(entity instanceof EntityPlayer){
				player = (EntityPlayer) entity;
			}
			boolean stop = false;
			if(player != null){
				stop = CheckPlayer(player);
			}
			if(stop){
				return;
			}
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 300, 2, false, false));
		}

	}

	private static boolean CheckPlayer(EntityPlayer player){
		String one = player.inventory.armorInventory.get(3).getItem().getRegistryName().toString();
		if(one.equals("ic2:hazmat_helmet")){
			return true;
		}
		String two = player.inventory.armorInventory.get(2).getItem().getRegistryName().toString();
		if(two.equals("scp:hazard_suit")){
			return true;
		}
		return false;
	}
}
