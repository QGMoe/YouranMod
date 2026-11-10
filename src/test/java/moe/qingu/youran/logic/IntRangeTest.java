package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntRangeTest {
    /** 与 1.3.4 的写法（random.nextInt(n) + min）取到同样的数，矿石才会生成在同样的位置 */
    @Test
    void sameDrawsAs134() {
        int[][] ranges = {{1, 40, 40, 1}, {1, 11, 11, 1}, {1, 39, 39, 1}, {0, 99, 100, 0}};
        for (int[] r : ranges) {
            Random ours = new Random(12345);
            Random old = new Random(12345);
            for (int i = 0; i < 1000; i++) {
                assertEquals(old.nextInt(r[2]) + r[3], IntRange.pick(ours, r[0], r[1]));
            }
        }
    }

    @Test
    void swapsReversedBounds() {
        Random a = new Random(7);
        Random b = new Random(7);
        for (int i = 0; i < 100; i++) {
            assertEquals(IntRange.pick(a, 5, 10), IntRange.pick(b, 10, 5));
        }
        assertEquals(3, IntRange.pick(new Random(1), 3, 3));
    }
}
