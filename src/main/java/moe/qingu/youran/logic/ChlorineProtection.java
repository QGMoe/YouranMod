package moe.qingu.youran.logic;

/** 氯气的防护判断（同 1.3.4）：头上戴 IC2 防化头盔，或身上穿 SCP 防护服。 */
public final class ChlorineProtection {
    public static final String HAZMAT_HELMET = "ic2:hazmat_helmet";
    public static final String HAZARD_SUIT = "scp:hazard_suit";

    private ChlorineProtection() {
    }

    /**
     * @param head  头盔栏物品的注册名（空栏是 minecraft:air）
     * @param chest 胸甲栏物品的注册名
     */
    public static boolean isProtected(String head, String chest) {
        return HAZMAT_HELMET.equals(head) || HAZARD_SUIT.equals(chest);
    }
}
