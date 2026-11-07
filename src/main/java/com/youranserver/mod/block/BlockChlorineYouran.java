
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
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;

import java.util.Map;
import java.util.HashMap;

import com.youranserver.mod.procedure.ProcedureChlorineTouch;
import com.youranserver.mod.ElementsYouranmodMod;
import com.youranserver.mod.material.MaterialGas;

@ElementsYouranmodMod.ModElement.Tag
public class BlockChlorineYouran extends ElementsYouranmodMod.ModElement {
	@GameRegistry.ObjectHolder("youranmod:chlorine_youran")
	public static final Block block = null;
	@GameRegistry.ObjectHolder("youranmod:chlorine_youran")
	public static final Item item = null;
	private Fluid fluid;
	public BlockChlorineYouran(ElementsYouranmodMod instance) {
		super(instance, 65);
		fluid = new Fluid("chlorine_youran", new ResourceLocation("youranmod:blocks/chlorine_still"),
				new ResourceLocation("youranmod:blocks/chlorine_flow")).setLuminosity(0).setDensity(3).setViscosity(15000).setGaseous(true).setRarity(EnumRarity.UNCOMMON).setUnlocalizedName("chlorine_youran");
			}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockFluidClassic(fluid, MaterialGas.GAS) {
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
					ProcedureChlorineTouch.executeProcedure($_dependencies);
				}
			}
		}.setUnlocalizedName("chlorine_youran").setRegistryName("chlorine_youran"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName("chlorine_youran"));
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
				return new ModelResourceLocation("youranmod:chlorine_youran", "chlorine_youran");
			}
		});
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation("youranmod:chlorine_youran", "chlorine_youran");
			}
		});
	}

	
}
