package moe.qingu.youran.registry;

import moe.qingu.youran.item.ItemDrink;
import moe.qingu.youran.item.ItemHome;
import moe.qingu.youran.item.ItemIceArmor;
import moe.qingu.youran.item.ItemPrimogemSword;
import moe.qingu.youran.item.ItemPrimogemTools;
import moe.qingu.youran.item.ItemRedBloodGem;
import moe.qingu.youran.item.ItemWaterBottle;
import moe.qingu.youran.item.ItemWithTooltip;
import moe.qingu.youran.item.ItemYouranLogo;
import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 物品（含方块与流体方块的物品形式）。注册名是存档、CraftTweaker 脚本、TAN 配置里用的，一个都不能改。
 */
public final class ModItems {
    public static Item iceHelmet;
    public static Item iceBody;
    public static Item iceLegs;
    public static Item iceBoots;
    public static Item youranLogo;
    public static Item home;
    public static Item oneYuan;
    public static Item oneYuanCoin;
    public static Item fiveYuan;
    public static Item tenYuan;
    public static Item redBloodGem;
    public static Item primogem;
    public static Item primogemPickaxe;
    public static Item primogemAxe;
    public static Item primogemSword;
    public static Item primogemShovel;
    public static Item mineralWater;
    public static Item mineralWaterBottle;
    public static Item drinkingWater;

    /** 全部物品，按注册顺序（客户端登记模型用） */
    private static final List<Item> ALL = new ArrayList<>();

    private ModItems() {
    }

    public static List<Item> all() {
        return Collections.unmodifiableList(ALL);
    }

    static void register(IForgeRegistry<Item> registry) {
        // 顺序同 1.3.4（只影响新世界里的数字 ID 与创造模式物品栏里的排列）
        iceHelmet = add(registry, ItemIceArmor.piece(EntityEquipmentSlot.HEAD, "icekuijiahelmet"));
        iceBody = add(registry, ItemIceArmor.piece(EntityEquipmentSlot.CHEST, "icekuijiabody"));
        iceLegs = add(registry, ItemIceArmor.piece(EntityEquipmentSlot.LEGS, "icekuijialegs"));
        iceBoots = add(registry, ItemIceArmor.piece(EntityEquipmentSlot.FEET, "icekuijiaboots"));
        youranLogo = add(registry, new ItemYouranLogo());
        add(registry, itemBlock(ModBlocks.youranLogo));
        add(registry, itemBlock(ModBlocks.mainCityPortal));
        home = add(registry, new ItemHome());
        add(registry, itemBlock(ModBlocks.hardIce));
        oneYuan = add(registry, new ItemWithTooltip("oneyuan", 50, "一元悠然币"));
        oneYuanCoin = add(registry, new ItemWithTooltip("oneyuancoin", 50, "一元悠然币（硬币）"));
        fiveYuan = add(registry, new ItemWithTooltip("fiveyuan", 50, "五元悠然币"));
        tenYuan = add(registry, new ItemWithTooltip("tenyuan", 50, "十元悠然币"));
        redBloodGem = add(registry, new ItemRedBloodGem());
        add(registry, itemBlock(ModBlocks.primogemOre));
        add(registry, itemBlock(ModBlocks.sphaleriteOre));
        primogem = add(registry, new ItemWithTooltip("primogem", 64, "据说这是很久以前由流星带来的东西，经过漫长的地质演变而深埋地底"));
        primogemPickaxe = add(registry, ItemPrimogemTools.pickaxe());
        primogemAxe = add(registry, ItemPrimogemTools.axe());
        primogemSword = add(registry, new ItemPrimogemSword());
        primogemShovel = add(registry, ItemPrimogemTools.shovel());
        mineralWater = add(registry, new ItemDrink("mineral_water", s -> s.mineralWaterHunger, s -> s.mineralWaterSaturation, false));
        mineralWaterBottle = add(registry, new ItemWaterBottle());
        drinkingWater = add(registry, new ItemDrink("drinkingwater", s -> s.drinkingWaterHunger, s -> s.drinkingWaterSaturation, true));
        add(registry, itemBlock(ModBlocks.mineralWater));
        add(registry, itemBlock(ModBlocks.hydrargyrum));
        add(registry, itemBlock(ModBlocks.gallium));
        add(registry, itemBlock(ModBlocks.chlorine));
    }

    private static Item itemBlock(Block block) {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    private static Item add(IForgeRegistry<Item> registry, Item item) {
        registry.register(item);
        ALL.add(item);
        return item;
    }
}
