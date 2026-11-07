package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/**
 * 给 SCP 模组的 D 级人员回血（1.3.4 用 /effect 指令做的）。找不到这种实体（没装 SCP）时什么都不做。
 * 回血是瞬间治疗：等级 n（0 起算）回 4×2^n 点，但 Java 的位移只取低 5 位，所以等级 100 实际按 4 算，回 64 点。
 */
@Config(modid = ServerConfig.KEY, name = ServerConfig.FILE, category = "dpersonnel")
public final class DPersonnelConfig {
    @Config.Comment("D 级人员的实体注册名。")
    @Config.RequiresMcRestart
    public static String entity = "scp:dpersonnel";

    @Config.Comment({"定时回血：每项“一天内的时间=瞬间治疗等级”（时间 0–23999，等级 0 起算，即 /effect 的 amplifier）。",
            "只看主世界的时间，每天到点时给所有已加载维度里的 D 级人员回一次血；睡觉或 /time add 跳过去的时刻也算到了，",
            "一次跳过好几天只回一次，时间往回调不算。同一时刻有多项时用第一项。",
            "1.3.4：23000=100、6000=2（1.3.4 拿总时间比，只有第一天对得上，服主 11-07 改成每天）。"})
    @Config.RequiresMcRestart
    public static String[] healSchedule = {"23000=100", "6000=2"};

    @Config.Comment("/youran saveD（权限等级 2）给的瞬间治疗等级。1.3.4：100。")
    @Config.RequiresMcRestart
    public static int saveDAmplifier = 100;

    private DPersonnelConfig() {
    }
}
