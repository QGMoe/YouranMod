package moe.qingu.youran.server;

import moe.qingu.youran.config.DPersonnelConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

/** 给 SCP 模组的 D 级人员回血（1.3.4：/effect @e[type=scp:dpersonnel] minecraft:instant_health 1 <等级>） */
public final class DPersonnel {
    private DPersonnel() {
    }

    /** 所有已加载维度里活着的 D 级人员；没装 SCP 时什么也不做 */
    public static void healAll(MinecraftServer server, int amplifier) {
        ResourceLocation type = new ResourceLocation(DPersonnelConfig.entity);
        for (Entity entity : EntityQueries.ofTypeEverywhere(server.worlds, type)) {
            if (entity instanceof EntityLivingBase) {
                Effects.instantHealth((EntityLivingBase) entity, amplifier);
            }
        }
    }
}
