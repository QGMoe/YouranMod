package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** 往手册书的宏表里写服务器的值、断开时恢复 */
class MacroOverlayTest {
    private static Map<String, String> book() {
        Map<String, String> m = new HashMap<>();
        m.put("$(yr_site)", "https://youran.qingu.moe/");
        m.put("$(yr_qq)", "953168624");
        m.put("$(br)", "$(br)");  // Patchouli 自己的宏不动
        return m;
    }

    @Test
    void applyThenRestore() {
        Map<String, String> macros = book();
        MacroOverlay o = new MacroOverlay(macros);
        Map<String, String> server = new HashMap<>();
        server.put("$(yr_site)", "https://a.example/");
        server.put("$(yr_qq)", "1");
        server.put("$(yr_new)", "x");
        o.apply(server);
        assertEquals("https://a.example/", macros.get("$(yr_site)"));
        assertEquals("x", macros.get("$(yr_new)"));
        o.restore();
        assertEquals(book(), macros, "恢复成原来的值，原来没有的删掉");
    }

    @Test
    void secondServerStillRestoresBookValues() {
        Map<String, String> macros = book();
        MacroOverlay o = new MacroOverlay(macros);
        Map<String, String> a = new HashMap<>();
        a.put("$(yr_site)", "https://a.example/");
        o.apply(a);
        Map<String, String> b = new HashMap<>();
        b.put("$(yr_site)", "https://b.example/");
        o.apply(b);  // 换服时没断开前又收到一次（或者断开事件比新的包晚）
        assertEquals("https://b.example/", macros.get("$(yr_site)"));
        o.restore();
        assertEquals(book(), macros);
        o.restore();
        assertEquals(book(), macros);
    }

    @Test
    void restoreBeforeApplyDoesNothing() {
        Map<String, String> macros = book();
        new MacroOverlay(macros).restore();
        assertEquals(book(), macros);
    }
}
