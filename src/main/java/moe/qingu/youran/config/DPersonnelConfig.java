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

    @Config.Comment({"定时回血：每项“世界时间=瞬间治疗等级”（等级 0 起算，即 /effect 的 amplifier）。",
            "每个维度每 tick 检查一次，那个维度的世界时间等于某一项时，给所有维度里的 D 级人员回血（同时有几个维度到点就回几次）。",
            "照 1.3.4 比的是世界的总时间（getWorldTime，不对 24000 取余），所以只在第一天到点，之后不会再触发（1.3.4 的已知问题）。",
            "1.3.4：23000=100、6000=2；同一时刻有多项时用第一项。"})
    @Config.RequiresMcRestart
    public static String[] healSchedule = {"23000=100", "6000=2"};

    @Config.Comment("/youran saveD（权限等级 2）给的瞬间治疗等级。1.3.4：100。")
    @Config.RequiresMcRestart
    public static int saveDAmplifier = 100;

    private DPersonnelConfig() {
    }
}
