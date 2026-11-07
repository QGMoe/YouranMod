package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/**
 * 1.3.4 定时让 Dynmap 更新渲染：在指定维度的世界时间等于 worldTime 且是满月时，以服务器身份执行一条指令。
 * 没装 Dynmap 时这条指令不存在，什么都不发生。
 * 照 1.3.4 比的是世界的总时间（不对 24000 取余），满月的第一天（第 0 天）才对得上，之后不会再触发（1.3.4 的已知问题）。
 */
@Config(modid = ServerConfig.KEY, name = ServerConfig.FILE, category = "dynmap")
public final class DynmapConfig {
    @Config.Comment("要执行的指令（不带斜杠）。1.3.4：dynmap updaterender world 0 0")
    @Config.RequiresMcRestart
    public static String command = "dynmap updaterender world 0 0";

    @Config.Comment("看哪个维度的时间。1.3.4：0（主世界）")
    @Config.RequiresMcRestart
    public static int dimension = 0;

    @Config.Comment("世界时间等于这个值（且满月）时执行。1.3.4：1000")
    @Config.RequiresMcRestart
    public static int worldTime = 1000;

    private DynmapConfig() {
    }
}
