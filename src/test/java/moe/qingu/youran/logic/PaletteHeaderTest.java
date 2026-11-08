package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** 区块一段的调色板：先看有没有传送门方块，没有就不逐格扫 */
class PaletteHeaderTest {
    /** 假设主城传送门的两种状态 ID 是 1234×16+1、+2（X 轴、Z 轴） */
    private static final int PORTAL_X = 1234 << 4 | 1;
    private static final int PORTAL_Z = 1234 << 4 | 2;
    private static final IntPredicate PORTAL = id -> id == PORTAL_X || id == PORTAL_Z;

    /** 照 BlockStateContainer.write：位数、项数、各项（VarInt），后面跟着方块数据（这里随便填几个字节） */
    private static byte[] section(int bits, int... palette) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(bits);
        varInt(out, palette.length);
        for (int id : palette) {
            varInt(out, id);
        }
        for (int i = 0; i < 16; i++) {
            out.write(0x55);
        }
        return out.toByteArray();
    }

    private static void varInt(ByteArrayOutputStream out, int v) {
        while ((v & ~0x7F) != 0) {
            out.write((v & 0x7F) | 0x80);
            v >>>= 7;
        }
        out.write(v);
    }

    @Test
    void readsPalette() {
        assertArrayEquals(new int[]{0, 16, 300, PORTAL_X}, PaletteHeader.read(section(4, 0, 16, 300, PORTAL_X)));
        assertArrayEquals(new int[]{0}, PaletteHeader.read(section(4, 0)));
    }

    @Test
    void sectionWithoutPortalIsSkipped() {
        assertFalse(PaletteHeader.mayContain(section(4, 0, 1, 16, 32), PORTAL));
        assertFalse(PaletteHeader.mayContain(section(8, 0, 1, 16, 32, 4096, 65535), PORTAL));
    }

    @Test
    void sectionWithPortalInPaletteIsScanned() {
        assertTrue(PaletteHeader.mayContain(section(4, 0, PORTAL_X), PORTAL));
        assertTrue(PaletteHeader.mayContain(section(5, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, PORTAL_Z), PORTAL));
    }

    /** 全局调色板（位数 > 8，项数写 0）看不出来，要逐格扫 */
    @Test
    void globalPaletteMustBeScanned() {
        assertTrue(PaletteHeader.mayContain(section(13), PORTAL));
        assertNull(PaletteHeader.read(section(13)));
    }

    @Test
    void brokenBytesMustBeScanned() {
        assertTrue(PaletteHeader.mayContain(new byte[0], PORTAL));
        assertTrue(PaletteHeader.mayContain(new byte[]{4}, PORTAL), "没有项数");
        assertTrue(PaletteHeader.mayContain(new byte[]{4, 3, 0}, PORTAL), "项数比实际多");
        assertTrue(PaletteHeader.mayContain(new byte[]{4, 1, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0}, PORTAL), "VarInt 太长");
    }
}
