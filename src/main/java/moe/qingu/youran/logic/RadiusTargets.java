package moe.qingu.youran.logic;

/** 血红宝石要杀的实体：“实体注册名=半径”（1.3.4：scp:old_man=10、scp:old_man_d=5） */
public final class RadiusTargets {
    /** 一项 */
    public static final class Target {
        public final String entity;
        public final int radius;

        Target(String entity, int radius) {
            this.entity = entity;
            this.radius = radius;
        }
    }

    private RadiusTargets() {
    }

    public static KeyValues.Result<Target> parse(String[] lines) {
        return KeyValues.parse(lines, (k, v) -> {
            if (k.indexOf(':') <= 0) {
                throw new IllegalArgumentException("实体注册名要带模组前缀（如 scp:old_man）");
            }
            return new Target(k, KeyValues.parseInt(v, 0));
        });
    }
}
