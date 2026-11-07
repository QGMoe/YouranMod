package com.youranserver.mod.procedure;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;

import java.util.Map;

import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureThis_is_Youran_Server extends ElementsYouranmodMod.ModElement {
	public ProcedureThis_is_Youran_Server(ElementsYouranmodMod instance) {
		super(instance, 34);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure This_is_Youran_Server!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof EntityPlayer && !entity.world.isRemote) {
			((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A7e\u8FD9\u91CC\u662F\u60A0\u7136Minecraft\u670D\u52A1\u5668"),
					(false));
		}
	}
}
