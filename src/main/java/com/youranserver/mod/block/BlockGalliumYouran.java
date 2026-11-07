
package com.youranserver.mod.block;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

import java.util.Map;
import java.util.HashMap;

import com.youranserver.mod.procedure.ProcedureGalliumTouch;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class BlockGalliumYouran extends ElementsYouranmodMod.ModElement {
	@GameRegistry.ObjectHolder("youranmod:gallium_youran")
	public static final Block block = null;
	@GameRegistry.ObjectHolder("youranmod:gallium_youran")
	public static final Item item = null;
	private Fluid fluid;
	public BlockGalliumYouran(ElementsYouranmodMod instance) {
		super(instance, 64);
		fluid = new Fluid("gallium_youran", new ResourceLocation("youranmod:blocks/gallium_still"),
				new ResourceLocation("youranmod:blocks/gallium_flow")).setLuminosity(0).setDensity(5910).setViscosity(2300).setGaseous(false);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockFluidClassic(fluid, Material.WATER) {
			@Override
			public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
				super.onEntityCollidedWithBlock(world, pos, state, entity);
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					$_dependencies.put("x", x);
					$_dependencies.put("y", y);
					$_dependencies.put("z", z);
					$_dependencies.put("world", world);
					ProcedureGalliumTouch.executeProcedure($_dependencies);
				}
			}
		}.setUnlocalizedName("gallium_youran").setRegistryName("gallium_youran"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName("gallium_youran"));
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		ModelBakery.registerItemVariants(item);
		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation("youranmod:gallium_youran", "gallium_youran");
			}
		});
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation("youranmod:gallium_youran", "gallium_youran");
			}
		});
	}
}
