package moe.qingu.youran.logic;

import moe.qingu.youran.config.DPersonnelConfig;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HealScheduleTest {
    private static HealSchedule of(String... lines) {
        KeyValues.Result<HealSchedule.Point> r = HealSchedule.parse(lines);
        assertTrue(r.errors.isEmpty(), r.errors.toString());
        return HealSchedule.of(r.values);
    }

    @Test
    void defaultsAreThoseOf134() {
        HealSchedule s = of(DPersonnelConfig.healSchedule);
        assertEquals(OptionalInt.of(100), s.amplifierAt(23000));
        assertEquals(OptionalInt.of(2), s.amplifierAt(6000));
        assertFalse(s.amplifierAt(0).isPresent());
        assertFalse(s.amplifierAt(22999).isPresent());
    }

    @Test
    void comparesTotalTimeLike134() {
        // 1.3.4 用 getWorldTime() 的总时间直接比，第二天的 23000 点（总时间 47000）对不上
        HealSchedule s = of(DPersonnelConfig.healSchedule);
        assertFalse(s.amplifierAt(24000 + 23000).isPresent());
        assertFalse(s.amplifierAt(24000 + 6000).isPresent());
    }

    @Test
    void firstEntryWins() {
        HealSchedule s = of("100=1", "100=5");
        assertEquals(OptionalInt.of(1), s.amplifierAt(100));
    }

    @Test
    void badEntriesAreSkipped() {
        KeyValues.Result<HealSchedule.Point> r = HealSchedule.parse(new String[]{"abc=1", "100", "200=-1", "300=x", " 400 = 3 ", "=5", "6="});
        assertEquals(1, r.values.size());
        assertEquals(400, r.values.get(0).worldTime);
        assertEquals(3, r.values.get(0).amplifier);
        assertEquals(6, r.errors.size(), r.errors.toString());
    }
}
