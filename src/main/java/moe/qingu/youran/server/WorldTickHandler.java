package moe.qingu.youran.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * D 级人员定时回血（1.3.4 的 RunningInDimensionTick）。
 * <p>
 * 服主 11-07 改：只看主世界的时间、按一天内的时间判断（见 {@code logic.HealSchedule}），到点时给所有已加载维度里的
 * D 级人员回一次血。1.3.4 是每个维度各自检查，几个维度同时到点就回几次；SpongeForge 下各维度的时间可以不同，
 * 只看主世界就只有一个“今天”。
 */
public final class WorldTickHandler {
    /** 上次检查时主世界的世界时间；开服后第一次检查只记下时间 */
    private long lastTime = Long.MIN_VALUE;

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote) {
            return;
        }
        World world = event.world;
        if (world.provider.getDimension() != 0) {
            return;
        }
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) {
            return;
        }
        long time = world.getWorldTime();
        if (lastTime != Long.MIN_VALUE) {
            for (int amplifier : Settings.healSchedule.reached(lastTime, time)) {
                DPersonnel.healAll(server, amplifier);
            }
        }
        lastTime = time;
    }
}
