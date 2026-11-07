package moe.qingu.youran.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * 碰到会有效果的流体（汞、镓、氯气）。效果见 {@link FluidContact}；同 1.3.4，两端都执行
 * （客户端那一侧给自己加的药水效果是预测，服务端会同步）。
 */
public class BlockToxicFluid extends BlockFluidClassic {
    /** 碰到时的效果 */
    public interface Contact {
        void apply(World world, BlockPos pos, Entity entity);
    }

    private final Contact contact;

    public BlockToxicFluid(Fluid fluid, Material material, String name, Contact contact) {
        super(fluid, material);
        setRegistryName(name);
        setTranslationKey(name);
        this.contact = contact;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        super.onEntityCollision(world, pos, state, entity);
        contact.apply(world, pos, entity);
    }
}
