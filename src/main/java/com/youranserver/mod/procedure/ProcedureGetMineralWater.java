package com.youranserver.mod.procedure;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.server.MinecraftServer;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import java.util.Map;
import java.util.Iterator;

import com.youranserver.mod.item.ItemMineralWaterBottle;
import com.youranserver.mod.item.ItemMineralWater;
import com.youranserver.mod.block.BlockFuildMineralWater;
import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureGetMineralWater extends ElementsYouranmodMod.ModElement {
	public ProcedureGetMineralWater(ElementsYouranmodMod instance) {
		super(instance, 54);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("direction") == null) {
			System.err.println("Failed to load dependency direction for procedure GetMineralWater!");
			return;
		}
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure GetMineralWater!");
			return;
		}
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure GetMineralWater!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure GetMineralWater!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure GetMineralWater!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure GetMineralWater!");
			return;
		}
		EnumFacing direction = (EnumFacing) dependencies.get("direction");
		Entity entity = (Entity) dependencies.get("entity");
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		boolean DoCan = false;
		DoCan = (boolean) (false);
		if ((direction == EnumFacing.UP)) {
			if ((((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) z))).getBlock() == BlockFuildMineralWater.block.getDefaultState()
					.getBlock())
					&& ((("" + ((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) z))))))
							.equals("youranmod:fuild_mineral_water[level=0]")))) {
				if (entity instanceof EntityPlayer) {
					ItemStack _setstack = new ItemStack(ItemMineralWater.block, (int) (1));
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(((EntityPlayer) entity), _setstack);
				}
				if (entity instanceof EntityPlayer)
					((EntityPlayer) entity).inventory.clearMatchingItems(new ItemStack(ItemMineralWaterBottle.block, (int) (1)).getItem(), -1,
							(int) 1, null);
				{
					BlockPos _bp = new BlockPos((int) x, (int) (y + 1), (int) z);
					IBlockState _bs = Blocks.AIR.getDefaultState();
					world.setBlockState(_bp, _bs, 3);
				}
				DoCan = (boolean) (true);
			}
		} else if ((direction == EnumFacing.DOWN)) {
			if ((((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z))).getBlock() == BlockFuildMineralWater.block.getDefaultState()
					.getBlock())
					&& ((("" + ((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z))))))
							.equals("youranmod:fuild_mineral_water[level=0]")))) {
				if (entity instanceof EntityPlayer) {
					ItemStack _setstack = new ItemStack(ItemMineralWater.block, (int) (1));
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(((EntityPlayer) entity), _setstack);
				}
				if (entity instanceof EntityPlayer)
					((EntityPlayer) entity).inventory.clearMatchingItems(new ItemStack(ItemMineralWaterBottle.block, (int) (1)).getItem(), -1,
							(int) 1, null);
				{
					BlockPos _bp = new BlockPos((int) x, (int) (y - 1), (int) z);
					IBlockState _bs = Blocks.AIR.getDefaultState();
					world.setBlockState(_bp, _bs, 3);
				}
				DoCan = (boolean) (true);
			}
		} else if ((direction == EnumFacing.NORTH)) {
			if ((((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1)))).getBlock() == BlockFuildMineralWater.block.getDefaultState()
					.getBlock())
					&& ((("" + ((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1)))))))
							.equals("youranmod:fuild_mineral_water[level=0]")))) {
				if (entity instanceof EntityPlayer) {
					ItemStack _setstack = new ItemStack(ItemMineralWater.block, (int) (1));
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(((EntityPlayer) entity), _setstack);
				}
				if (entity instanceof EntityPlayer)
					((EntityPlayer) entity).inventory.clearMatchingItems(new ItemStack(ItemMineralWaterBottle.block, (int) (1)).getItem(), -1,
							(int) 1, null);
				{
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) (z - 1));
					IBlockState _bs = Blocks.AIR.getDefaultState();
					world.setBlockState(_bp, _bs, 3);
				}
				DoCan = (boolean) (true);
			}
		} else if ((direction == EnumFacing.SOUTH)) {
			if ((((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 1)))).getBlock() == BlockFuildMineralWater.block.getDefaultState()
					.getBlock())
					&& ((("" + ((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 1)))))))
							.equals("youranmod:fuild_mineral_water[level=0]")))) {
				if (entity instanceof EntityPlayer) {
					ItemStack _setstack = new ItemStack(ItemMineralWater.block, (int) (1));
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(((EntityPlayer) entity), _setstack);
				}
				if (entity instanceof EntityPlayer)
					((EntityPlayer) entity).inventory.clearMatchingItems(new ItemStack(ItemMineralWaterBottle.block, (int) (1)).getItem(), -1,
							(int) 1, null);
				{
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) (z + 1));
					IBlockState _bs = Blocks.AIR.getDefaultState();
					world.setBlockState(_bp, _bs, 3);
				}
				DoCan = (boolean) (true);
			}
		} else if ((direction == EnumFacing.WEST)) {
			if ((((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z))).getBlock() == BlockFuildMineralWater.block.getDefaultState()
					.getBlock())
					&& ((("" + ((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z))))))
							.equals("youranmod:fuild_mineral_water[level=0]")))) {
				if (entity instanceof EntityPlayer) {
					ItemStack _setstack = new ItemStack(ItemMineralWater.block, (int) (1));
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(((EntityPlayer) entity), _setstack);
				}
				if (entity instanceof EntityPlayer)
					((EntityPlayer) entity).inventory.clearMatchingItems(new ItemStack(ItemMineralWaterBottle.block, (int) (1)).getItem(), -1,
							(int) 1, null);
				{
					BlockPos _bp = new BlockPos((int) (x - 1), (int) y, (int) z);
					IBlockState _bs = Blocks.AIR.getDefaultState();
					world.setBlockState(_bp, _bs, 3);
				}
				DoCan = (boolean) (true);
			}
		} else if ((direction == EnumFacing.EAST)) {
			if ((((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) z))).getBlock() == BlockFuildMineralWater.block.getDefaultState()
					.getBlock())
					&& ((("" + ((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) z))))))
							.equals("youranmod:fuild_mineral_water[level=0]")))) {
				if (entity instanceof EntityPlayer) {
					ItemStack _setstack = new ItemStack(ItemMineralWater.block, (int) (1));
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(((EntityPlayer) entity), _setstack);
				}
				if (entity instanceof EntityPlayer)
					((EntityPlayer) entity).inventory.clearMatchingItems(new ItemStack(ItemMineralWaterBottle.block, (int) (1)).getItem(), -1,
							(int) 1, null);
				{
					BlockPos _bp = new BlockPos((int) (x + 1), (int) y, (int) z);
					IBlockState _bs = Blocks.AIR.getDefaultState();
					world.setBlockState(_bp, _bs, 3);
				}
				DoCan = (boolean) (true);
			}
		}
		if (((DoCan) == (true))) {
			if (entity instanceof EntityPlayerMP) {
				Advancement _adv = ((MinecraftServer) ((EntityPlayerMP) entity).mcServer).getAdvancementManager()
						.getAdvancement(new ResourceLocation("youranmod:nongfu_shanquan"));
				AdvancementProgress _ap = ((EntityPlayerMP) entity).getAdvancements().getProgress(_adv);
				if (!_ap.isDone()) {
					Iterator _iterator = _ap.getRemaningCriteria().iterator();
					while (_iterator.hasNext()) {
						String _criterion = (String) _iterator.next();
						((EntityPlayerMP) entity).getAdvancements().grantCriterion(_adv, _criterion);
					}
				}
			}
		}
	}
}
