package com.youranserver.mod.procedure;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;

import java.util.Map;

import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureRunningInDimensionTick extends ElementsYouranmodMod.ModElement {
	public ProcedureRunningInDimensionTick(ElementsYouranmodMod instance) {
		super(instance, 58);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure RunningInDimensionTick!");
			return;
		}
		World world = (World) dependencies.get("world");
		if ((((world.provider.getDimension()) == (0)) && (((world.getWorldTime()) == 1000) && ((world.getCurrentMoonPhaseFactor()) == 1)))) {
			if (!world.isRemote && world.getMinecraftServer() != null) {
				world.getMinecraftServer().getCommandManager().executeCommand(new ICommandSender() {
					@Override
					public String getName() {
						return "";
					}

					@Override
					public boolean canUseCommand(int permission, String command) {
						return true;
					}

					@Override
					public World getEntityWorld() {
						return world;
					}

					@Override
					public MinecraftServer getServer() {
						return world.getMinecraftServer();
					}

					@Override
					public boolean sendCommandFeedback() {
						return false;
					}

					@Override
					public BlockPos getPosition() {
						return new BlockPos((int) 0, (int) 64, (int) 0);
					}

					@Override
					public Vec3d getPositionVector() {
						return new Vec3d(0, 64, 0);
					}
				}, "dynmap updaterender world 0 0");
			}
		}
		if (((world.getWorldTime()) == 23000)) {
			if (!world.isRemote && world.getMinecraftServer() != null) {
				world.getMinecraftServer().getCommandManager().executeCommand(new ICommandSender() {
					@Override
					public String getName() {
						return "";
					}

					@Override
					public boolean canUseCommand(int permission, String command) {
						return true;
					}

					@Override
					public World getEntityWorld() {
						return world;
					}

					@Override
					public MinecraftServer getServer() {
						return world.getMinecraftServer();
					}

					@Override
					public boolean sendCommandFeedback() {
						return false;
					}

					@Override
					public BlockPos getPosition() {
						return new BlockPos((int) 0, (int) 64, (int) 0);
					}

					@Override
					public Vec3d getPositionVector() {
						return new Vec3d(0, 64, 0);
					}
				}, "/effect @e[type=scp:dpersonnel] minecraft:instant_health 1 100");
			}
		} else if (((world.getWorldTime()) == 6000)) {
			if (!world.isRemote && world.getMinecraftServer() != null) {
				world.getMinecraftServer().getCommandManager().executeCommand(new ICommandSender() {
					@Override
					public String getName() {
						return "";
					}

					@Override
					public boolean canUseCommand(int permission, String command) {
						return true;
					}

					@Override
					public World getEntityWorld() {
						return world;
					}

					@Override
					public MinecraftServer getServer() {
						return world.getMinecraftServer();
					}

					@Override
					public boolean sendCommandFeedback() {
						return false;
					}

					@Override
					public BlockPos getPosition() {
						return new BlockPos((int) 0, (int) 64, (int) 0);
					}

					@Override
					public Vec3d getPositionVector() {
						return new Vec3d(0, 64, 0);
					}
				}, "/effect @e[type=scp:dpersonnel] minecraft:instant_health 1 2");
			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			World world = event.world;
			java.util.HashMap<String, Object> dependencies = new java.util.HashMap<>();
			dependencies.put("world", world);
			dependencies.put("event", event);
			this.executeProcedure(dependencies);
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
