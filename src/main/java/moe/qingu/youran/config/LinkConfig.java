package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/** /youran website、serverhelp、about 发的可点链接（1.3.4 写死在 tellraw 指令里）。显示的文字与打开的地址分开配。 */
@Config(modid = ServerConfig.KEY, name = ServerConfig.FILE, category = "links")
public final class LinkConfig {
    @Config.Comment("服务器官网（“服务器官网：”后面的链接）打开的地址。1.3.4：https://www.youranserver.top/")
    @Config.RequiresMcRestart
    public static String websiteUrl = "https://www.youranserver.top/";
    @Config.Comment("服务器官网链接显示的文字。1.3.4：www.youranserver.top")
    @Config.RequiresMcRestart
    public static String websiteText = "www.youranserver.top";

    @Config.Comment("服务器手册（“服务器手册：”后面的链接）打开的地址。")
    @Config.RequiresMcRestart
    public static String manualUrl = "https://qiguaideaaaa.github.io/The_Online_Manual_Guide_of_Youran_Minecraft_Server/";
    @Config.Comment("服务器手册链接显示的文字（1.3.4 与地址相同）。")
    @Config.RequiresMcRestart
    public static String manualText = "https://qiguaideaaaa.github.io/The_Online_Manual_Guide_of_Youran_Minecraft_Server/";

    @Config.Comment("MC 百科服务器页（/youran about 里“服务器MC百科页面 - ”后面的链接）打开的地址。")
    @Config.RequiresMcRestart
    public static String mcmodUrl = "https://play.mcmod.cn/sv20185890.html";
    @Config.Comment("MC 百科服务器页链接显示的文字。")
    @Config.RequiresMcRestart
    public static String mcmodText = "play.mcmod.cn/sv20185890.html";

    private LinkConfig() {
    }
}
