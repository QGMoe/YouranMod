package moe.qingu.youran.config;

import moe.qingu.youran.logic.KeyValues;
import moe.qingu.youran.logic.SharedSettings;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 由配置（{@link LinkConfig}、{@link FluidConfig}、{@link ItemConfig} 里标了“同步给客户端”的项）算出 {@link SharedSettings} */
public final class SharedConfig {
    private SharedConfig() {
    }

    /** 列表里格式不对的行跳过、不合规的项用默认值，原因都加进 problems（每条写明是哪一项） */
    public static SharedSettings build(List<String> problems) {
        SharedSettings.Builder b = new SharedSettings.Builder();
        b.websiteUrl = LinkConfig.websiteUrl;
        b.websiteText = LinkConfig.websiteText;
        b.manualUrl = LinkConfig.manualUrl;
        b.manualText = LinkConfig.manualText;
        b.mcmodUrl = LinkConfig.mcmodUrl;
        b.mcmodText = LinkConfig.mcmodText;
        b.rulesUrl = LinkConfig.rulesUrl;
        b.qqGroup = LinkConfig.qqGroup;
        b.qqGroupUrl = LinkConfig.qqGroupUrl;

        b.mercurySlownessAmplifier = FluidConfig.mercurySlownessAmplifier;
        b.mercurySlownessTicks = FluidConfig.mercurySlownessTicks;
        b.galliumSlownessAmplifier = FluidConfig.galliumSlownessAmplifier;
        b.galliumSlownessTicks = FluidConfig.galliumSlownessTicks;
        b.chlorinePoisonAmplifier = FluidConfig.chlorinePoisonAmplifier;
        b.chlorinePoisonTicks = FluidConfig.chlorinePoisonTicks;
        b.chlorineHelmets = trimmed(FluidConfig.chlorineProtectionHelmets);
        b.chlorineChestplates = trimmed(FluidConfig.chlorineProtectionChestplates);
        b.reactiveIngots = ingots(FluidConfig.reactiveIngots, problems);

        b.swordSlownessChance = ItemConfig.swordSlownessChance;
        b.swordSlownessTicks = ItemConfig.swordSlownessTicks;
        b.swordSlownessAmplifier = ItemConfig.swordSlownessAmplifier;
        b.redBloodGemDurabilityCost = ItemConfig.redBloodGemDurabilityCost;
        b.homeToolDurabilityCost = ItemConfig.homeToolDurabilityCost;
        b.mineralWaterHunger = ItemConfig.mineralWaterHunger;
        b.mineralWaterSaturation = (float) ItemConfig.mineralWaterSaturation;
        b.drinkingWaterHunger = ItemConfig.drinkingWaterHunger;
        b.drinkingWaterSaturation = (float) ItemConfig.drinkingWaterSaturation;
        return SharedSettings.sanitize(b.build(), problems);
    }

    private static List<String> trimmed(String[] lines) {
        String[] out = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            out[i] = lines[i] == null ? null : lines[i].trim();
        }
        return Arrays.asList(out);
    }

    private static Map<String, Float> ingots(String[] lines, List<String> problems) {
        KeyValues.Result<Map.Entry<String, Float>> r = KeyValues.parse(lines, (k, v) -> {
            float f;
            try {
                f = Float.parseFloat(v);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("“" + v + "”不是数");
            }
            return new java.util.AbstractMap.SimpleEntry<>(k, f);
        });
        for (String e : r.errors) {
            problems.add("reactiveIngots 跳过 " + e);
        }
        Map<String, Float> m = new LinkedHashMap<>();
        for (Map.Entry<String, Float> e : r.values) {
            if (m.containsKey(e.getKey())) {
                problems.add("reactiveIngots 跳过重复的 " + e.getKey() + "（用前面那一项）");
                continue;
            }
            m.put(e.getKey(), e.getValue());
        }
        return m;
    }
}
