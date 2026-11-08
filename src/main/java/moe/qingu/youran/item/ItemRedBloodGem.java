package moe.qingu.youran.item;

import moe.qingu.youran.logic.SharedState;
import moe.qingu.youran.registry.YouranTab;
import moe.qingu.youran.server.ItemEffects;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 血红宝石（youranmod:red_blood_gem），60 点耐久，没用过的能叠 64 个。
 * <p>
 * 右键（1.3.4 的 KillOldMan）：在自己脚下劈一道闪电；杀死附近的 SCP-106（{@code scp:old_man}，10 格）与它的
 * D 级形态（{@code scp:old_man_d}，5 格）；自己受 5 点魔法伤害；宝石掉 1 点耐久（配置，同步给客户端），用完时少一个、
 * 下一个从满耐久开始。
 * 没有冷却。
 */
public class ItemRedBloodGem extends Item {
    public ItemRedBloodGem() {
        setRegistryName("red_blood_gem");
        setTranslationKey("red_blood_gem");
        setMaxDamage(60);
        setMaxStackSize(64);
        setCreativeTab(YouranTab.tab);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        // 同 1.3.4（右键不进入“使用中”，所以这个值实际不起作用）
        return 20;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> lines, ITooltipFlag flag) {
        super.addInformation(stack, world, lines, flag);
        lines.add("充满能量的血红色宝石");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ActionResult<ItemStack> result = super.onItemRightClick(world, player, hand);
        ItemStack stack = result.getResult();
        // 1.3.4 用的是截断成整数的坐标（不是方块中心）
        int x = (int) player.posX;
        int y = (int) player.posY;
        int z = (int) player.posZ;
        // 闪电两端都放（同 1.3.4：客户端立刻看到一道，服务端那道也会发过来）
        world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
        if (!world.isRemote) {
            ItemEffects.redBloodGem(world, player, x, y, z);
        }
        if (stack.attemptDamageItem(SharedState.get(world.isRemote).redBloodGemDurabilityCost, world.rand, null)) {
            stack.shrink(1);
            stack.setItemDamage(0);
        }
        return result;
    }
}
