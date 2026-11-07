package com.youranserver.mod.procedure;

import net.minecraftforge.common.DimensionManager;

import net.minecraft.world.WorldServer;
import net.minecraft.world.World;
import net.minecraft.world.Teleporter;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSender;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;

import com.youranserver.mod.world.WorldHome;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureYouranCommand extends ElementsYouranmodMod.ModElement {
	public ProcedureYouranCommand(ElementsYouranmodMod instance) {
		super(instance, 31);
	}

	public static String executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure YouranCommand!");
			return "";
		}
		if (dependencies.get("cmdparams") == null) {
			System.err.println("Failed to load dependency cmdparams for procedure YouranCommand!");
			return "";
		}
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure YouranCommand!");
			return "";
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure YouranCommand!");
			return "";
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure YouranCommand!");
			return "";
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure YouranCommand!");
			return "";
		}
		Entity entity = (Entity) dependencies.get("entity");
		HashMap cmdparams = (HashMap) dependencies.get("cmdparams");
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		String returnvalue = "";
		if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("website"))) {
			returnvalue = (String) "\u60A0\u7136\u670D\u52A1\u5668\uFF01";
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
						return new BlockPos((int) x, (int) y, (int) z);
					}

					@Override
					public Vec3d getPositionVector() {
						return new Vec3d(x, y, z);
					}
				}, "tellraw @p[r=1] [{\"text\":\"\u670D\u52A1\u5668\u5B98\u7F51\uFF1A\"},{\"text\":\"mc.ireading.store\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://mc.ireading.store/\"},\"color\":\"dark_blue\",\"underlined\":\"true\"}]");
			}
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("saveD"))) {
			if ((entity.canUseCommand((int) 2, ""))) {
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
							return new BlockPos((int) x, (int) y, (int) z);
						}

						@Override
						public Vec3d getPositionVector() {
							return new Vec3d(x, y, z);
						}
					}, "/effect @e[type=scp:dpersonnel] minecraft:instant_health 1 100");
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
							"\u00A72\u6210\u529F\u6267\u884C\uFF1A/effect @e[type=scp:dpersonnel] minecraft:instant_health 1 100"), (false));
				}
			} else {
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A74\u60A8\u7684\u64CD\u4F5C\u6743\u9650\u7B49\u7EA7\u4E0D\u8DB3\uFF01"), (false));
				}
			}
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("serverhelp"))) {
			returnvalue = (String) "\u60A0\u7136\u670D\u52A1\u5668\u624B\u518C\uFF01";
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
						return new BlockPos((int) x, (int) y, (int) z);
					}

					@Override
					public Vec3d getPositionVector() {
						return new Vec3d(x, y, z);
					}
				}, "tellraw @p[r=1] [{\"text\":\"\u670D\u52A1\u5668\u624B\u518C\uFF1A\"},{\"text\":\"https://www.yuque.com/youranserver/book\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.yuque.com/youranserver/book\"},\"color\":\"dark_blue\",\"underlined\":\"true\"}]");
			}
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("maincity"))) {
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A7e\u6B63\u5728\u524D\u5F80\u4E3B\u57CE......."), (false));
			}
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				ProcedureTp_to_maincity.executeProcedure($_dependencies);
			}
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("help"))) {
			returnvalue = (String) "114514";
			if ((entity.canUseCommand((int) 2, ""))) {
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A7e\u6307\u4EE4\u4F7F\u7528\u5E2E\u52A9\uFF1A"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran about - \u5173\u4E8E\u670D\u52A1\u5668"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran back - \u4ECE\u4E3B\u57CE\u8FD4\u56DE\u4E3B\u4E16\u754C"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity)
							.sendStatusMessage(new TextComponentString("\u00A76youran help - \u663E\u793A\u8BE5\u5E2E\u52A9\u4FE1\u606F"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran maincity - \u524D\u5F80\u4E3B\u57CE"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran saveD - \u7ED9D\u7EA7\u4EBA\u5458\u56DE\u8840"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran serverhelp - \u524D\u5F80\u670D\u52A1\u5668\u7F51\u9875\u624B\u518C"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran temperature - \u83B7\u53D6\u6240\u5904\u5730\u76EE\u524D\u7684\u6E29\u5EA6"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
							"\u00A76youran tp - \u4F20\u9001\u81F3\u4E00\u4E9B\u5730\u65B9\uFF08\u6CE8\uFF1Ayouran tp list \u53EF\u5217\u51FA\u5F53\u524D\u6240\u6709\u4F20\u9001\u70B9\uFF09"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran website - \u524D\u5F80\u670D\u52A1\u5668\u5B98\u7F51"), (false));
				}
			} else {
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A7e\u6307\u4EE4\u4F7F\u7528\u5E2E\u52A9\uFF1A"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran about - \u5173\u4E8E\u670D\u52A1\u5668"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran back - \u4ECE\u4E3B\u57CE\u8FD4\u56DE\u4E3B\u4E16\u754C"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity)
							.sendStatusMessage(new TextComponentString("\u00A76youran help - \u663E\u793A\u8BE5\u5E2E\u52A9\u4FE1\u606F"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran maincity - \u524D\u5F80\u4E3B\u57CE"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran serverhelp - \u524D\u5F80\u670D\u52A1\u5668\u7F51\u9875\u624B\u518C"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran temperature - \u83B7\u53D6\u6240\u5904\u5730\u76EE\u524D\u7684\u6E29\u5EA6"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
							"\u00A76youran tp - \u4F20\u9001\u81F3\u4E00\u4E9B\u5730\u65B9\uFF08\u6CE8\uFF1Ayouran tp list \u53EF\u5217\u51FA\u5F53\u524D\u6240\u6709\u4F20\u9001\u70B9\uFF09"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran website - \u524D\u5F80\u670D\u52A1\u5668\u5B98\u7F51"), (false));
				}
			}
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("about"))) {
			returnvalue = (String) "1919810";
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
						"\u60A0\u7136\u670D\u52A1\u5668\u662F\u4E00\u4E2A\u4E3B\u6253\u4F11\u95F2\u3001\u5192\u9669\u5411\u7684Minecraft 1.12.2\u7248\u672CForge mod + SpongeForge\u670D\u52A1\u5668\u3002"),
						(false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
						"\u4E5F\u5C31\u662F\u8BF4\uFF0C\u6211\u4EEC\u670D\u52A1\u5668\u662F\u4E00\u4E2A\u63D2\u4EF6+\u6A21\u7EC4\u670D\u52A1\u5668"),
						(false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
						"\u6211\u4EEC\u670D\u52A1\u5668\u7684\u9B54\u6539\u5F88\u5C11\uFF0C\u4E3B\u8981\u662F\u5BF9\u4E8E\u4E00\u4E9B\u7EC6\u8282\u65B9\u9762\u7684\u4FEE\u6539"),
						(false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
						"\u5982\u679C\u4F60\u60F3\u4E86\u89E3\u66F4\u591A\u4FE1\u606F\uFF0C\u8BF7\u70B9\u51FB\u4E0B\u9762\u7684\u94FE\u63A5\uFF1A"),
						(false));
			}
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
						return new BlockPos((int) x, (int) y, (int) z);
					}

					@Override
					public Vec3d getPositionVector() {
						return new Vec3d(x, y, z);
					}
				}, "tellraw @p[r=1] [{\"text\":\"\u670D\u52A1\u5668\u5B98\u7F51 - \"},{\"text\":\"mc.ireading.store\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://mc.ireading.store/\"},\"color\":\"dark_blue\",\"underlined\":\"true\"}]");
			}
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
						return new BlockPos((int) x, (int) y, (int) z);
					}

					@Override
					public Vec3d getPositionVector() {
						return new Vec3d(x, y, z);
					}
				}, "tellraw @p[r=1] [{\"text\":\"\u670D\u52A1\u5668MC\u767E\u79D1\u9875\u9762 - \"},{\"text\":\"play.mcmod.cn/sv20185890.html\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://play.mcmod.cn/sv20185890.html\"},\"color\":\"dark_blue\",\"underlined\":\"true\"}]");
			}
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
						return new BlockPos((int) x, (int) y, (int) z);
					}

					@Override
					public Vec3d getPositionVector() {
						return new Vec3d(x, y, z);
					}
				}, "tellraw @p[r=1] [{\"text\":\"\u670D\u52A1\u5668\u624B\u518C - \"},{\"text\":\"https://www.yuque.com/youranserver/book\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.yuque.com/youranserver/book\"},\"color\":\"dark_blue\",\"underlined\":\"true\"}]");
			}
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("temperature"))) {
			returnvalue = (String) (""
					+ ((world.getBiome(new BlockPos((int) x, (int) y, (int) z)).getTemperature(new BlockPos((int) x, (int) y, (int) z)) * 100.f)));
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString((("\u5F53\u5730\u6C14\u6E29\uFF1A\u00A73") + ""
						+ ((world.getBiome(new BlockPos((int) x, (int) y, (int) z)).getTemperature(new BlockPos((int) x, (int) y, (int) z))
								* 100.f)))),
						(false));
			}
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("back"))) {
			returnvalue = (String) "Retrun to World";
			if (((entity.dimension) == (WorldHome.DIMID))) {
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A7e\u6B63\u5728\u8FD4\u56DE\u4E3B\u4E16\u754C......."),
							(false));
				}
				entity.setPositionAndUpdate((-291), 64, 108);
				{
					Entity _ent = entity;
					if (!_ent.world.isRemote && !_ent.isRiding() && !_ent.isBeingRidden() && _ent instanceof EntityPlayerMP) {
						int dimensionID = 0;
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
						_player.mcServer.getPlayerList().transferPlayerToDimension(_player, dimensionID,
								new TeleporterDirect(_player.getServerWorld()));
						_player.connection.setPlayerLocation(DimensionManager.getWorld(dimensionID).getSpawnPoint().getX(),
								DimensionManager.getWorld(dimensionID).getSpawnPoint().getY() + 1,
								DimensionManager.getWorld(dimensionID).getSpawnPoint().getZ(), _player.rotationYaw, _player.rotationPitch);
					}
				}
			} else {
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
							"\u00A74\u60A8\u4E0D\u5728\u4E3B\u57CE\u7EF4\u5EA6\uFF0C\u65E0\u6CD5\u8FD4\u56DE\u4E3B\u4E16\u754C"), (false));
				}
			}
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("test"))) {
			returnvalue = (String) "debug";
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("tp"))) {
			returnvalue = (String) "TP";
			if ((((new Object() {
				public String getText() {
					String param = (String) cmdparams.get("1");
					if (param != null) {
						return param;
					}
					return "";
				}
			}.getText())).equals("help"))) {
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A7e\u60A0\u7136\u670D\u52A1\u5668TP\u5E2E\u52A9"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran tp help - \u83B7\u53D6\u8BE5\u5E2E\u52A9"),
							(false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76youran tp list - \u5217\u51FA\u6240\u6709\u4F20\u9001\u70B9"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
							"\u00A76youran tp <\u4F20\u9001\u70B9\u540D\u79F0> - \u4F20\u9001\u81EA\u5DF1\u81F3\u6307\u5B9A\u7684\u4F20\u9001\u70B9"),
							(false));
				}
			} else if ((((new Object() {
				public String getText() {
					String param = (String) cmdparams.get("1");
					if (param != null) {
						return param;
					}
					return "";
				}
			}.getText())).equals("list"))) {
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A7e\u60A0\u7136\u670D\u52A1\u5668\u4F20\u9001\u70B9\u5217\u8868"), (false));
				}
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A76\u5E9F\u7269 - \u4E3B\u57CE\u7EF4\u5EA6\uFF0C\u5E9F\u7269\u5C9B\u5C7F"), (false));
				}
			} else if ((((new Object() {
				public String getText() {
					String param = (String) cmdparams.get("1");
					if (param != null) {
						return param;
					}
					return "";
				}
			}.getText())).equals("\u5E9F\u7269"))) {
				if ((((entity instanceof EntityPlayerMP) && ((entity).world instanceof WorldServer))
						? ((EntityPlayerMP) entity).getAdvancements()
								.getProgress(((WorldServer) (entity).world).getAdvancementManager()
										.getAdvancement(new ResourceLocation("youranmod:a_spcial_village")))
								.isDone()
						: false)) {
					if (entity instanceof EntityPlayer && !entity.world.isRemote) {
						((EntityPlayer) entity).sendStatusMessage(
								new TextComponentString("\u00A72\u6B63\u5728\u4F20\u9001\u81F3\u201C\u4E3B\u57CE-\u5E9F\u7269\u201D"), (false));
					}
					{
						Map<String, Object> $_dependencies = new HashMap<>();
						$_dependencies.put("entity", entity);
						ProcedureTptoFeiwu.executeProcedure($_dependencies);
					}
				} else {
					if (entity instanceof EntityPlayer && !entity.world.isRemote) {
						((EntityPlayer) entity).sendStatusMessage(new TextComponentString(
								"\u00A74\u60A8\u5C1A\u672A\u5B8C\u6210\u524D\u7F6E\u6210\u5C31\uFF01\u65E0\u6CD5\u4F7F\u7528\u8BE5\u4F20\u9001\u70B9\uFF01"),
								(false));
					}
				}
			} else {
				if (entity instanceof EntityPlayer && !entity.world.isRemote) {
					((EntityPlayer) entity).sendStatusMessage(
							new TextComponentString("\u00A74\u8FD0\u884C\u201C/youran tp help\u201D\u6765\u83B7\u53D6\u6307\u4EE4\u5E2E\u52A9"),
							(false));
				}
			}
		} else {
			returnvalue = (String) "What are you doing?";
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A7e\u6307\u4EE4\u4F7F\u7528\u5E2E\u52A9\uFF1A"), (false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran about - \u5173\u4E8E\u670D\u52A1\u5668"), (false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity)
						.sendStatusMessage(new TextComponentString("\u00A76youran back - \u4ECE\u4E3B\u57CE\u8FD4\u56DE\u4E3B\u4E16\u754C"), (false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran help - \u663E\u793A\u5E2E\u52A9\u4FE1\u606F"),
						(false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran maincity - \u524D\u5F80\u4E3B\u57CE"), (false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(
						new TextComponentString("\u00A76youran serverhelp - \u524D\u5F80\u670D\u52A1\u5668\u7F51\u9875\u624B\u518C"), (false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(
						new TextComponentString("\u00A76youran temperature - \u83B7\u53D6\u6240\u5904\u5730\u76EE\u524D\u7684\u6E29\u5EA6"), (false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString("\u00A76youran tp - \u4F20\u9001\u81F3\u4E00\u4E9B\u5730\u65B9"),
						(false));
			}
			if (entity instanceof EntityPlayer && !entity.world.isRemote) {
				((EntityPlayer) entity)
						.sendStatusMessage(new TextComponentString("\u00A76youran website - \u524D\u5F80\u670D\u52A1\u5668\u5B98\u7F51"), (false));
			}
		}
		if (((((entity instanceof EntityPlayerMP) && ((entity).world instanceof WorldServer))
				? ((EntityPlayerMP) entity).getAdvancements()
						.getProgress(((WorldServer) (entity).world).getAdvancementManager()
								.getAdvancement(new ResourceLocation("youranmod:run_youran_command")))
						.isDone()
				: false) == (false))) {
			if (entity instanceof EntityPlayerMP) {
				Advancement _adv = ((MinecraftServer) ((EntityPlayerMP) entity).mcServer).getAdvancementManager()
						.getAdvancement(new ResourceLocation("youranmod:run_youran_command"));
				AdvancementProgress _ap = ((EntityPlayerMP) entity).getAdvancements().getProgress(_adv);
				if (!_ap.isDone()) {
					Iterator _iterator = _ap.getRemaningCriteria().iterator();
					while (_iterator.hasNext()) {
						String _criterion = (String) _iterator.next();
						((EntityPlayerMP) entity).getAdvancements().grantCriterion(_adv, _criterion);
					}
				}
			}
		}
		return (returnvalue);
	}
}
