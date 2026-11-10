package moe.qingu.youran.client;

import moe.qingu.youran.YouranMod;
import moe.qingu.youran.logic.SharedSettings;
import moe.qingu.youran.logic.SharedState;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端这一侧的同步配置：连着服务器时用服务器发来的值（{@link SharedState#client()}），并写进手册书的链接；
 * 断开后回到默认值、手册书回到 book.json 里的值。单人游戏一样（内置服务端发的就是默认值）。
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = YouranMod.MODID)
public final class ClientSettings {
    private ClientSettings() {
    }

    /** 网络线程里调用：转到主线程 */
    public static void receive(byte[] data) {
        Minecraft.getMinecraft().addScheduledTask(() -> apply(data));
    }

    private static void apply(byte[] data) {
        List<String> problems = new ArrayList<>();
        SharedSettings settings = SharedSettings.decode(data, problems);
        for (String p : problems) {
            YouranMod.LOG.warn("服务器发来的配置有问题：{}", p);
        }
        SharedState.setClient(settings);
        PatchouliLinks.apply(settings.bookMacros());
        YouranMod.LOG.info("用上了服务器发来的配置（官网 {}，手册 {}）", settings.websiteUrl, settings.manualUrl);
    }

    /** 断开连接（网络线程里触发）：转到主线程，回到默认值 */
    @SubscribeEvent
    public static void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Minecraft.getMinecraft().addScheduledTask(ClientSettings::reset);
    }

    static void reset() {
        SharedState.resetClient();
        PatchouliLinks.restore();
        YouranMod.LOG.info("已断开，配置回到默认值");
    }
}
