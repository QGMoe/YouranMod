package moe.qingu.youran;

import moe.qingu.youran.config.ServerConfig;
import moe.qingu.youran.registry.ModBlocks;
import moe.qingu.youran.registry.ModFluids;
import moe.qingu.youran.registry.ModItems;
import moe.qingu.youran.registry.YouranTab;
import moe.qingu.youran.server.ServerSide;
import moe.qingu.youran.world.MainCity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
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
        // 顺序同 1.3.4：流体与创造模式物品栏在方块、物品的注册事件之前
        ModFluids.register();
        YouranTab.create();
        MainCity.register();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.addSmelting(new ItemStack(ModBlocks.primogemOre), new ItemStack(ModItems.primogem), 1F);
        OreDictionary.registerOre("primogem", new ItemStack(ModItems.primogem));
        OreDictionary.registerOre("primogem_ore", new ItemStack(ModBlocks.primogemOre));
    }

    /** 服务端（独立服务端或进单人世界时的内置服务端）启动前：读配置、登记 tick 处理 */
    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        ServerSide.start();
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        ServerSide.stop();
    }
}
