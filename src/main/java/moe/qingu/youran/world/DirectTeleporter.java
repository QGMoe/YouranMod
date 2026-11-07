package moe.qingu.youran.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * 指令传送换维度时用的传送器：什么都不做（不找、不造传送门），玩家落在原来的坐标上，之后由调用方设位置。同 1.3.4 的 TeleporterDirect。
 */
public class DirectTeleporter extends Teleporter {
    public DirectTeleporter(WorldServer world) {
        super(world);
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw) {
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        return true;
    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }
}
