package moe.qingu.youran.net;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/** 网络频道 {@code youranmod}（1.3.4 的 MCreator 频道 {@code youranmod:a} 已去掉，见 README）。现在只有一种包：同步配置。 */
public final class YouranNetwork {
    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("youranmod");

    private YouranNetwork() {
    }

    /** 预初始化时登记（两端都要登记，包的编号才对得上） */
    public static void register() {
        CHANNEL.registerMessage(SyncSettingsMessage.Handler.class, SyncSettingsMessage.class, 0, Side.CLIENT);
    }
}
