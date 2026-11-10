package moe.qingu.youran.logic;

import moe.qingu.youran.config.TeleportConfig;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeleportPointsTest {
    private static List<TeleportPoints.Point> defaults() {
        KeyValues.Result<TeleportPoints.Point> r = TeleportPoints.parsePoints(TeleportConfig.points);
        assertTrue(r.errors.isEmpty(), r.errors.toString());
        return r.values;
    }

    @Test
    void defaultIsFwOf134() {
        List<TeleportPoints.Point> points = defaults();
        assertEquals(1, points.size());
        TeleportPoints.Point fw = points.get(0);
        assertEquals("fw", fw.name);
        assertEquals(3, fw.dimension);
        assertEquals(-274, fw.x);
        assertEquals(69, fw.y);
        assertEquals(162, fw.z);
        assertNull(fw.yaw, "1.3.4 不改朝向");
        assertEquals("youranmod:a_spcial_village", fw.advancement);
        // 文字与 1.3.4 逐字相同
        assertEquals("§6fw - 主城维度，垃圾回收站岛屿", Texts.tpListEntry(fw.name, fw.description));
        assertEquals("§2正在传送至“主城-垃圾回收站岛屿\"", Texts.tpGoing(fw.title));
        assertTrue(TeleportPoints.find(points, "FW") == null, "区分大小写");
    }

    @Test
    void defaultUnlockAreaIsRecycleStation() {
        List<TeleportPoints.Point> points = defaults();
        KeyValues.Result<TeleportPoints.UnlockArea> r = TeleportPoints.parseUnlockAreas(TeleportConfig.unlockAreas, points);
        assertTrue(r.errors.isEmpty(), r.errors.toString());
        TeleportPoints.UnlockArea u = r.values.get(0);
        assertEquals(3, u.dimension);
        assertEquals("youranmod:a_spcial_village", u.advancement);
        assertTrue(u.area.contains(-271.5, 68, 162.5));
        // 提示与实际子命令一致（1.3.4 写成了“废物”）
        assertEquals("§2传送点已解锁，可通过“/youran tp fw”来直接到达该岛屿", Texts.tpUnlocked(u.pointName, u.place));
    }

    @Test
    void parsesFacingAndOptionalAdvancement() {
        KeyValues.Result<TeleportPoints.Point> r = TeleportPoints.parsePoints(new String[]{
                "spawn;0;0.5;64;0.5;90,-10;出生点;主世界出生点;"});
        assertTrue(r.errors.isEmpty(), r.errors.toString());
        TeleportPoints.Point p = r.values.get(0);
        assertEquals(90f, p.yaw);
        assertEquals(-10f, p.pitch);
        assertNull(p.advancement);
    }

    @Test
    void rejectsBadLines() {
        KeyValues.Result<TeleportPoints.Point> r = TeleportPoints.parsePoints(new String[]{
                "a;0;1;2;3;;说明;名字;",          // 有效
                "a;0;1;2;3;;重复;名字;",          // 名字重复
                "list;0;1;2;3;;x;x;",            // 保留字
                "b c;0;1;2;3;;x;x;",             // 有空白
                "c;x;1;2;3;;x;x;",               // 维度不是整数
                "d;0;1;2;3;90;x;x;",             // 朝向少一半
                "e;0;1;2;3;;x;x;Not An Id",      // 进度注册名不对
                "f;0;1;2;3;;x;x"});              // 段数不对
        assertEquals(Arrays.asList("a"), TeleportPoints.names(r.values));
        assertEquals(7, r.errors.size(), r.errors.toString());
        KeyValues.Result<TeleportPoints.UnlockArea> u = TeleportPoints.parseUnlockAreas(new String[]{
                "0;1;2;3;4;5;youranmod:x;nope;这里", "0;1;2;3;4;5;;a;这里"}, r.values);
        assertEquals(0, u.values.size());
        assertEquals(2, u.errors.size(), u.errors.toString());
    }
}
