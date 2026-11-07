package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/** 传送点与主城里的几个区域（1.3.4 写死在代码里）。坐标照 1.3.4 用方块的角（整数坐标），不是方块中心。 */
@Config(modid = ServerConfig.KEY, name = ServerConfig.FILE, category = "teleport")
public final class TeleportConfig {
    @Config.Comment({"主城传送点（主城维度里的坐标）。/hub、/youran maincity、在主城维度里掉进虚空时都传到这里。",
            "1.3.4 写死为 (-258, 70, 118)。"})
    @Config.RequiresMcRestart
    public static double mainCityX = -258;
    @Config.RequiresMcRestart
    public static double mainCityY = 70;
    @Config.RequiresMcRestart
    public static double mainCityZ = 118;

    @Config.Comment({"垃圾回收站岛屿传送点（主城维度里的坐标），/youran tp fw 传到这里。1.3.4 写死为 (-274, 69, 162)。"})
    @Config.RequiresMcRestart
    public static double recycleStationX = -274;
    @Config.RequiresMcRestart
    public static double recycleStationY = 69;
    @Config.RequiresMcRestart
    public static double recycleStationZ = 162;

    @Config.Comment({"/youran back（从主城回主世界）用的坐标。1.3.4 写死为 (-291, 64, 108)，但它的实际做法是：",
            "先把玩家移到主城维度里的这个坐标，再换到主世界，换过去以后放到主世界出生点上方一格（见 backToWorldSpawn）。",
            "所以玩家最后落在主世界出生点；只有骑着东西（或被骑着）不能换维度时，才会停在主城维度里的这个坐标（1.3.4 的已知问题）。"})
    @Config.RequiresMcRestart
    public static double backX = -291;
    @Config.RequiresMcRestart
    public static double backY = 64;
    @Config.RequiresMcRestart
    public static double backZ = 108;

    @Config.Comment({"true（默认，同 1.3.4）：/youran back 换到主世界后放到主世界出生点上方一格；",
            "false：放到主世界的 (backX, backY, backZ)。"})
    @Config.RequiresMcRestart
    public static boolean backToWorldSpawn = true;

    @Config.Comment({"在主城维度里，玩家的 y 坐标（向 0 取整）≤ 这个值时传回主城传送点（掉进虚空的保护）。1.3.4 是 0，即 y < 1。"})
    @Config.RequiresMcRestart
    public static int voidRescueY = 0;

    @Config.Comment({"垃圾回收站岛屿的范围（主城维度里，坐标向 0 取整，两端都含）：玩家走进来时给“垃圾回收站”进度，",
            "解锁 /youran tp fw。1.3.4 写死为 x -275 到 -268、z 159 到 165、y ≥ 67。"})
    @Config.RequiresMcRestart
    public static int recycleAreaMinX = -275;
    @Config.RequiresMcRestart
    public static int recycleAreaMaxX = -268;
    @Config.RequiresMcRestart
    public static int recycleAreaMinZ = 159;
    @Config.RequiresMcRestart
    public static int recycleAreaMaxZ = 165;
    @Config.RequiresMcRestart
    public static int recycleAreaMinY = 67;

    private TeleportConfig() {
    }
}
