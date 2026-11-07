package moe.qingu.youran.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 发给玩家的文字，逐字照 1.3.4（含原来的颜色代码与标点，如“主城-垃圾回收站岛屿"”两头引号不配对、
 * 回收站解锁提示里写的是“/youran tp 废物”而实际子命令是 fw）。1.3.4 写死为中文，不随客户端语言变，这里也一样。
 */
public final class Texts {
    // ---- /youran ----
    public static final String NO_PERMISSION = "§4您的操作权限等级不足！";
    public static final String GOING_TO_MAIN_CITY = "§e正在前往主城.......";
    public static final String RETURNING = "§e正在返回主世界.......";
    public static final String NOT_IN_MAIN_CITY = "§4您不在主城维度，无法返回主世界";
    public static final String TP_LIST_TITLE = "§e悠然服务器传送点列表";
    public static final String TP_LIST_FW = "§6fw - 主城维度，垃圾回收站岛屿";
    public static final String TP_TO_RECYCLE_STATION = "§2正在传送至“主城-垃圾回收站岛屿\"";
    public static final String TP_LOCKED = "§4您尚未完成前置成就！无法使用该传送点！";
    public static final List<String> ABOUT = Collections.unmodifiableList(Arrays.asList(
            "悠然服务器是一个主打休闲、冒险向的Minecraft 1.12.2版本Forge mod + SpongeForge服务器。",
            "也就是说，我们服务器是一个插件+模组服务器",
            "我们服务器的魔改很少，主要是对于一些细节方面的修改",
            "如果你想了解更多信息，请点击下面的链接："));
    public static final List<String> TP_HELP = Collections.unmodifiableList(Arrays.asList(
            "§e悠然服务器TP帮助",
            "§6youran tp help - 获取该帮助",
            "§6youran tp list - 列出所有传送点",
            "§6youran tp <传送点名称> - 传送自己至指定的传送点"));
    /** 链接前面的文字 */
    public static final String WEBSITE_LABEL = "服务器官网：";
    public static final String MANUAL_LABEL = "服务器手册：";
    public static final String MCMOD_LABEL = "服务器MC百科页面 - ";

    // ---- 主城、物品 ----
    public static final String WELCOME_MAIN_CITY = "§e欢迎来到悠然服务器大厅";
    public static final String LEFT_MAIN_CITY = "§e您已离开主城大厅";
    public static final String RECYCLE_STATION_UNLOCKED = "§2传送点已解锁，可通过“/youran tp 废物”来直接到达该岛屿";
    /** 主城维度没有登记上（维度 ID 冲突，见 world.MainCity）时；1.3.4 没有这种情况 */
    public static final String MAIN_CITY_UNAVAILABLE = "§4主城维度没有启用（维度 ID 冲突），请联系管理员";

    private Texts() {
    }

    /** /youran saveD 成功时的提示（默认配置下与 1.3.4 逐字相同） */
    public static String saveDDone(String entity, int amplifier) {
        return "§2成功执行：/effect @e[type=" + entity + "] minecraft:instant_health 1 " + amplifier;
    }

    /** /youran temperature：群系温度 ×100（float 运算与输出格式同 1.3.4，如 0.8 → 80.0） */
    public static String temperature(float biomeTemperature) {
        return "当地气温：§3" + (biomeTemperature * 100.f);
    }

    /** /youran help（和不认识的子命令）的帮助；saveD 那一行只给权限等级 2 的人看 */
    public static List<String> help(boolean showSaveD) {
        List<String> lines = new ArrayList<>();
        lines.add("§e指令使用帮助：");
        lines.add("§6youran about - 关于服务器");
        lines.add("§6youran back - 从主城返回主世界");
        lines.add("§6youran help - 显示该帮助信息");
        lines.add("§6youran maincity - 前往主城");
        if (showSaveD) {
            lines.add("§6youran saveD - 给D级人员回血");
        }
        lines.add("§6youran serverhelp - 前往服务器网页手册");
        lines.add("§6youran temperature - 获取所处地目前的温度");
        lines.add("§6youran tp - 传送至一些地方（注：youran tp list 可列出当前所有传送点）");
        lines.add("§6youran website - 前往服务器官网");
        return lines;
    }
}
