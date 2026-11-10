package moe.qingu.youran.logic;

import java.util.function.IntPredicate;

/**
 * 区块一段（16×16×16）方块数据的调色板，从 {@code BlockStateContainer.write} 写出的字节开头读出来
 * （1.12：一个字节的位数，位数 ≤ 8 时接着是调色板项数与各项的方块状态 ID，都是 VarInt；位数更大时用全局调色板，项数写 0）。
 * 用来先看一段里可能不可能有某种方块，没有就不必逐格扫。
 */
public final class PaletteHeader {
    /** 位数大于这个值时用的是全局调色板，看不出这一段有哪些方块 */
    static final int MAX_LOCAL_BITS = 8;

    private PaletteHeader() {
    }

    /**
     * 这一段里可能有满足条件的方块：调色板里有满足条件的状态 ID，或者用的是全局调色板（看不出来，只能逐格扫）、字节不完整。
     * 调色板里有不代表真的有（调色板不会随方块被换掉而缩小），所以返回 true 之后还要逐格确认。
     */
    public static boolean mayContain(byte[] data, IntPredicate stateId) {
        int[] palette = read(data);
        if (palette == null) {
            return true;
        }
        for (int id : palette) {
            if (stateId.test(id)) {
                return true;
            }
        }
        return false;
    }

    /** 调色板里的状态 ID；全局调色板或字节不完整时返回 null */
    static int[] read(byte[] data) {
        if (data.length < 1) {
            return null;
        }
        int bits = data[0] & 0xFF;
        if (bits > MAX_LOCAL_BITS) {
            return null;
        }
        int[] pos = {1};
        int count = readVarInt(data, pos);
        if (count < 0 || count > (1 << MAX_LOCAL_BITS)) {
            return null;
        }
        int[] ids = new int[count];
        for (int i = 0; i < count; i++) {
            ids[i] = readVarInt(data, pos);
            if (ids[i] < 0) {
                return null;
            }
        }
        return ids;
    }

    /** 读一个 VarInt（同 PacketBuffer.readVarInt）；字节不够或超过 5 个字节时返回 -1 */
    private static int readVarInt(byte[] data, int[] pos) {
        int value = 0;
        for (int i = 0; i < 5; i++) {
            if (pos[0] >= data.length) {
                return -1;
            }
            byte b = data[pos[0]++];
            value |= (b & 0x7F) << (7 * i);
            if ((b & 0x80) == 0) {
                return value;
            }
        }
        return -1;
    }
}
