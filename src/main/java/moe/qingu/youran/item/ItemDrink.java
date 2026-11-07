package moe.qingu.youran.item;

import moe.qingu.youran.registry.ModItems;
import moe.qingu.youran.registry.YouranTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * 矿泉水、饮用水：喝的动作，随时可以喝，喝完还一个矿泉水瓶。补多少水分由 TAN 的配置（drink_stats.json）定。
 */
public class ItemDrink extends ItemFood {
    public ItemDrink(String name, int hunger, float saturation, boolean wolfFood) {
        super(hunger, saturation, wolfFood);
        setRegistryName(name);
        setTranslationKey(name);
        setAlwaysEdible();
        setCreativeTab(YouranTab.tab);
        setMaxStackSize(64);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        super.onFoodEaten(stack, world, player);
        // 两端都执行（同 1.3.4）
        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.mineralWaterBottle));
    }
}
