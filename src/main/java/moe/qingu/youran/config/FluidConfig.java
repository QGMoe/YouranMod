package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/**
 * 碰到汞、镓、氯气时的效果与防护，掉进汞、镓里会爆炸的锭。客户端也要算（预测），所以玩家登录时同步给客户端
 * （见 {@code logic.SharedSettings}）。
 */
@Config(modid = ServerConfig.KEY, name = ServerConfig.FILE, category = "fluids")
public final class FluidConfig {
    @Config.Comment("碰到汞时缓慢的等级（0 起算；5 = 缓慢 VI）。同步给客户端。1.3.4：5")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 127)
    public static int mercurySlownessAmplifier = 5;

    @Config.Comment("碰到汞时缓慢的时长（tick；每 tick 碰着就再给一次）。同步给客户端。1.3.4：1")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 1, max = 1000000)
    public static int mercurySlownessTicks = 1;

    @Config.Comment("碰到镓时缓慢的等级（0 起算；3 = 缓慢 IV）。同步给客户端。1.3.4：3")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 127)
    public static int galliumSlownessAmplifier = 3;

    @Config.Comment("碰到镓时缓慢的时长（tick）。同步给客户端。1.3.4：1")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 1, max = 1000000)
    public static int galliumSlownessTicks = 1;

    @Config.Comment("碰到氯气时中毒的等级（0 起算；2 = 中毒 III）。同步给客户端。1.3.4：2")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 127)
    public static int chlorinePoisonAmplifier = 2;

    @Config.Comment("碰到氯气时中毒的时长（tick）。同步给客户端。1.3.4：300（15 秒）")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 1, max = 1000000)
    public static int chlorinePoisonTicks = 300;

    @Config.Comment({"戴在头盔栏就不受氯气影响的物品（注册名，一行一个）。同步给客户端。",
            "1.3.4：ic2:hazmat_helmet（IC2 防化头盔）"})
    @Config.RequiresMcRestart
    public static String[] chlorineProtectionHelmets = {"ic2:hazmat_helmet"};

    @Config.Comment({"穿在胸甲栏就不受氯气影响的物品（注册名，一行一个）。同步给客户端。",
            "1.3.4：scp:hazard_suit（SCP 防护服）"})
    @Config.RequiresMcRestart
    public static String[] chlorineProtectionChestplates = {"scp:hazard_suit"};

    @Config.Comment({"掉进汞、镓里会爆炸的锭：每项“矿物词典名=爆炸威力”（0–20；TNT 是 4）。同步给客户端。",
            "同 1.3.4 只看物品的第一个矿物词典名。1.3.4：钠、镁 2，钾、钙 1.5，铝 1。"})
    @Config.RequiresMcRestart
    public static String[] reactiveIngots = {"ingotSodium=2", "ingotMagnesium=2", "ingotPotassium=1.5", "ingotCalcium=1.5",
            "ingotAluminum=1"};

    private FluidConfig() {
    }
}
