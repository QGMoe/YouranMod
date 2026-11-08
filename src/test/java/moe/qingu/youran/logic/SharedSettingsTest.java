package moe.qingu.youran.logic;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** 同步给客户端的配置：默认值、编解码、校验 */
class SharedSettingsTest {
    private static SharedSettings.Builder changed() {
        SharedSettings.Builder b = new SharedSettings.Builder();
        b.websiteUrl = "https://example.org/新官网?a=1&b=2";
        b.websiteText = "example.org（新）";
        b.manualUrl = "http://docs.example.org/";
        b.manualText = "docs";
        b.mcmodUrl = "https://mcmod.example/1";
        b.mcmodText = "百科";
        b.rulesUrl = "https://rules.example/";
        b.qqGroup = "123456";
        b.qqGroupUrl = "https://qq.example/join";
        b.mercurySlownessAmplifier = 1;
        b.mercurySlownessTicks = 40;
        b.galliumSlownessAmplifier = 0;
        b.galliumSlownessTicks = 2;
        b.chlorinePoisonAmplifier = 4;
        b.chlorinePoisonTicks = 20;
        b.chlorineHelmets = Arrays.asList("a:mask", "b:helmet");
        b.chlorineChestplates = Collections.emptyList();
        Map<String, Float> ingots = new LinkedHashMap<>();
        ingots.put("ingotLithium", 3.5f);
        ingots.put("ingotSodium", 0f);
        b.reactiveIngots = ingots;
        b.swordSlownessChance = 1;
        b.swordSlownessTicks = 100;
        b.swordSlownessAmplifier = 127;
        b.redBloodGemDurabilityCost = 0;
        b.homeToolDurabilityCost = 5;
        b.mineralWaterHunger = 4;
        b.mineralWaterSaturation = 0.6f;
        b.drinkingWaterHunger = 2;
        b.drinkingWaterSaturation = 0f;
        return b;
    }

    @Test
    void defaultsAsIn134() {
        SharedSettings d = SharedSettings.DEFAULTS;
        assertEquals(5, d.mercurySlownessAmplifier);
        assertEquals(1, d.mercurySlownessTicks);
        assertEquals(3, d.galliumSlownessAmplifier);
        assertEquals(1, d.galliumSlownessTicks);
        assertEquals(2, d.chlorinePoisonAmplifier);
        assertEquals(300, d.chlorinePoisonTicks);
        assertEquals(Collections.singletonList("ic2:hazmat_helmet"), d.chlorineHelmets);
        assertEquals(Collections.singletonList("scp:hazard_suit"), d.chlorineChestplates);
        assertEquals(Arrays.asList("ingotSodium", "ingotMagnesium", "ingotPotassium", "ingotCalcium", "ingotAluminum"),
                new ArrayList<>(d.reactiveIngots.keySet()));
        assertEquals(Arrays.asList(2.0f, 2.0f, 1.5f, 1.5f, 1.0f), new ArrayList<>(d.reactiveIngots.values()));
        assertEquals(0.5, d.swordSlownessChance);
        assertEquals(5, d.swordSlownessTicks);
        assertEquals(2, d.swordSlownessAmplifier);
        assertEquals(1, d.redBloodGemDurabilityCost);
        assertEquals(1, d.homeToolDurabilityCost);
        assertEquals(1, d.mineralWaterHunger);
        assertEquals(0.3f, d.mineralWaterSaturation);
        assertEquals(0, d.drinkingWaterHunger);
        assertEquals(0.2f, d.drinkingWaterSaturation);
    }

    @Test
    void defaultsPassValidation() {
        List<String> problems = new ArrayList<>();
        assertEquals(SharedSettings.DEFAULTS, SharedSettings.sanitize(SharedSettings.DEFAULTS, problems));
        assertEquals(Collections.emptyList(), problems);
    }

