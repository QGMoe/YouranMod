package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/** 传送点与解锁区域（1.3.4 写死在代码里）。坐标照 1.3.4 用方块的角（整数坐标），不是方块中心。 */
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

    @Config.Comment({"/youran tp 的传送点，一行一个：名字;维度;x;y;z;朝向;列表里的说明;传送提示里的名字;要先完成的进度",
            "朝向写“yaw,pitch”（如 90,0），留空则不改朝向；进度写注册名，留空则不用解锁就能用。",
            "名字区分大小写，不能有空白，不能是 help、list。/youran tp list 按这里的顺序列出。",
            "默认是 1.3.4 的 fw：主城维度 (-274, 69, 162)，要先完成“垃圾回收站”进度（走进下面的解锁区域时给）。"})
    @Config.RequiresMcRestart
    public static String[] points = {"fw;3;-274;69;162;;主城维度，垃圾回收站岛屿;主城-垃圾回收站岛屿;youranmod:a_spcial_village"};

    @Config.Comment({"传送点的解锁区域，一行一个：维度;最小x;最大x;最小z;最大z;最小y;给的进度;解锁的传送点名;提示里的叫法",
            "玩家走进这个范围（坐标向 0 取整，两端都含，y 只有下限）、还没有这个进度时，给进度并提示",
            "“传送点已解锁，可通过“/youran tp 传送点名”来直接到达<叫法>”。进度可以是数据包（world/data/advancements）里自己加的。",
            "默认是 1.3.4 的垃圾回收站岛屿：主城维度 x -275 到 -268、z 159 到 165、y ≥ 67，给“垃圾回收站”进度，解锁 fw。"})
    @Config.RequiresMcRestart
    public static String[] unlockAreas = {"3;-275;-268;159;165;67;youranmod:a_spcial_village;fw;该岛屿"};

    private TeleportConfig() {
    }
}
