package moe.qingu.youran;

import moe.qingu.youran.config.ServerConfig;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 悠然服务器模组。服务器与客户端整合包都装。
 * <p>
 * 两端都要的：方块、物品、流体、主城维度（客户端也要知道维度类型）、创造模式物品栏、模型。
 * 只在服务端（独立服务端或单人游戏的内置服务端）的：配置、指令、tick 里的处理，见 {@code server.ServerSide}。
 * 世界生成器两端都登记，但只有服务端生成区块时才会被调用。
 */
@Mod(modid = YouranMod.MODID, useMetadata = true)
public final class YouranMod {
    public static final String MODID = "youranmod";
    public static final Logger LOG = LogManager.getLogger("YouranMod");

    static {
        // 流体的桶用 Forge 的通用桶（同 1.3.4）；必须在预初始化之前打开
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // 服务端配置只在独立服务端读（见 ServerConfig）
        if (event.getSide().isServer()) {
            ServerConfig.loadOnDedicatedServer();
        }
    }
}
