package moe.qingu.youran.item;

import moe.qingu.youran.registry.YouranTab;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

/**
 * 冰制盔甲（youranmod:icekuijiahelmet / body / legs / boots）。材料：耐久系数 50，护甲 头 2 胸 6 腿 5 脚 2，
 * 附魔能力 10，韧性 0.2，贴图 youranmod:ice。
 * <p>
 * 穿戴音效：1.3.4 写的是 {@code SoundEvent.REGISTRY.getObject(new ResourceLocation(""))}，找不到，结果是没有音效（null）；照旧。
 * 隔热效果由 TAN 的配置（armor_temp_modifiers.json）定；穿齐一套给“用冰块包裹我”进度（见 server.PlayerTickHandler）。
 */
public final class ItemIceArmor {
    private static ItemArmor.ArmorMaterial material;

    private ItemIceArmor() {
    }

    public static Item piece(EntityEquipmentSlot slot, String name) {
        if (material == null) {
            material = EnumHelper.addArmorMaterial("ICEKUIJIA", "youranmod:ice", 50, new int[]{2, 5, 6, 2}, 10, null, 0.2f);
        }
        return new ItemArmor(material, 0, slot).setRegistryName(name).setTranslationKey(name).setCreativeTab(YouranTab.tab);
    }
}
