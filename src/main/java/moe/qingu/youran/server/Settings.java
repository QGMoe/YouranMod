package moe.qingu.youran.server;

import moe.qingu.youran.YouranMod;
import moe.qingu.youran.config.DPersonnelConfig;
import moe.qingu.youran.config.ItemConfig;
import moe.qingu.youran.config.TeleportConfig;
import moe.qingu.youran.logic.HealSchedule;
import moe.qingu.youran.logic.KeyValues;
import moe.qingu.youran.logic.RadiusTargets;
import moe.qingu.youran.logic.TruncatedArea;

import java.util.Collections;
import java.util.List;

/** 由配置算出来的、开服时算一次的东西（列表解析等） */
final class Settings {
    static HealSchedule healSchedule = HealSchedule.of(Collections.emptyList());
    static List<RadiusTargets.Target> redBloodGemTargets = Collections.emptyList();
    static TruncatedArea recycleArea;

    private Settings() {
    }

    static void reload() {
        KeyValues.Result<HealSchedule.Point> heal = HealSchedule.parse(DPersonnelConfig.healSchedule);
        warn("dpersonnel.healSchedule", heal.errors);
        healSchedule = HealSchedule.of(heal.values);
        KeyValues.Result<RadiusTargets.Target> targets = RadiusTargets.parse(ItemConfig.redBloodGemTargets);
        warn("items.redBloodGemTargets", targets.errors);
        redBloodGemTargets = targets.values;
        recycleArea = new TruncatedArea(TeleportConfig.recycleAreaMinX, TeleportConfig.recycleAreaMaxX,
                TeleportConfig.recycleAreaMinZ, TeleportConfig.recycleAreaMaxZ, TeleportConfig.recycleAreaMinY);
    }

    private static void warn(String key, List<String> errors) {
        for (String e : errors) {
            YouranMod.LOG.warn("配置 {} 有一项不对，跳过：{}", key, e);
        }
    }
}
