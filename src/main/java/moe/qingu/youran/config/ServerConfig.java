package moe.qingu.youran.config;

import moe.qingu.youran.YouranMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

/**
 * 服务端配置 {@code config/youranmod.cfg}（Forge 的 {@code @Config}，按类分节）。
 * <p>
 * 只在<b>独立服务端</b>上读：各配置类的 {@code @Config} 用的不是模组 ID 而是 {@link #KEY}，Forge 就不会在构造模组时自动读
 * （自动读的话客户端也会生成这份文件）；由 {@link #loadOnDedicatedServer()} 在独立服务端的预初始化时读。单人游戏不读，用默认值。
 * <p>
 * 客户端也要用的项（链接、碰到汞 / 镓 / 氯气的效果等，注释里写了“同步给客户端”）由 {@link SharedConfig} 算出来，
 * 玩家登录时发给客户端（{@code net.SyncSettingsMessage}）；客户端连着服务器时用发来的值，断开后回到默认值。
 * 注册时就定死的属性（耐久、伤害、硬度等）不做配置。
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
