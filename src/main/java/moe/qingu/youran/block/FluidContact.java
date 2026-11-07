package moe.qingu.youran.block;

import moe.qingu.youran.logic.ChlorineProtection;
import moe.qingu.youran.logic.ReactiveIngots;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * 碰到汞、镓、氯气时的效果（1.3.4 的 HydrargyrumPoison、GalliumTouch、ChlorineTouch）。每个 tick 只要实体碰着这格流体就执行一次，
 * 两端都执行。这些数值客户端也要用（预测），所以没有做成配置。
 */
public final class FluidContact {
    /** 1.3.4 碰到汞时给实体写的标记（之后没有地方读它；照旧写，存档里的数据不变） */
    public static final String MERCURY_MARK = "hydrargyrum_poison";

    private FluidContact() {
    }

    /** 汞：缓慢 VI 1 tick；在实体数据里写标记；掉落物是活泼金属锭的会爆炸 */
    public static void mercury(World world, BlockPos pos, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 5, false, false));
        }
        entity.getEntityData().setDouble(MERCURY_MARK, 1);
        if (entity instanceof EntityItem) {
            explodeReactiveIngot(world, pos, (EntityItem) entity);
        }
    }

    /** 镓：缓慢 IV 1 tick；掉落物是活泼金属锭的会爆炸 */
    public static void gallium(World world, BlockPos pos, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 3, false, false));
        }
        if (entity instanceof EntityItem) {
            explodeReactiveIngot(world, pos, (EntityItem) entity);
        }
    }

    /** 氯气：生物中毒 III 15 秒；戴 IC2 防化头盔或穿 SCP 防护服的玩家不受影响 */
    public static void chlorine(World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof EntityLivingBase)) {
            return;
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            String head = String.valueOf(player.inventory.armorInventory.get(3).getItem().getRegistryName());
            String chest = String.valueOf(player.inventory.armorInventory.get(2).getItem().getRegistryName());
            if (ChlorineProtection.isProtected(head, chest)) {
                return;
            }
        }
        ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 300, 2, false, false));
    }

    /**
     * 掉落物的矿物词典名是活泼金属锭时，在这格流体的位置爆炸（不破坏方块）并去掉掉落物。
     * 判断规则（含 1.3.4 只看第一个词典名的问题）见 {@link ReactiveIngots#explosionStrength}。
     */
    private static void explodeReactiveIngot(World world, BlockPos pos, EntityItem item) {
        ItemStack stack = item.getItem();
        int[] ids = OreDictionary.getOreIDs(stack);
        List<String> names = new ArrayList<>(ids.length);
        for (int id : ids) {
            names.add(OreDictionary.getOreName(id));
        }
        float strength = ReactiveIngots.explosionStrength(names);
        if (strength < 0) {
            return;
        }
        world.createExplosion(item, pos.getX(), pos.getY(), pos.getZ(), strength, false);
        item.setDead();
    }
}
