package moe.qingu.youran.logic;

import java.util.List;

/**
 * 掉进汞、镓里会爆炸的活泼金属锭（1.3.4 的 HydrargyrumPoison.canBumb / level）。
 */
public final class ReactiveIngots {
    /** 矿物词典名与爆炸威力（同 1.3.4） */
    static final String[] NAMES = {"ingotSodium", "ingotMagnesium", "ingotPotassium", "ingotCalcium", "ingotAluminum"};
    static final float[] STRENGTHS = {2.0f, 2.0f, 1.5f, 1.5f, 1.0f};

    private ReactiveIngots() {
    }

    /**
     * 爆炸威力；不爆炸时返回 -1。
     * <p>
     * 照 1.3.4：只看物品的<b>第一个</b>矿物词典名（{@code OreDictionary.getOreIDs} 的第一个），第一个不在表里就不爆炸，
     * 即使后面的名字在表里（1.3.4 的循环在第一个不匹配时就 return 了；已知问题，见 README）。
     */
    public static float explosionStrength(List<String> oreNames) {
        if (oreNames.isEmpty()) {
            return -1;
        }
        String first = oreNames.get(0);
        for (int i = 0; i < NAMES.length; i++) {
            if (NAMES[i].equals(first)) {
                return STRENGTHS[i];
            }
        }
        return -1;
    }
}
