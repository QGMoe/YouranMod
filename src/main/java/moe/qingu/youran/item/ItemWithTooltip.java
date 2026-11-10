package moe.qingu.youran.item;

import moe.qingu.youran.registry.YouranTab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/** 只有一行说明的普通物品（货币、奇怪的石头等）。说明文字照 1.3.4 写死为中文。 */
public class ItemWithTooltip extends Item {
    private final String tooltip;

    public ItemWithTooltip(String name, int maxStackSize, String tooltip) {
        setRegistryName(name);
        setTranslationKey(name);
        setMaxStackSize(maxStackSize);
        setCreativeTab(YouranTab.tab);
        this.tooltip = tooltip;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> lines, ITooltipFlag flag) {
        super.addInformation(stack, world, lines, flag);
        lines.add(tooltip);
    }
}
