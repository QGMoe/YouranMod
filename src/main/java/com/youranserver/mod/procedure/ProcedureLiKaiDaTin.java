package com.youranserver.mod.procedure;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;

import java.util.Map;

import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureLiKaiDaTin extends ElementsYouranmodMod.ModElement {
	public ProcedureLiKaiDaTin(ElementsYouranmodMod instance) {
		super(instance, 22);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure LiKaiDaTin!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof EntityPlayer && !entity.world.isRemote) {
			((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A7e\u60A8\u5DF2\u79BB\u5F00\u4E3B\u57CE\u5927\u5385"), (true));
		}
	}
}
