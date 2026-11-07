package moe.qingu.youran.server.command;

import moe.qingu.youran.server.Teleports;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;

/** /hub：去主城（同 /youran maincity，但不发“正在前往主城”）。控制台、命令方块执行时什么也不做（同 1.3.4）。 */
public class CommandHub extends SimpleCommand {
    public CommandHub() {
        super("hub");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        Entity entity = sender.getCommandSenderEntity();
        if (entity != null) {
            Teleports.toMainCity(entity);
        }
    }
}
