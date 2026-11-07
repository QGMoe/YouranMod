package moe.qingu.youran.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** 创造模式物品栏“悠然服务器”（翻译键 itemGroup.tabyou_ran_fu_wu_qi），图标是悠然服务器 Logo，没有搜索框。 */
public final class YouranTab {
    public static CreativeTabs tab;

    private YouranTab() {
    }

    /** 预初始化时调用（物品注册之前） */
    public static void create() {
        tab = new CreativeTabs("tabyou_ran_fu_wu_qi") {
            @SideOnly(Side.CLIENT)
            @Override
            public ItemStack createIcon() {
                return new ItemStack(ModItems.youranLogo);
            }

            @SideOnly(Side.CLIENT)
            @Override
            public boolean hasSearchBar() {
                return false;
            }
        };
    }
}
