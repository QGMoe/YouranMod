package moe.qingu.youran.world;

import moe.qingu.youran.YouranMod;
import moe.qingu.youran.server.MainCityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 主城维度（维度类型名 home，存档目录 DIM3）。
 * <p>
 * 维度 ID 固定是 3：存档目录、玩家所在维度都按它记。1.3.4 在 3 已被别的模组占用时会悄悄换一个空号，主城就变成了别的维度；
 * 现在改成报错并不登记主城维度（主城相关的指令、传送门也不再工作），由服主处理冲突。
 */
public final class MainCity {
    public static final int DIM_ID = 3;

    private static DimensionType type;

    private MainCity() {
    }

    /** 预初始化时调用（两端） */
    public static void register() {
        if (DimensionManager.isDimensionRegistered(DIM_ID)) {
            YouranMod.LOG.error("维度 ID {} 已被别的模组占用（{}），不登记主城维度；主城相关的指令与传送门不会工作。"
                    + "请改掉那个模组的维度 ID（主城的存档在 DIM{}，换号会丢主城）。", DIM_ID, DimensionManager.getProviderType(DIM_ID), DIM_ID);
            return;
        }
        type = DimensionType.register("home", "_home", DIM_ID, Provider.class, true);
        DimensionManager.registerDimension(DIM_ID, type);
    }

    /** 主城维度登记上了（维度 ID 没有冲突） */
    public static boolean isAvailable() {
        return type != null;
    }

    public static DimensionType type() {
        return type;
    }

    /** 实体在主城维度里 */
    public static boolean contains(Entity entity) {
        return isAvailable() && entity.dimension == DIM_ID;
    }

    /**
     * 主城维度：全是平原群系的虚空，新区块完全是空的（见 {@link MainCityChunkGenerator}），有天空光照，最暗处也有一半亮度，
     * 可以睡觉、可以在这里重生，水不会蒸发。
     * <p>
     * 必须是嵌套类（同 1.3.4 的 WorldHome$WorldProviderMod）：SpongeForge 按提供者的类名找它属于哪个模组，
     * 用“类名去掉 .简单类名”当包名，嵌套类的类名里是 $，对不上，于是记成 unknown，维度类型 ID 是 unknown:home
     * （存档 DIM3/level_sponge.dat、配置 config/sponge/worlds/unknown/home/）。改成顶层类会变成 youranmod:home，
     * Sponge 就换用一份新的维度配置（默认开服不加载）。
     */
    public static class Provider extends WorldProvider {
        @Override
        protected void init() {
            // 1.3.4 用的是只有平原一种群系的群系层（放大、Voronoi 之后仍处处是平原），结果与单一群系相同
            Biome plains = ForgeRegistries.BIOMES.getValue(new ResourceLocation("plains"));
            this.biomeProvider = new BiomeProviderSingle(plains != null ? plains : Biomes.PLAINS);
            this.nether = false;
            this.hasSkyLight = true;
        }

        @Override
        public DimensionType getDimensionType() {
            return type();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public Vec3d getFogColor(float celestialAngle, float partialTicks) {
            return new Vec3d(0.753f, 0.847f, 1);
        }

        @Override
        public IChunkGenerator createChunkGenerator() {
            return new MainCityChunkGenerator(this.world);
        }

        @Override
        public boolean isSurfaceWorld() {
            return true;
        }

        @Override
        public boolean canRespawnHere() {
            return true;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public boolean doesXZShowFog(int x, int z) {
            return false;
        }

        @Override
        public WorldSleepResult canSleepAt(EntityPlayer player, BlockPos pos) {
            return WorldSleepResult.ALLOW;
        }

        /** 亮度表：最暗（光照 0）也有 0.5 */
        @Override
        protected void generateLightBrightnessTable() {
            float minBrightness = 0.5f;
            for (int i = 0; i <= 15; ++i) {
                float f = 1 - (float) i / 15f;
                this.lightBrightnessTable[i] = (1 - f) / (f * 3 + 1) * (1 - minBrightness) + minBrightness;
            }
        }

        @Override
        public boolean doesWaterVaporize() {
            return false;
        }

        /** 玩家进入这个维度（换维度过来或在这里登录），只在服务端调用 */
        @Override
        public void onPlayerAdded(EntityPlayerMP player) {
            MainCityEvents.onEnter(player);
        }

        /** 玩家离开这个维度（换维度走或下线），只在服务端调用 */
        @Override
        public void onPlayerRemoved(EntityPlayerMP player) {
            MainCityEvents.onLeave(player);
        }
    }
}
