package moe.qingu.youran.server;

import moe.qingu.youran.config.ItemConfig;
import moe.qingu.youran.logic.RadiusTargets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/** 物品效果里只在服务端做的部分（物品类里在 {@code !world.isRemote} 时调用） */
public final class ItemEffects {
    private ItemEffects() {
    }

    // ---- 来自异世之剑 ----

    public static double swordXpChance() {
        return ItemConfig.swordXpChance;
    }

    public static double swordHealChance() {
        return ItemConfig.swordHealChance;
    }

    /** 在目标处（截断成整数的坐标）掉 1–3 点经验球 */
    public static void swordXp(World world, int x, int y, int z) {
        int value = (int) Math.ceil(ItemConfig.swordXpMax * world.rand.nextDouble());
        world.spawnEntity(new EntityXPOrb(world, x, y, z, value));
    }

    /** 给目标附近最近的玩家瞬间治疗（1.3.4：effect @p[r=10] instant_health 1 1，从目标处执行） */
    public static void swordHeal(World world, int x, int y, int z) {
        EntityPlayer player = EntityQueries.nearestPlayerWithin(world, x, y, z, ItemConfig.swordHealRadius);
        if (player != null) {
            Effects.instantHealth(player, ItemConfig.swordHealAmplifier);
        }
    }

    // ---- 血红宝石 ----

    /** 杀死附近的 SCP-106 等（1.3.4：kill @e[type=scp:old_man,r=10] 与 kill @e[type=scp:old_man_d,r=5]），再伤到自己 */
    public static void redBloodGem(World world, EntityPlayer player, int x, int y, int z) {
        for (RadiusTargets.Target target : Settings.redBloodGemTargets) {
            for (Entity entity : EntityQueries.ofTypeWithin(world, new ResourceLocation(target.entity), x, y, z, target.radius)) {
                // 同 /kill：生物受到“虚空”伤害（数值最大），其他实体直接移除
                entity.onKillCommand();
            }
        }
        player.attackEntityFrom(DamageSource.MAGIC, (float) ItemConfig.redBloodGemSelfDamage);
    }
}
