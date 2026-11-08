package moe.qingu.youran.net;

import io.netty.buffer.ByteBuf;
import moe.qingu.youran.logic.SharedSettings;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 服务端 → 客户端：两端都要用的配置（{@link SharedSettings#encode()} 的字节）。玩家登录时发一次。
 * 客户端在主线程里解码、校验、换上（{@code client.ClientSettings}）。
 */
public final class SyncSettingsMessage implements IMessage {
    /** 包里的字节最多这么多（正常不到 1 KB）；超过的当坏包 */
    static final int MAX_BYTES = 32 * 1024;
    /** 与 YouranMod.LOG 同名（不直接引用 YouranMod，免得单元测试里初始化模组主类） */
    private static final Logger LOG = LogManager.getLogger("YouranMod");

    private byte[] data;

    /** 收包时由 FML 构造 */
    public SyncSettingsMessage() {
    }

    public SyncSettingsMessage(SharedSettings settings) {
        this.data = settings.encode();
    }

    /** null 表示包坏了 */
    public byte[] data() {
        return data;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(data.length);
        buf.writeBytes(data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int n = buf.readInt();
        if (n < 0 || n > MAX_BYTES || n > buf.readableBytes()) {
            LOG.warn("服务器发来的配置包长度不对（{} 字节，实际可读 {} 字节），按坏包处理", n, buf.readableBytes());
            buf.skipBytes(buf.readableBytes());
            data = null;
            return;
        }
        data = new byte[n];
        buf.readBytes(data);
    }

    /** 在网络线程里收到；转到客户端主线程处理 */
    public static final class Handler implements IMessageHandler<SyncSettingsMessage, IMessage> {
        @Override
        public IMessage onMessage(SyncSettingsMessage message, MessageContext ctx) {
            byte[] data = message.data();
            moe.qingu.youran.client.ClientSettings.receive(data);
            return null;
        }
    }
}
