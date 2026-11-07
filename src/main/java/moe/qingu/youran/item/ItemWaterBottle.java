package moe.qingu.youran.item;

import moe.qingu.youran.registry.ModBlocks;
import moe.qingu.youran.registry.ModItems;
import moe.qingu.youran.registry.YouranTab;
import moe.qingu.youran.util.Advancements;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * 矿泉水瓶（youranmod:mineral_water_bottle）：对着矿泉水源头旁边的方块的那一面右键（视线穿过流体点到后面的方块），
 * 装一瓶矿泉水：给一瓶矿泉水、从物品栏里拿走一个瓶子、源头变成空气、给“可以开农夫山泉了！”进度。
 * <p>
 * 同 1.3.4：两端都执行（客户端那一侧是预测），手里拿着的是哪一个瓶子都行，拿走的是物品栏里找到的第一个瓶子。
 */
public class ItemWaterBottle extends Item {
    public ItemWaterBottle() {
        setRegistryName("mineral_water_bottle");
        setTranslationKey("mineral_water_bottle");
        setMaxStackSize(64);
        setCreativeTab(YouranTab.tab);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side,
                                           float hitX, float hitY, float hitZ, EnumHand hand) {
        EnumActionResult result = super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        BlockPos source = pos.offset(side);
        IBlockState state = world.getBlockState(source);
        if (state.getBlock() == ModBlocks.mineralWater && state.getValue(BlockFluidBase.LEVEL) == 0) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.mineralWater));
            player.inventory.clearMatchingItems(ModItems.mineralWaterBottle, -1, 1, null);
            world.setBlockState(source, Blocks.AIR.getDefaultState(), 3);
            if (player instanceof EntityPlayerMP) {
                Advancements.grant((EntityPlayerMP) player, Advancements.NONGFU_SHANQUAN);
            }
        }
        return result;
    }
}
