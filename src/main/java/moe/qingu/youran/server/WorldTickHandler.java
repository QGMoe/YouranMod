package moe.qingu.youran.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.OptionalInt;

/**
 * 每个维度每 tick（服务端，tick 结束时）：1.3.4 的 RunningInDimensionTick。
 * 照 1.3.4 比的是世界的总时间（{@code getWorldTime()}，不对 24000 取余），所以第一天以后都对不上（已知问题，见 README）。
 */
public final class WorldTickHandler {
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote) {
            return;
        }
        World world = event.world;
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) {
            return;
        }
        long time = world.getWorldTime();
        // D 级人员定时回血（每个维度到点都触发一次，回的是所有维度里的 D 级人员，同 1.3.4）
        OptionalInt amplifier = Settings.healSchedule.amplifierAt(time);
        if (amplifier.isPresent()) {
            DPersonnel.healAll(server, amplifier.getAsInt());
        }
    }
}
