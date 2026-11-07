package com.youranserver.mod.procedure;

import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;

import java.util.Map;

import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureHydrargyrumPoison extends ElementsYouranmodMod.ModElement {

	static String[] canBumb={
		"ingotSodium",
		"ingotMagnesium",
		"ingotPotassium",
		"ingotCalcium",
		"ingotAluminum"
	};
	static float[] level={
		2.0f,2.0f,1.5f,1.5f,1.0f
	};

	public ProcedureHydrargyrumPoison(ElementsYouranmodMod instance) {
		super(instance, 63);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure HydrargyrumPoison!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof EntityLivingBase)
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 5, false, false));
		entity.getEntityData().setDouble("hydrargyrum_poison", 1);
		if (entity instanceof EntityItem){
			EntityItem entity1 = (EntityItem) entity;
			CheckItem(dependencies,entity1);
		}
			
	}

	static void CheckItem(Map<String, Object> dependencies,EntityItem entity){
		ItemStack item = entity.getItem();
		int[] id = OreDictionary.getOreIDs(item);
		if(id.length <1){
			return;
		}
		int oreid = -1;
		for(int a : id){
			String ore = OreDictionary.getOreName(a);
			if((oreid = IsCanBumb(ore))>=0){
			break;
			}
			return;
		}
		World world = (World) dependencies.get("world");
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		world.createExplosion(entity,x,y,z,level[oreid], false);
		entity.setDead();
	}

	private static int IsCanBumb(String ore){
		for(int i=0;i<canBumb.length;i++){
			if(ore.equals(canBumb[i])){
				return i;
			}
		}
		return -1;
	}
}
