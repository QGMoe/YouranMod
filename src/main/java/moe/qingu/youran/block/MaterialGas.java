package moe.qingu.youran.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;

/** 氯气的材质：同液体（可替换、不能被活塞推），地图颜色同水（同 1.3.4）。 */
public final class MaterialGas extends MaterialLiquid {
    public static final MaterialGas GAS = new MaterialGas();

    private MaterialGas() {
        super(MapColor.WATER);
    }
}
