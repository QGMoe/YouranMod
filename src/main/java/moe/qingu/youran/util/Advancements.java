package moe.qingu.youran.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

/** 本模组的进度（定义在 assets/youranmod/advancements，除了“不错的饮用水”“这是什么？！”以外都是代码给的） */
public final class Advancements {
    public static final ResourceLocation YOURAN = id("youran");
    public static final ResourceLocation RUN_YOURAN_COMMAND = id("run_youran_command");
    public static final ResourceLocation GET_ICE_FULL = id("geticefull");
    public static final ResourceLocation WALLET = id("youran_wallet");
    public static final ResourceLocation BETTER_THAN_DIAMOND = id("itisbetterthandiamond");
    public static final ResourceLocation RECYCLING_STATION = id("a_spcial_village");
    public static final ResourceLocation MAIN_CITY = id("zhe_di_fang_zhen_bu_cuo");
    public static final ResourceLocation NONGFU_SHANQUAN = id("nongfu_shanquan");

    private Advancements() {
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation("youranmod", path);
    }

    /** 进度不存在（被数据包删了）时当作没完成 */
    public static boolean isDone(EntityPlayerMP player, ResourceLocation id) {
        Advancement advancement = player.server.getAdvancementManager().getAdvancement(id);
        return advancement != null && player.getAdvancements().getProgress(advancement).isDone();
    }

    /** 把进度剩下的条件都给上（同 1.3.4） */
    public static void grant(EntityPlayerMP player, ResourceLocation id) {
        Advancement advancement = player.server.getAdvancementManager().getAdvancement(id);
        if (advancement == null) {
            return;
        }
        AdvancementProgress progress = player.getAdvancements().getProgress(advancement);
        if (progress.isDone()) {
            return;
        }
        for (String criterion : progress.getRemaningCriteria()) {
            player.getAdvancements().grantCriterion(advancement, criterion);
        }
    }
}
