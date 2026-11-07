package moe.qingu.youran.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/** 悠然服务器 Logo（youranmod:youranserver）：右键在聊天栏说一句“这里是悠然Minecraft服务器”。 */
public class ItemYouranLogo extends ItemWithTooltip {
    public ItemYouranLogo() {
        super("youranserver", 1, "悠然服务器Logo");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ActionResult<ItemStack> result = super.onItemRightClick(world, player, hand);
        if (!world.isRemote) {
            player.sendStatusMessage(new TextComponentString("§e这里是悠然Minecraft服务器"), false);
        }
        return result;
    }
}
