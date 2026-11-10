package moe.qingu.youran.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.3.4 用目标选择器（{@code @e[type=…,r=…]}、{@code @p[r=…]}）找的实体，这里直接找，规则照原版 1.12.2 的 EntitySelector：
 * 只算活着的；半径从“执行位置”（1.3.4 是截断成整数的坐标，即方块的角）算到实体的脚下；
 * 找不到这种实体类型（没装那个模组）时什么也找不到。
 */
public final class EntityQueries {
    private EntityQueries() {
    }

    /** {@code @e[type=<type>,r=<radius>]}：这个世界里、半径内的这种实体 */
    public static List<Entity> ofTypeWithin(World world, ResourceLocation type, int x, int y, int z, int radius) {
        AxisAlignedBB box = new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius + 1, y + radius + 1, z + radius + 1);
        double max = Math.max(radius, 1.0E-4D);
        double maxSq = max * max;
        Vec3d center = new Vec3d(x, y, z);
        return world.getEntitiesWithinAABB(Entity.class, box, e -> e != null && e.isEntityAlive()
                && EntityList.isMatchingName(e, type) && center.squareDistanceTo(e.posX, e.posY, e.posZ) <= maxSq);
    }

    /** {@code @e[type=<type>]}（没有位置参数）：所有已加载维度里的这种实体 */
    public static List<Entity> ofTypeEverywhere(WorldServer[] worlds, ResourceLocation type) {
        List<Entity> found = new ArrayList<>();
        for (WorldServer world : worlds) {
            if (world != null) {
                found.addAll(world.getEntities(Entity.class, e -> e != null && e.isEntityAlive() && EntityList.isMatchingName(e, type)));
            }
        }
        return found;
    }

    /** {@code @p[r=<radius>]}：这个世界里、半径内最近的玩家（一样近时取先进服的） */
    @Nullable
    public static EntityPlayer nearestPlayerWithin(World world, int x, int y, int z, int radius) {
        double max = Math.max(radius, 1.0E-4D);
        double maxSq = max * max;
        Vec3d center = new Vec3d(x, y, z);
        EntityPlayer best = null;
        double bestSq = 0;
        for (EntityPlayer player : world.playerEntities) {
            if (!player.isEntityAlive()) {
                continue;
            }
            double sq = center.squareDistanceTo(player.posX, player.posY, player.posZ);
            if (sq > maxSq) {
                continue;
            }
            double distanceSq = player.getDistanceSq(center.x, center.y, center.z);
            if (best == null || distanceSq < bestSq) {
                best = player;
                bestSq = distanceSq;
            }
        }
        return best;
    }
}
