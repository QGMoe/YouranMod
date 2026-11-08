package moe.qingu.youran.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import moe.qingu.youran.logic.SharedSettings;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/** 配置包在 ByteBuf 上的编解码 */
class SyncSettingsMessageTest {
    private static SharedSettings changed() {
        SharedSettings.Builder b = new SharedSettings.Builder();
        b.websiteUrl = "https://example.org/";
        b.mercurySlownessAmplifier = 1;
        return b.build();
    }

    @Test
    void roundTripThroughByteBuf() {
        SharedSettings s = changed();
        ByteBuf buf = Unpooled.buffer();
        new SyncSettingsMessage(s).toBytes(buf);
        SyncSettingsMessage got = new SyncSettingsMessage();
        got.fromBytes(buf);
        assertEquals(0, buf.readableBytes());
        assertArrayEquals(s.encode(), got.data());
        List<String> problems = new ArrayList<>();
        assertEquals(s, SharedSettings.decode(got.data(), problems));
        assertEquals(Collections.emptyList(), problems);
    }

    @Test
    void badLengthIsABrokenPacket() {
        for (int n : new int[]{-1, SyncSettingsMessage.MAX_BYTES + 1, 100}) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(n);
            buf.writeBytes(new byte[10]);
            SyncSettingsMessage got = new SyncSettingsMessage();
            got.fromBytes(buf);
            assertNull(got.data(), "长度 " + n);
            List<String> problems = new ArrayList<>();
            assertSame(SharedSettings.DEFAULTS, SharedSettings.decode(got.data(), problems));
            assertEquals(1, problems.size());
        }
    }
}
