package moe.qingu.youran.server;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

/** 药水效果（1.3.4 用 /effect 指令给的） */
final class Effects {
    private Effects() {
    }

    /**
     * 同 {@code /effect <实体> instant_health 1 <等级>}：瞬间效果的持续时间按指令的规则就是 1（tick），显示粒子。
     * 等级 n 回 4×2^n 点（Java 位移只取低 5 位，等级 100 回 64 点）。
     */
    static void instantHealth(EntityLivingBase entity, int amplifier) {
        entity.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, amplifier, false, true));
    }
}
