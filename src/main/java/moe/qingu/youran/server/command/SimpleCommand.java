package moe.qingu.youran.server.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * 1.3.4 两个指令共同的外壳（MCreator 的指令模板）：谁都能用（权限在子命令里自己查），没有别名、默认没有 Tab 补全（/youran 自己加了）；
 * 每个参数都可以写目标选择器（如 /youran @a），会对选中的每个实体各执行一次（原版指令系统的行为）。
 * 直接实现 {@link ICommand}（不继承 CommandBase），与 1.3.4 相同，SpongeForge 下的权限判断不变。
 */
abstract class SimpleCommand implements ICommand {
    private final String name;

    SimpleCommand(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + name + " [<arguments>]";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return true;
    }

    @Override
    public int compareTo(ICommand other) {
        return getName().compareTo(other.getName());
    }
}
