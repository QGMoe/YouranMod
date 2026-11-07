package moe.qingu.youran.item;

import moe.qingu.youran.registry.ModBlocks;
import moe.qingu.youran.registry.YouranTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 奇怪的工具（youranmod:home），64 点耐久：对着悠然 Logo 块框架里面点一下，点燃主城传送门。
 * 同 1.3.4：点在哪一面就看那一面外面那一格，是空气才试着点燃；不论点没点燃都掉 1 点耐久；两端都执行。
 */
public class ItemHome extends Item {
    public ItemHome() {
        setRegistryName("home");
        setTranslationKey("home");
        setMaxStackSize(1);
        setMaxDamage(64);
        setCreativeTab(YouranTab.tab);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
                                      float hitX, float hitY, float hitZ) {
        BlockPos target = pos.offset(facing);
        ItemStack stack = player.getHeldItem(hand);
        if (!player.canPlayerEdit(target, facing, stack)) {
            return EnumActionResult.FAIL;
        }
        if (world.isAirBlock(target)) {
            ModBlocks.mainCityPortal.tryIgnite(world, target);
        }
        stack.damageItem(1, player);
        return EnumActionResult.SUCCESS;
    }
}
