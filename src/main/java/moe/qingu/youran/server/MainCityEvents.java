package moe.qingu.youran.server;

import moe.qingu.youran.logic.Texts;
import moe.qingu.youran.util.Advancements;
import net.minecraft.entity.player.EntityPlayerMP;

/** 进出主城维度（由 {@code world.MainCity.Provider} 在服务端调用） */
public final class MainCityEvents {
    private MainCityEvents() {
    }

    /** 进入（换维度过来或在主城里登录）：欢迎语，给“这地方针不戳”进度 */
    public static void onEnter(EntityPlayerMP player) {
        Chat.tell(player, Texts.WELCOME_MAIN_CITY);
        Advancements.grant(player, Advancements.MAIN_CITY);
    }

    /** 离开（换维度走或下线）：物品栏上方提示 */
    public static void onLeave(EntityPlayerMP player) {
        Chat.actionBar(player, Texts.LEFT_MAIN_CITY);
    }
}
