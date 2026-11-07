package moe.qingu.youran.config;

import moe.qingu.youran.YouranMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

/**
 * 服务端配置 {@code config/youranmod.cfg}（Forge 的 {@code @Config}，按类分节）。
 * <p>
 * 这些配置只给服务端的逻辑用（指令、传送、tick 里的处理、世界生成、物品效果里只在服务端算的部分），所以只在<b>独立服务端</b>上读：
 * 各配置类的 {@code @Config} 用的不是模组 ID 而是 {@link #KEY}，Forge 就不会在构造模组时自动读（自动读的话客户端也会生成这份文件）；
 * 由 {@link #loadOnDedicatedServer()} 在独立服务端的预初始化时读。客户端与单人游戏不读，用默认值（等于 1.3.4 写死的值）。
 * <p>
 * 两端都要用的数值（物品、方块的属性，碰到汞、镓、氯气的效果等，客户端要预测）没有做成配置，见各类的说明。
 * 所有配置都在开服时读一次，改了要重启服务器。
 */
public final class ServerConfig {
    /** 各配置类 {@code @Config(modid = ...)} 的值（不是模组 ID，见类说明） */
    public static final String KEY = "youranmod_server";
    /** 配置文件名（config/youranmod.cfg） */
    public static final String FILE = "youranmod";

    private ServerConfig() {
    }

    /** 独立服务端的预初始化时调用（这时 Forge 还在加载阶段，会从文件读值并写好注释） */
    public static void loadOnDedicatedServer() {
        ConfigManager.sync(KEY, Config.Type.INSTANCE);
        YouranMod.LOG.info("读了服务端配置 config/{}.cfg", FILE);
    }
}
