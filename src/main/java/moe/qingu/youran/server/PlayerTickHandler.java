package moe.qingu.youran.server;

import moe.qingu.youran.config.TeleportConfig;
import moe.qingu.youran.logic.Texts;
import moe.qingu.youran.registry.ModItems;
import moe.qingu.youran.util.Advancements;
import moe.qingu.youran.world.MainCity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 每个玩家每 tick（服务端，tick 结束时）：1.3.4 的 RunningInPlayerTick。
 * <ul>
 *   <li>进度：“悠然服务器”（进服就给）、“用冰块包裹我”（穿齐冰制盔甲）、“Money!”（身上有任一种悠然币）、
 *   “这可比钻石要强太多了”（身上有任一种没用过的来自异世的工具）；</li>
 *   <li>在主城维度里：掉进虚空（y ≤ 0）传回主城；走进垃圾回收站岛屿时给进度并提示传送点已解锁。</li>
 * </ul>
 * 1.3.4 每 tick 先翻一遍物品栏再看进度完成没有；这里先看进度（一次查表），没完成的才翻物品栏，结果相同。
 * 物品栏的判断照 1.3.4 用 {@code InventoryPlayer.hasItemStack}：比较物品与损耗值，所以用过（掉了耐久）的工具不算。
 */
public final class PlayerTickHandler {
    private ItemStack[] coins;
    private ItemStack[] tools;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.side != Side.SERVER || !(event.player instanceof EntityPlayerMP)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        if (coins == null) {
            coins = stacks(ModItems.oneYuan, ModItems.oneYuanCoin, ModItems.fiveYuan, ModItems.tenYuan);
            tools = stacks(ModItems.primogemPickaxe, ModItems.primogemAxe, ModItems.primogemSword, ModItems.primogemShovel);
        }
        if (!Advancements.isDone(player, Advancements.GET_ICE_FULL) && wearsFullIceArmor(player)) {
            Advancements.grant(player, Advancements.GET_ICE_FULL);
        }
        Advancements.grant(player, Advancements.YOURAN);
        if (!Advancements.isDone(player, Advancements.WALLET) && hasAny(player, coins)) {
            Advancements.grant(player, Advancements.WALLET);
        }
        if (!Advancements.isDone(player, Advancements.BETTER_THAN_DIAMOND) && hasAny(player, tools)) {
            Advancements.grant(player, Advancements.BETTER_THAN_DIAMOND);
        }
        if (MainCity.contains(player)) {
            // 1.3.4 用 (int) 截断，不是 floor
            if ((int) player.posY <= TeleportConfig.voidRescueY) {
                Teleports.toMainCity(player);
            }
            if (Settings.recycleArea.contains(player.posX, player.posY, player.posZ)
                    && !Advancements.isDone(player, Advancements.RECYCLING_STATION)) {
                Advancements.grant(player, Advancements.RECYCLING_STATION);
                Chat.tell(player, Texts.RECYCLE_STATION_UNLOCKED);
            }
        }
    }

    /** 盔甲栏 0–3 依次是脚、腿、胸、头；只比物品，不比耐久 */
    private static boolean wearsFullIceArmor(EntityPlayerMP player) {
        return player.inventory.armorInventory.get(0).getItem() == ModItems.iceBoots
                && player.inventory.armorInventory.get(1).getItem() == ModItems.iceLegs
                && player.inventory.armorInventory.get(2).getItem() == ModItems.iceBody
                && player.inventory.armorInventory.get(3).getItem() == ModItems.iceHelmet;
    }

    private static boolean hasAny(EntityPlayerMP player, ItemStack[] stacks) {
        for (ItemStack stack : stacks) {
            if (player.inventory.hasItemStack(stack)) {
                return true;
            }
        }
        return false;
    }

    private static ItemStack[] stacks(Item... items) {
        ItemStack[] stacks = new ItemStack[items.length];
        for (int i = 0; i < items.length; i++) {
            stacks[i] = new ItemStack(items[i]);
        }
        return stacks;
    }
}
