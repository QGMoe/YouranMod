package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletionsTest {
    private static final List<String> POINTS = Arrays.asList("fw", "farm");

    @Test
    void subcommands() {
        assertEquals(Completions.SUBCOMMANDS, Completions.youran(new String[]{""}, true, POINTS));
        assertEquals(Arrays.asList("saveD", "serverhelp"), Completions.youran(new String[]{"s"}, true, POINTS));
        assertEquals(Collections.singletonList("serverhelp"), Completions.youran(new String[]{"s"}, false, POINTS),
                "没有权限时不补 saveD");
        assertEquals(Collections.singletonList("saveD"), Completions.youran(new String[]{"SAV"}, true, POINTS), "不分大小写");
        assertEquals(Arrays.asList("temperature", "tp"), Completions.youran(new String[]{"t"}, true, POINTS));
    }

    @Test
    void teleportPoints() {
        assertEquals(Arrays.asList("help", "list", "fw", "farm"), Completions.youran(new String[]{"tp", ""}, false, POINTS));
        assertEquals(Arrays.asList("fw", "farm"), Completions.youran(new String[]{"tp", "f"}, false, POINTS));
        assertEquals(Collections.emptyList(), Completions.youran(new String[]{"about", ""}, false, POINTS));
        assertEquals(Collections.emptyList(), Completions.youran(new String[]{"tp", "fw", ""}, false, POINTS));
    }
}
