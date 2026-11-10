package moe.qingu.youran.config;

import net.minecraftforge.common.config.Config;

/**
 * /youran website、serverhelp、about 发的可点链接（1.3.4 写死在 tellraw 指令里），以及手册书（Patchouli 的 youranmod:guide）
 * 里的链接。显示的文字与打开的地址分开配。全部同步给客户端：客户端连着服务器时手册书里显示的是服务器配置的值
 * （写进书的宏，见 {@code client.PatchouliLinks}），断开后回到 book.json 里的默认值。
 * <p>
 * 地址要以 http:// 或 https:// 开头，不能有空白、括号、$、§（括号与 $ 会破坏手册书的链接写法）；文字不能有“$(”与换行。
 * 不合规的项用默认值（开服日志里有警告）。
 */
@Config(modid = ServerConfig.KEY, name = ServerConfig.FILE, category = "links")
public final class LinkConfig {
    @Config.Comment("服务器官网（“服务器官网：”后面的链接）打开的地址。1.3.4 是 https://www.youranserver.top/（已停用）")
    @Config.RequiresMcRestart
    public static String websiteUrl = "https://youran.qingu.moe/";
    @Config.Comment("服务器官网链接显示的文字。")
    @Config.RequiresMcRestart
    public static String websiteText = "youran.qingu.moe";

    @Config.Comment("服务器手册（“服务器手册：”后面的链接）打开的地址。1.3.4 是 GitHub Pages 上的旧手册（已停用）")
    @Config.RequiresMcRestart
    public static String manualUrl = "https://docs.youran.qingu.moe/";
    @Config.Comment("服务器手册链接显示的文字。")
    @Config.RequiresMcRestart
    public static String manualText = "docs.youran.qingu.moe";

    @Config.Comment("MC 百科服务器页（/youran about 里“服务器MC百科页面 - ”后面的链接）打开的地址。")
    @Config.RequiresMcRestart
    public static String mcmodUrl = "https://play.mcmod.cn/sv20185890.html";
    @Config.Comment("MC 百科服务器页链接显示的文字。")
    @Config.RequiresMcRestart
    public static String mcmodText = "play.mcmod.cn/sv20185890.html";

    @Config.Comment("服务器规则文档的地址（手册书“规则”一页里的链接）。")
    @Config.RequiresMcRestart
    public static String rulesUrl = "https://docs.qq.com/doc/DVHpzQUdQSlFuT0ha";

    @Config.Comment("服务器 QQ 群号（手册书首页显示的文字）。")
    @Config.RequiresMcRestart
    public static String qqGroup = "953168624";

    @Config.Comment("服务器 QQ 群的加群链接（点手册书首页的群号打开的地址）。")
    @Config.RequiresMcRestart
    public static String qqGroupUrl = "https://jq.qq.com/?_wv=1027&k=Hwv4w2NU";

    private LinkConfig() {
    }
}
