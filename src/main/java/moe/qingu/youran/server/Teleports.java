package moe.qingu.youran.server;

import moe.qingu.youran.config.TeleportConfig;
import moe.qingu.youran.logic.Texts;
import moe.qingu.youran.world.DirectTeleporter;
import moe.qingu.youran.world.MainCity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;

/**
 * 指令传送（1.3.4 的 Tp_to_maincity、TptoFeiwu 与 /youran back 里的那段），做法照 1.3.4：
 * <ul>
 *   <li>去主城：已在主城维度就直接设位置；否则（玩家时）先换到主城维度并放到出生点上方一格，再设到传送点；</li>
 *   <li>回主世界：先在主城维度里设到 back 坐标，再换到主世界、放到出生点上方一格。</li>
 * </ul>
 * 传送前先让执行者下坐骑、让骑在它身上的下来（服主 11-07）。1.3.4 骑乘时不换维度却仍设位置，骑着东西用 /hub
 * 会被设到当前维度的传送点坐标、/youran back 会停在主城维度的 back 坐标。原版换维度带不走坐骑，所以先下来再传送。
 * 实体不是玩家时（如 /execute 让别的实体执行）照 1.3.4 只设位置。
 */
public final class Teleports {
    private Teleports() {
    }

    /** 去主城传送点 */
    public static void toMainCity(Entity entity) {
        toPointInMainCity(entity, TeleportConfig.mainCityX, TeleportConfig.mainCityY, TeleportConfig.mainCityZ);
    }

    /** 去垃圾回收站岛屿 */
    public static void toRecycleStation(Entity entity) {
        toPointInMainCity(entity, TeleportConfig.recycleStationX, TeleportConfig.recycleStationY, TeleportConfig.recycleStationZ);
    }

    private static void toPointInMainCity(Entity entity, double x, double y, double z) {
        if (!MainCity.isAvailable()) {
            Chat.tell(entity, Texts.MAIN_CITY_UNAVAILABLE);
            return;
        }
        dismount(entity);
        if (entity.dimension == MainCity.DIM_ID) {
            entity.setPositionAndUpdate(x, y, z);
            return;
        }
        if (canChangeDimension(entity)) {
            changeDimensionToSpawn((EntityPlayerMP) entity, MainCity.DIM_ID);
        }
        entity.setPositionAndUpdate(x, y, z);
    }

    /** /youran back（调用方已确认在主城维度） */
    public static void back(Entity entity) {
        dismount(entity);
        entity.setPositionAndUpdate(TeleportConfig.backX, TeleportConfig.backY, TeleportConfig.backZ);
        if (!canChangeDimension(entity)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) entity;
        if (TeleportConfig.backToWorldSpawn) {
            changeDimensionToSpawn(player, 0);
        } else {
            player.server.getPlayerList().transferPlayerToDimension(player, 0, new DirectTeleporter(player.getServerWorld()));
            player.connection.setPlayerLocation(TeleportConfig.backX, TeleportConfig.backY, TeleportConfig.backZ,
                    player.rotationYaw, player.rotationPitch);
        }
    }

    /** 下坐骑，骑在身上的也都下来 */
    private static void dismount(Entity entity) {
        if (entity.isRiding()) {
            entity.dismountRidingEntity();
        }
        if (entity.isBeingRidden()) {
            entity.removePassengers();
        }
    }

    private static boolean canChangeDimension(Entity entity) {
        return !entity.world.isRemote && !entity.isRiding() && !entity.isBeingRidden() && entity instanceof EntityPlayerMP;
    }

    /** 换维度（不找传送门），放到那个维度出生点上方一格（出生点是方块坐标，落在方块的角上，同 1.3.4） */
    private static void changeDimensionToSpawn(EntityPlayerMP player, int dimension) {
        // 同 1.3.4：传送器建在出发的那个世界上（它什么也不做，建在哪里都一样）
        player.server.getPlayerList().transferPlayerToDimension(player, dimension, new DirectTeleporter(player.getServerWorld()));
        BlockPos spawn = DimensionManager.getWorld(dimension).getSpawnPoint();
        player.connection.setPlayerLocation(spawn.getX(), spawn.getY() + 1, spawn.getZ(), player.rotationYaw, player.rotationPitch);
    }
}