    /** 手册书的宏：book.json 里写的值就是默认值（客户端断开后恢复成 book.json 的值，与默认值一致） */
    @Test
    void bookJsonMacrosAreTheDefaults() throws Exception {
        String json;
        try (InputStream in = getClass().getResourceAsStream("/assets/youranmod/patchouli_books/guide/book.json")) {
            assertNotNull(in, "找不到 book.json");
            json = new Scanner(in, StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
        }
        String block = json.substring(json.indexOf("\"macros\""));
        block = block.substring(block.indexOf('{') + 1, block.indexOf('}'));
        Map<String, String> macros = new LinkedHashMap<>();
        Matcher m = Pattern.compile("\"([^\"]*)\"\\s*:\\s*\"([^\"]*)\"").matcher(block);
        while (m.find()) {
            macros.put(m.group(1), m.group(2));
        }
        assertEquals(SharedSettings.DEFAULTS.bookMacros(), macros);
    }

    @Test
    void bookMacrosFollowLinks() {
        Map<String, String> m = changed().build().bookMacros();
        assertEquals("https://example.org/新官网?a=1&b=2", m.get("$(yr_site)"));
        assertEquals("example.org（新）", m.get("$(yr_site_text)"));
        assertEquals("http://docs.example.org/", m.get("$(yr_docs)"));
        assertEquals("docs", m.get("$(yr_docs_text)"));
        assertEquals("https://mcmod.example/1", m.get("$(yr_mcmod)"));
        assertEquals("https://rules.example/", m.get("$(yr_rules)"));
        assertEquals("123456", m.get("$(yr_qq)"));
        assertEquals("https://qq.example/join", m.get("$(yr_qq_link)"));
    }

    @Test
    void roundTrip() {
        SharedSettings s = changed().build();
        List<String> problems = new ArrayList<>();
        SharedSettings back = SharedSettings.decode(s.encode(), problems);
        assertEquals(Collections.emptyList(), problems);
        assertEquals(s, back);
        assertNotEquals(SharedSettings.DEFAULTS, back);
        assertEquals(Arrays.asList("ingotLithium", "ingotSodium"), new ArrayList<>(back.reactiveIngots.keySet()));
        assertEquals(3.5f, back.explosionStrength(Collections.singletonList("ingotLithium")));
        assertEquals(-1, back.explosionStrength(Collections.singletonList("ingotAluminum")));
        assertTrue(back.protectsFromChlorine("b:helmet", "minecraft:air"));
        assertEquals(1, back.mercurySlownessAmplifier);
        assertEquals(0.6f, back.mineralWaterSaturation);

        assertEquals(SharedSettings.DEFAULTS, SharedSettings.decode(SharedSettings.DEFAULTS.encode(), problems));
        assertEquals(Collections.emptyList(), problems);
    }

    /** 不合规的项各自换回默认值，其他项不受影响 */
    @Test
    void invalidFieldsFallBackToDefaults() {
        SharedSettings.Builder b = changed();
        b.websiteUrl = "javascript:alert(1)";
        b.manualUrl = "https://docs.example.org/a b";
        b.mcmodUrl = "https://x.example/$(k)";
        b.rulesUrl = "https://x.example/(1)";
        b.qqGroupUrl = "";
        b.websiteText = "$(l:https://evil.example)点我";
        b.qqGroup = "12\n34";
        b.mercurySlownessAmplifier = 200;
        b.galliumSlownessAmplifier = -1;
        b.chlorinePoisonTicks = 0;
        b.swordSlownessChance = Double.NaN;
        b.redBloodGemDurabilityCost = -3;
        b.mineralWaterHunger = 21;
        b.drinkingWaterSaturation = Float.POSITIVE_INFINITY;
        b.chlorineHelmets = Arrays.asList("ok:item", "Not A Name");
        Map<String, Float> ingots = new LinkedHashMap<>();
        ingots.put("ingotLithium", -1f);
        b.reactiveIngots = ingots;
        List<String> problems = new ArrayList<>();
        SharedSettings s = SharedSettings.decode(b.build().encode(), problems);
        SharedSettings d = SharedSettings.DEFAULTS;
        assertEquals(d.websiteUrl, s.websiteUrl);
        assertEquals(d.manualUrl, s.manualUrl);
        assertEquals(d.mcmodUrl, s.mcmodUrl);
        assertEquals(d.rulesUrl, s.rulesUrl);
        assertEquals(d.qqGroupUrl, s.qqGroupUrl);
        assertEquals(d.websiteText, s.websiteText);
        assertEquals(d.qqGroup, s.qqGroup);
        assertEquals(d.mercurySlownessAmplifier, s.mercurySlownessAmplifier);
        assertEquals(d.galliumSlownessAmplifier, s.galliumSlownessAmplifier);
        assertEquals(d.chlorinePoisonTicks, s.chlorinePoisonTicks);
        assertEquals(d.swordSlownessChance, s.swordSlownessChance);
        assertEquals(d.redBloodGemDurabilityCost, s.redBloodGemDurabilityCost);
        assertEquals(d.mineralWaterHunger, s.mineralWaterHunger);
        assertEquals(d.drinkingWaterSaturation, s.drinkingWaterSaturation);
        assertEquals(d.chlorineHelmets, s.chlorineHelmets);
        assertEquals(d.reactiveIngots, s.reactiveIngots);
        assertEquals(16, problems.size(), problems.toString());
        assertTrue(problems.get(0).startsWith("websiteUrl = "), problems.get(0));
        // 合规的项照用
        assertEquals("docs", s.manualText);
        assertEquals(40, s.mercurySlownessTicks);
        assertEquals(Collections.emptyList(), s.chlorineChestplates);
        assertEquals(5, s.homeToolDurabilityCost);
    }

    @Test
    void limits() {
        SharedSettings.Builder b = new SharedSettings.Builder();
        b.mercurySlownessAmplifier = 127;
        b.chlorinePoisonTicks = 1_000_000;
        b.swordSlownessChance = 0;
        StringBuilder url = new StringBuilder("https://");
        while (url.length() < 512) {
            url.append('a');
        }
        b.websiteUrl = url.toString();
        List<String> problems = new ArrayList<>();
        SharedSettings.sanitize(b.build(), problems);
        assertEquals(Collections.emptyList(), problems);
        b.mercurySlownessAmplifier = 128;
        b.chlorinePoisonTicks = 1_000_001;
        b.websiteUrl = url + "a";
        SharedSettings.sanitize(b.build(), problems);
        assertEquals(3, problems.size(), problems.toString());
    }

    @Test
    void brokenPacketsUseAllDefaults() {
        byte[] good = changed().build().encode();
        List<String> problems = new ArrayList<>();
        assertSame(SharedSettings.DEFAULTS, SharedSettings.decode(null, problems));
        assertSame(SharedSettings.DEFAULTS, SharedSettings.decode(new byte[0], problems));
        assertSame(SharedSettings.DEFAULTS, SharedSettings.decode(Arrays.copyOf(good, good.length - 1), problems));
        assertSame(SharedSettings.DEFAULTS, SharedSettings.decode(Arrays.copyOf(good, good.length + 1), problems));
        byte[] wrongFormat = good.clone();
        wrongFormat[0] = 9;
        assertSame(SharedSettings.DEFAULTS, SharedSettings.decode(wrongFormat, problems));
        assertEquals(5, problems.size(), problems.toString());
    }

    @Test
    void oversizedListIsRejectedWhileDecoding() {
        SharedSettings.Builder b = new SharedSettings.Builder();
        List<String> many = new ArrayList<>();
        for (int i = 0; i < 65; i++) {
            many.add("a:item" + i);
        }
        b.chlorineHelmets = many;
        List<String> problems = new ArrayList<>();
        assertSame(SharedSettings.DEFAULTS, SharedSettings.decode(b.build().encode(), problems));
        assertEquals(1, problems.size());
    }

    @Test
    void clientStateResetsToDefaults() {
        SharedSettings s = changed().build();
        try {
            SharedState.setClient(s);
            assertSame(s, SharedState.get(true));
            assertSame(SharedSettings.DEFAULTS, SharedState.get(false), "服务端那份不受影响");
            SharedState.resetClient();
            assertSame(SharedSettings.DEFAULTS, SharedState.get(true));
        } finally {
            SharedState.resetClient();
            SharedState.resetServer();
        }
    }
}
