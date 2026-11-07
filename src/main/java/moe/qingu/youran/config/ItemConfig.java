package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/**
 * 物品效果里只在服务端算的部分。两端都要算的（来自异世之剑的缓慢、血红宝石的闪电与耐久等）不在这里。
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

    private ItemConfig() {
    }
}
