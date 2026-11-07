package moe.qingu.youran.item;

import com.google.common.collect.ImmutableSet;
import moe.qingu.youran.registry.YouranTab;
import moe.qingu.youran.server.ItemEffects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Set;

/**
 * 来自异世之剑（youranmod:primogem_sword）。材料：挖掘等级 4、耐久 4000、效率 20、伤害 8（剑 3+8=11）、附魔能力 50。
 * <p>
 * 1.3.4 还在主手属性里又加了一遍伤害与攻速（-2），但用的是原版同一个 UUID，放不进原版已有的那一份（集合里按 UUID 去重），
 * 实际生效的是原版剑的攻速 -2.4；这里不再写那段，结果相同。
 * <p>
 * 打中生物时（1.3.4 的 Primogemsword_Attack_Mobs）：30% 在目标处掉 1–3 点经验球；50% 给目标缓慢 III 5 tick；
 * 10% 给目标附近 10 格内最近的玩家瞬间治疗 II。
 */
public class ItemPrimogemSword extends ItemSword {
    public ItemPrimogemSword() {
        super(EnumHelper.addToolMaterial("PRIMOGEM_SWORD", 4, 4000, 20f, 8f, 50));
        setRegistryName("primogem_sword");
        setTranslationKey("primogem_sword");
        setCreativeTab(YouranTab.tab);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("sword");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        super.hitEntity(stack, target, attacker);
        World world = target.world;
        int x = (int) target.posX;
        int y = (int) target.posY;
        int z = (int) target.posZ;
        // 三次独立的概率判定，顺序同 1.3.4。缓慢那一项 1.3.4 没有限定服务端（玩家互打时客户端也会算一次，是预测），照旧；
        // 另外两项只在服务端（概率是服务端配置）
        double xpRoll = world.rand.nextDouble();
        if (!world.isRemote && xpRoll < ItemEffects.swordXpChance()) {
            ItemEffects.swordXp(world, x, y, z);
        }
        if (world.rand.nextDouble() < 0.5) {
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5, 2, false, false));
        }
        double healRoll = world.rand.nextDouble();
        if (!world.isRemote && healRoll < ItemEffects.swordHealChance()) {
            ItemEffects.swordHeal(world, x, y, z);
        }
        return true;
    }
}
