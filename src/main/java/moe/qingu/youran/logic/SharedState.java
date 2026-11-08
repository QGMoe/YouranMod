package moe.qingu.youran.logic;

/**
 * 正在用的 {@link SharedSettings}，服务端与客户端各一份。
 * <ul>
 *   <li>服务端：开服时由配置算出（独立服务端读配置文件；单人游戏的内置服务端不读，是默认值），关服时回到默认值；</li>
 *   <li>客户端：连着服务器时是服务器发来的值，断开后回到默认值。</li>
 * </ul>
 * 单人游戏里两份同时在用（内置服务端与客户端在同一个进程里），所以按“是不是客户端世界”取，不共用一份。
 */
public final class SharedState {
    private static volatile SharedSettings server = SharedSettings.DEFAULTS;
    private static volatile SharedSettings client = SharedSettings.DEFAULTS;

    private SharedState() {
    }

    /** @param remote {@code world.isRemote}：客户端的世界取客户端那份 */
    public static SharedSettings get(boolean remote) {
        return remote ? client : server;
    }

    public static SharedSettings server() {
        return server;
    }

    public static SharedSettings client() {
        return client;
    }

    public static void setServer(SharedSettings settings) {
        server = settings;
    }

    public static void setClient(SharedSettings settings) {
        client = settings;
    }

    public static void resetServer() {
        server = SharedSettings.DEFAULTS;
    }

    public static void resetClient() {
        client = SharedSettings.DEFAULTS;
    }
}
