package moe.qingu.youran.logic;

import java.util.List;
import java.util.Map;

/**
 * 掉进汞、镓里会爆炸的活泼金属锭（1.3.4 的 HydrargyrumPoison.canBumb / level）。矿物词典名与爆炸威力的表是配置
 * （默认同 1.3.4，见 {@link SharedSettings}）。
 */
public final class ReactiveIngots {
    private ReactiveIngots() {
    }

    /**
     * 爆炸威力；不爆炸时返回 -1。
     * <p>
     * 照 1.3.4：只看物品的<b>第一个</b>矿物词典名（{@code OreDictionary.getOreIDs} 的第一个），第一个不在表里就不爆炸，
     * 即使后面的名字在表里（1.3.4 的循环在第一个不匹配时就 return 了；已知问题）。
     */
    public static float explosionStrength(List<String> oreNames, Map<String, Float> table) {
        if (oreNames.isEmpty()) {
            return -1;
        }
        Float strength = table.get(oreNames.get(0));
        return strength == null ? -1 : strength;
    }
}
