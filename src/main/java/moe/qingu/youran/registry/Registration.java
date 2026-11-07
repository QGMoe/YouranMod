package moe.qingu.youran.registry;

import moe.qingu.youran.YouranMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** 方块、物品的注册事件（两端） */
@Mod.EventBusSubscriber(modid = YouranMod.MODID)
public final class Registration {
    private Registration() {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.register(event.getRegistry());
    }
}
