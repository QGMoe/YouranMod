package moe.qingu.youran.logic;

import moe.qingu.youran.config.DPersonnelConfig;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HealScheduleTest {
    private static HealSchedule of(String... lines) {
        KeyValues.Result<HealSchedule.Point> r = HealSchedule.parse(lines);
        assertTrue(r.errors.isEmpty(), r.errors.toString());
        return HealSchedule.of(r.values);
    }

    private final HealSchedule defaults = of(DPersonnelConfig.healSchedule);

    @Test
    void defaultsTickByTick() {
        // 一 tick 一 tick 走：正好走到那一刻时触发
        assertEquals(Collections.singletonList(100), defaults.reached(22999, 23000));
        assertEquals(Collections.singletonList(2), defaults.reached(5999, 6000));
        assertEquals(Collections.emptyList(), defaults.reached(23000, 23001));
        assertEquals(Collections.emptyList(), defaults.reached(0, 1));
    }

    @Test
    void everyDayNotOnlyTheFirst() {
        // 1.3.4 只有第一天对得上；现在每天都到点
        assertEquals(Collections.singletonList(100), defaults.reached(24000 + 22999, 24000 + 23000));
        assertEquals(Collections.singletonList(2), defaults.reached(10 * 24000 + 5999, 10 * 24000 + 6000));
    }

    @Test
    void jumpsCountOnceAndBackwardsNever() {
        // 睡觉从 13000 跳到第二天 0 点，跳过了 23000：也算到
        assertEquals(Collections.singletonList(100), defaults.reached(13000, 24000));
        // 一次跳好几天：每项只回一次
        assertEquals(Arrays.asList(100, 2), defaults.reached(1000, 5 * 24000));
        // 时间停住、往回调：不触发
        assertEquals(Collections.emptyList(), defaults.reached(23000, 23000));
        assertEquals(Collections.emptyList(), defaults.reached(30000, 1000));
    }

    @Test
    void firstEntryWinsAndTimesWrap() {
        HealSchedule s = of("100=1", "100=5", "24100=7");
        assertEquals(Collections.singletonList(1), s.reached(99, 100));
        assertEquals(100, HealSchedule.parse(new String[]{"24100=7"}).values.get(0).timeOfDay);
    }

    @Test
    void badEntriesAreSkipped() {
        KeyValues.Result<HealSchedule.Point> r = HealSchedule.parse(new String[]{"abc=1", "100", "200=-1", "300=x", " 400 = 3 ", "=5", "6="});
        assertEquals(1, r.values.size());
        assertEquals(400, r.values.get(0).timeOfDay);
        assertEquals(3, r.values.get(0).amplifier);
        assertEquals(6, r.errors.size(), r.errors.toString());
    }
}
