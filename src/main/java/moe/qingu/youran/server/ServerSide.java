package moe.qingu.youran.server;

import moe.qingu.youran.server.command.CommandHub;
import moe.qingu.youran.server.command.CommandYouran;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * 只在服务端（独立服务端或单人游戏的内置服务端）运行的部分的入口：开服时登记主城区块的处理与指令，关服时撤掉。
 * 客户端不会走到这里（单人游戏时由内置服务端那一侧调用）。
 */
public final class ServerSide {
    private static MainCityChunks mainCityChunks;

    private ServerSide() {
    }

    /** 服务端启动前 */
    public static void start() {
        Settings.reload();
        stop();
        mainCityChunks = new MainCityChunks();
        MinecraftForge.EVENT_BUS.register(mainCityChunks);
    }

    /** 顺序同 1.3.4：/hub 在前 */
    public static void registerCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandHub());
        event.registerServerCommand(new CommandYouran());
    }

    /** 服务端停下以后（单人游戏退回标题画面时也会） */
    public static void stop() {
        if (mainCityChunks != null) {
            MinecraftForge.EVENT_BUS.unregister(mainCityChunks);
            mainCityChunks = null;
        }
    }
}
