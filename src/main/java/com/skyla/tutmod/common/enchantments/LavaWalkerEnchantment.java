package com.skyla.tutmod.common.enchantments;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
//import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

//Author Skyla
//TODO: FIX THIS ENCHANTMENT, CURRENTLY DOES NOT WORK
public class LavaWalkerEnchantment extends Enchantment {

	public LavaWalkerEnchantment(Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.ARMOR_FEET, slots);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return enchantmentLevel * 10;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {

		return this.getMinEnchantability(enchantmentLevel) + 15;
	}

	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@SuppressWarnings("deprecation")
	public static void coolNearby(LivingEntity living, World worldIn, BlockPos pos, int level) {
		if (living.isOnGround()) {
			BlockState blockstate = Blocks.FROSTED_ICE.getDefaultState();
			float f = (float) Math.min(16, 2 + level);
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add((double) (-f), -1.0D, (double) (-f)),
					pos.add((double) f, -1.0D, (double) f))) {
				if (blockpos.withinDistance(living.getPositionVec(), (double) f)) {
					blockpos$mutable.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = worldIn.getBlockState(blockpos$mutable);
					if (blockstate1.isAir(worldIn, blockpos$mutable)) {
						BlockState blockstate2 = worldIn.getBlockState(blockpos);
						boolean isFull = blockstate2.getBlock() == Blocks.LAVA; // TODO: Forge, modded waters?
						if (blockstate2.getMaterial() == Material.LAVA && isFull
								&& blockstate.isValidPosition(worldIn, blockpos)
								&& worldIn.placedBlockCollides(blockstate, blockpos, ISelectionContext.dummy())
								&& !ForgeEventFactory.onBlockPlace(
										living, BlockSnapshot
												.create(worldIn.getDimensionKey(), worldIn, blockpos),
										Direction.UP)) {
							worldIn.setBlockState(blockpos, blockstate);
							worldIn.getPendingBlockTicks().scheduleTick(blockpos, Blocks.FROSTED_ICE,
									MathHelper.nextInt(living.getRNG(), 60, 120));
						}
					}
				}
			}

		}
	}

	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return super.canApplyTogether(ench)
				&& (ench != Enchantments.DEPTH_STRIDER || ench != Enchantments.FROST_WALKER);
	}
}
