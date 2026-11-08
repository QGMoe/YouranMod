package moe.qingu.youran.item;

import moe.qingu.youran.logic.SharedSettings;
import moe.qingu.youran.logic.SharedState;
import moe.qingu.youran.registry.ModItems;
import moe.qingu.youran.registry.YouranTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

/**
 * 矿泉水、饮用水：喝的动作，随时可以喝，喝完还一个矿泉水瓶。补多少水分由 TAN 的配置（drink_stats.json）定。
 * <p>
 * 补的饥饿值与饱和度是配置（{@code config.ItemConfig}），同步给客户端（喝的时候两端都加，客户端是预测）。
 * 原版取这两个值的方法不带世界，所以按当前线程是哪一端取（{@code getEffectiveSide}）。
 */
public class ItemDrink extends ItemFood {
    private final ToIntFunction<SharedSettings> hunger;
    private final ToDoubleFunction<SharedSettings> saturation;

    public ItemDrink(String name, ToIntFunction<SharedSettings> hunger, ToDoubleFunction<SharedSettings> saturation, boolean wolfFood) {
        super(hunger.applyAsInt(SharedSettings.DEFAULTS), (float) saturation.applyAsDouble(SharedSettings.DEFAULTS), wolfFood);
        this.hunger = hunger;
        this.saturation = saturation;
        setRegistryName(name);
        setTranslationKey(name);
        setAlwaysEdible();
        setCreativeTab(YouranTab.tab);
        setMaxStackSize(64);
    }

    private static SharedSettings current() {
        return SharedState.get(FMLCommonHandler.instance().getEffectiveSide().isClient());
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        return hunger.applyAsInt(current());
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        return (float) saturation.applyAsDouble(current());
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
