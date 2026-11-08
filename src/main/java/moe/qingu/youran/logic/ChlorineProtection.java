package moe.qingu.youran.logic;

import java.util.Collection;

/**
 * 氯气的防护判断（同 1.3.4）：头盔栏的物品在头盔表里，或胸甲栏的物品在胸甲表里。
 * 两张表是配置（默认 IC2 防化头盔、SCP 防护服，见 {@link SharedSettings}）。
 */
public final class ChlorineProtection {
    private ChlorineProtection() {
    }

    /**
     * @param head  头盔栏物品的注册名（空栏是 minecraft:air）
     * @param chest 胸甲栏物品的注册名
     */
    public static boolean isProtected(String head, String chest, Collection<String> helmets, Collection<String> chestplates) {
        return helmets.contains(head) || chestplates.contains(chest);
    }
}
