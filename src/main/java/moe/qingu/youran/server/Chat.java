package moe.qingu.youran.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

/** 给玩家发文字（同 1.3.4：只发给玩家，别的实体执行指令时什么也不发） */
public final class Chat {
    private Chat() {
    }

    /** 聊天栏一行（1.3.4 的 sendStatusMessage(…, false)） */
    public static void tell(Entity entity, String text) {
        if (entity instanceof EntityPlayer && !entity.world.isRemote) {
            ((EntityPlayer) entity).sendStatusMessage(new TextComponentString(text), false);
        }
    }

    /** 物品栏上方的一行（1.3.4 的 sendStatusMessage(…, true)） */
    public static void actionBar(Entity entity, String text) {
        if (entity instanceof EntityPlayer && !entity.world.isRemote) {
            ((EntityPlayer) entity).sendStatusMessage(new TextComponentString(text), true);
        }
    }

    /**
     * 可点的链接：前面一段普通文字，后面深蓝色带下划线、点了打开网址（与 1.3.4 tellraw 的那段 JSON 相同，
     * 也同 tellraw 一样作为系统消息发）。
     */
    public static void link(Entity entity, String label, String text, String url) {
        if (!(entity instanceof EntityPlayer) || entity.world.isRemote) {
            return;
        }
        ITextComponent link = new TextComponentString(text);
        link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                .setColor(TextFormatting.DARK_BLUE).setUnderlined(true);
        entity.sendMessage(new TextComponentString(label).appendSibling(link));
    }
}
