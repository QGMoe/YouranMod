package com.youranserver.mod.procedure;

import net.minecraftforge.common.DimensionManager;

import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;

import java.util.Map;

import com.youranserver.mod.world.WorldHome;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureTp_to_maincity extends ElementsYouranmodMod.ModElement {
	public ProcedureTp_to_maincity(ElementsYouranmodMod instance) {
		super(instance, 35);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure Tp_to_maincity!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity.dimension) == (WorldHome.DIMID))) {
			entity.setPositionAndUpdate((-258), 70, 118);
		} else {
			{
				Entity _ent = entity;
				if (!_ent.world.isRemote && !_ent.isRiding() && !_ent.isBeingRidden() && _ent instanceof EntityPlayerMP) {
					int dimensionID = WorldHome.DIMID;
					class TeleporterDirect extends Teleporter {
						public TeleporterDirect(WorldServer worldserver) {
							super(worldserver);
						}

						@Override
						public void placeInPortal(Entity entity, float yawrotation) {
						}

						@Override
						public boolean placeInExistingPortal(Entity entity, float yawrotation) {
							return true;
						}

						@Override
						public boolean makePortal(Entity entity) {
							return true;
						}
					}
					EntityPlayerMP _player = (EntityPlayerMP) _ent;
					_player.mcServer.getPlayerList().transferPlayerToDimension(_player, dimensionID, new TeleporterDirect(_player.getServerWorld()));
					_player.connection.setPlayerLocation(DimensionManager.getWorld(dimensionID).getSpawnPoint().getX(),
							DimensionManager.getWorld(dimensionID).getSpawnPoint().getY() + 1,
							DimensionManager.getWorld(dimensionID).getSpawnPoint().getZ(), _player.rotationYaw, _player.rotationPitch);
				}
			}
			entity.setPositionAndUpdate((-258), 70, 118);
		}
	}
}
