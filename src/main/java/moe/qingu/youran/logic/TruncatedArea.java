package moe.qingu.youran.logic;

/**
 * 一个长方体范围，坐标两端都含；判断用的是向 0 取整的坐标（1.3.4 用 {@code (int) posX}，不是 floor，
 * 所以 x = -275.5 算作 -275）。y 只有下限。
 */
public final class TruncatedArea {
    private final int minX;
    private final int maxX;
    private final int minZ;
    private final int maxZ;
    private final int minY;

    public TruncatedArea(int minX, int maxX, int minZ, int maxZ, int minY) {
        this.minX = Math.min(minX, maxX);
        this.maxX = Math.max(minX, maxX);
        this.minZ = Math.min(minZ, maxZ);
        this.maxZ = Math.max(minZ, maxZ);
        this.minY = minY;
    }

    public boolean contains(double posX, double posY, double posZ) {
        int x = (int) posX;
        int y = (int) posY;
        int z = (int) posZ;
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ && y >= minY;
    }
}
