package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/**
 * 物品效果。标了“同步给客户端”的项两端都要算（客户端预测），玩家登录时发给客户端（见 {@code logic.SharedSettings}）；
 * 其余只在服务端算。注册时就定死的属性（耐久、伤害、挖掘等级、叠放数量等）不做配置。
 */
@Config(modid = ServerConfig.KEY, name = ServerConfig.FILE, category = "items")
public final class ItemConfig {
    @Config.Comment("来自异世之剑打中生物时掉经验球的概率。1.3.4：0.3")
    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0, max = 1)
    public static double swordXpChance = 0.3;

    @Config.Comment("经验球最多几点（在 1 到这个数之间随机）。1.3.4：3")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 1)
    public static int swordXpMax = 3;

    @Config.Comment("来自异世之剑打中生物时，给目标附近最近的玩家瞬间治疗的概率。1.3.4：0.1")
    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0, max = 1)
    public static double swordHealChance = 0.1;

    @Config.Comment("上面那个“附近”的半径（格，从目标所在方块的角算起）。1.3.4：10")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    public static int swordHealRadius = 10;

    @Config.Comment("瞬间治疗的等级（0 起算；1 = 瞬间治疗 II，回 8 点）。1.3.4：1")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 255)
    public static int swordHealAmplifier = 1;

    @Config.Comment({"血红宝石右键时杀死附近的哪些实体：每项“实体注册名=半径”（格，从玩家所在方块的角算起）。",
            "1.3.4：scp:old_man=10（SCP-106）、scp:old_man_d=5。"})
    @Config.RequiresMcRestart
    public static String[] redBloodGemTargets = {"scp:old_man=10", "scp:old_man_d=5"};

    @Config.Comment("血红宝石右键时自己受到的魔法伤害。1.3.4：5")
    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0)
    public static double redBloodGemSelfDamage = 5;

    @Config.Comment("来自异世之剑打中生物时给目标缓慢的概率。同步给客户端。1.3.4：0.5")
    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0, max = 1)
    public static double swordSlownessChance = 0.5;

    @Config.Comment("上面那个缓慢的时长（tick）。同步给客户端。1.3.4：5")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 1, max = 1000000)
    public static int swordSlownessTicks = 5;

    @Config.Comment("上面那个缓慢的等级（0 起算；2 = 缓慢 III）。同步给客户端。1.3.4：2")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 127)
    public static int swordSlownessAmplifier = 2;

    @Config.Comment("血红宝石每右键一次用掉几点耐久（共 60 点）。同步给客户端。1.3.4：1")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 10000)
    public static int redBloodGemDurabilityCost = 1;

    @Config.Comment("奇怪的工具每点一次用掉几点耐久（共 64 点；点没点燃传送门都算）。同步给客户端。1.3.4：1")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 10000)
    public static int homeToolDurabilityCost = 1;

    @Config.Comment("喝一瓶矿泉水补的饥饿值（半个鸡腿 = 1）。同步给客户端。补多少水分由 TAN 的配置定。1.3.4：1")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 20)
    public static int mineralWaterHunger = 1;

    @Config.Comment("矿泉水的饱和度系数（补的饱和度 = 饥饿值 × 系数 × 2）。同步给客户端。1.3.4：0.3")
    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0, max = 10)
    public static double mineralWaterSaturation = 0.3;

    @Config.Comment("喝一瓶饮用水补的饥饿值。同步给客户端。1.3.4：0")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 20)
    public static int drinkingWaterHunger = 0;

    @Config.Comment("饮用水的饱和度系数。同步给客户端。1.3.4：0.2")
    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0, max = 10)
    public static double drinkingWaterSaturation = 0.2;

    private ItemConfig() {
    }
}
