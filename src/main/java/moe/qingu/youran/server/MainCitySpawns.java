package moe.qingu.youran.server;

import moe.qingu.youran.world.MainCity;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 主城维度里生物不自然刷新（服主 11-07）。
 * <p>
 * 原版的自然刷新已经因为刷怪表为空而停了（{@code world.MainCityChunkGenerator#getPossibleCreatures}）；
 * 这里再拦下走 Forge 刷怪检查（{@link LivingSpawnEvent.CheckSpawn}）的其他自然刷新，比如别的模组自己写的刷怪逻辑。
 * 刷怪笼（{@code isSpawner()}）不拦；刷怪蛋、指令召唤、繁殖本来就不经过这个检查。
 */
public final class MainCitySpawns {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        if (event.getWorld().isRemote || event.isSpawner() || event.getWorld().provider.getDimension() != MainCity.DIM_ID
                || !MainCity.isAvailable()) {
            return;
        }
        event.setResult(Event.Result.DENY);
    }
}
