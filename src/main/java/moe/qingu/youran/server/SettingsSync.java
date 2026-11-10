package moe.qingu.youran.server;

import moe.qingu.youran.logic.SharedState;
import moe.qingu.youran.net.SyncSettingsMessage;
import moe.qingu.youran.net.YouranNetwork;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/** 玩家登录时把两端都要用的配置发给他（单人游戏里发给自己，内容是默认值） */
public final class SettingsSync {
    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            YouranNetwork.CHANNEL.sendTo(new SyncSettingsMessage(SharedState.server()), (EntityPlayerMP) event.player);
        }
    }
}
