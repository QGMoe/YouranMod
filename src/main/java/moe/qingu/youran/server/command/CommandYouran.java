package moe.qingu.youran.server.command;

import moe.qingu.youran.config.DPersonnelConfig;
import moe.qingu.youran.logic.SharedSettings;
import moe.qingu.youran.logic.SharedState;
import moe.qingu.youran.logic.Completions;
import moe.qingu.youran.logic.TeleportPoints;
import moe.qingu.youran.logic.Texts;
import moe.qingu.youran.server.Chat;
import moe.qingu.youran.server.DPersonnel;
import moe.qingu.youran.server.Teleports;
import moe.qingu.youran.util.Advancements;
import moe.qingu.youran.world.MainCity;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * /youran 及其子命令（1.3.4 的 YouranCommand）。子命令区分大小写（如 saveD）；不认识的子命令与不带子命令时显示帮助。
 * 执行者必须是实体（玩家，或 /execute 让某个实体执行）；控制台、命令方块执行时什么也不做（同 1.3.4）。
 * 无论执行了哪个子命令，执行者是玩家时都给“盲僧，你发现了华点”进度（在帮助文字之前）。
 */
public class CommandYouran extends SimpleCommand {
    public CommandYouran() {
        super("youran");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        Entity entity = sender.getCommandSenderEntity();
        if (entity == null) {
            return;
        }
        String sub = args.length > 0 ? args[0] : "";
        SharedSettings links = SharedState.server();
        boolean showHelp = false;
        switch (sub) {
            case "website":
                Chat.link(entity, Texts.WEBSITE_LABEL, links.websiteText, links.websiteUrl);
                break;
            case "saveD":
                if (entity.canUseCommand(2, "")) {
                    DPersonnel.healAll(server, DPersonnelConfig.saveDAmplifier);
                    Chat.tell(entity, Texts.saveDDone(DPersonnelConfig.entity, DPersonnelConfig.saveDAmplifier));
                } else {
                    Chat.tell(entity, Texts.NO_PERMISSION);
                }
                break;
            case "serverhelp":
                Chat.link(entity, Texts.MANUAL_LABEL, links.manualText, links.manualUrl);
                break;
            case "maincity":
                Chat.tell(entity, Texts.GOING_TO_MAIN_CITY);
                Teleports.toMainCity(entity);
                break;
            case "help":
                // 1.3.4 这里的返回值是 114514（MCreator 过程的返回值，没有显示给谁）
                showHelp = true;
                break;
            case "about":
                // 1.3.4 这里的返回值是 1919810（同上）
                for (String line : Texts.ABOUT) {
                    Chat.tell(entity, line);
                }
                Chat.link(entity, Texts.WEBSITE_LABEL, links.websiteText, links.websiteUrl);
                Chat.link(entity, Texts.MCMOD_LABEL, links.mcmodText, links.mcmodUrl);
                Chat.link(entity, Texts.MANUAL_LABEL, links.manualText, links.manualUrl);
                break;
            case "temperature": {
                // 位置是执行位置（/execute 时是指定的位置），世界是执行者所在的世界（同 1.3.4）
                BlockPos pos = sender.getPosition();
                World world = entity.world;
                Chat.tell(entity, Texts.temperature(world.getBiome(pos).getTemperature(pos)));
                break;
            }
            case "back":
                if (MainCity.contains(entity)) {
                    Chat.tell(entity, Texts.RETURNING);
                    Teleports.back(entity);
                } else {
                    Chat.tell(entity, Texts.NOT_IN_MAIN_CITY);
                }
                break;
            case "test":
                // 1.3.4 什么也不做（返回值 "debug"）
                break;
            case "tp":
                tp(entity, args.length > 1 ? args[1] : "");
                break;
            default:
                showHelp = true;
                break;
        }
        if (entity instanceof EntityPlayerMP) {
            Advancements.grant((EntityPlayerMP) entity, Advancements.RUN_YOURAN_COMMAND);
        }
        if (showHelp) {
            for (String line : Texts.help(entity.canUseCommand(2, ""))) {
                Chat.tell(entity, line);
            }
        }
    }

    /** 子命令补全，tp 后面补传送点名（服主 11-07 加；1.3.4 没有补全） */
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Completions.youran(args, sender.canUseCommand(2, ""), TeleportPoints.names(Teleports.points()));
    }

    private static void tp(Entity entity, String target) {
        switch (target) {
            case "list":
                Chat.tell(entity, Texts.TP_LIST_TITLE);
                for (TeleportPoints.Point p : Teleports.points()) {
                    Chat.tell(entity, Texts.tpListEntry(p.name, p.description));
                }
                return;
            case "help":
            case "":
                break;
            default:
                TeleportPoints.Point point = TeleportPoints.find(Teleports.points(), target);
                if (point == null) {
                    break;
                }
                // 要解锁的传送点只给完成了那个进度的玩家用（同 1.3.4 的 fw）
                if (point.advancement != null && !(entity instanceof EntityPlayerMP
                        && Advancements.isDone((EntityPlayerMP) entity, new ResourceLocation(point.advancement)))) {
                    Chat.tell(entity, Texts.TP_LOCKED);
                    return;
                }
                Chat.tell(entity, Texts.tpGoing(point.title));
                Teleports.toPoint(entity, point);
                return;
        }
        // help、不带参数、不认识的传送点
        for (String line : Texts.TP_HELP) {
            Chat.tell(entity, line);
        }
    }
}
