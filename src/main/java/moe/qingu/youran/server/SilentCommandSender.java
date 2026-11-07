package moe.qingu.youran.server;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 以服务器身份执行别的模组的指令用的执行者（同 1.3.4 的那个匿名 ICommandSender）：什么权限都有，不输出反馈，名字是空的，
 * 位置在 (0, 64, 0)。
 */
final class SilentCommandSender implements ICommandSender {
    private final World world;

    SilentCommandSender(World world) {
        this.world = world;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean canUseCommand(int permLevel, String commandName) {
        return true;
    }

    @Override
    public World getEntityWorld() {
        return world;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return world.getMinecraftServer();
    }

    @Override
    public boolean sendCommandFeedback() {
        return false;
    }

    @Override
    public BlockPos getPosition() {
        return new BlockPos(0, 64, 0);
    }

    @Override
    public Vec3d getPositionVector() {
        return new Vec3d(0, 64, 0);
    }
}
