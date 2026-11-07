package moe.qingu.youran.logic;

import moe.qingu.youran.config.DPersonnelConfig;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextsTest {
    @Test
    void saveDMessageSameAs134ByDefault() {
        assertEquals("§2成功执行：/effect @e[type=scp:dpersonnel] minecraft:instant_health 1 100",
                Texts.saveDDone(DPersonnelConfig.entity, DPersonnelConfig.saveDAmplifier));
    }

    @Test
    void temperatureLikeFloatTimes100() {
        assertEquals("当地气温：§380.0", Texts.temperature(0.8f));
        assertEquals("当地气温：§3200.0", Texts.temperature(2.0f));
        assertEquals("当地气温：§3-50.0", Texts.temperature(-0.5f));
        assertEquals("当地气温：§3" + (0.95f * 100.f), Texts.temperature(0.95f));
    }

    @Test
    void helpShowsSaveDOnlyForOps() {
        List<String> op = Texts.help(true);
        List<String> player = Texts.help(false);
        assertEquals(10, op.size());
        assertEquals(9, player.size());
        assertTrue(op.contains("§6youran saveD - 给D级人员回血"));
        assertFalse(player.contains("§6youran saveD - 给D级人员回血"));
        assertEquals("§e指令使用帮助：", op.get(0));
        assertEquals("§6youran website - 前往服务器官网", op.get(9));
        // saveD 在 maincity 与 serverhelp 之间（同 1.3.4）
        assertEquals("§6youran maincity - 前往主城", op.get(4));
        assertEquals("§6youran saveD - 给D级人员回血", op.get(5));
        assertEquals("§6youran serverhelp - 前往服务器网页手册", op.get(6));
    }
}
