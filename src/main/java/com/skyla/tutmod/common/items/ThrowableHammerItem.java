package com.skyla.tutmod.common.items;

import com.skyla.tutmod.common.entites.HammerEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

/**
 * A Hammer Item than can be thrown like a trient
 * @author Skyla
 *
 */
public class ThrowableHammerItem extends HammerItem {

	public ThrowableHammerItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Properties builderIn) {
		super((float) attackDamageIn, attackSpeedIn, tier, builderIn.addToolType(ModToolType.HAMMER, tier.getHarvestLevel()));
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 7200;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity)entityLiving;
			int i = this.getUseDuration(stack) - timeLeft;
			if (i >= 10) {
				if (!worldIn.isRemote) {
					stack.damageItem(1, playerEntity, (player) -> {
						player.sendBreakAnimation(entityLiving.getActiveHand());
					});
					HammerEntity hammerEntity = new HammerEntity(worldIn, playerEntity, stack);
					 hammerEntity.func_234612_a_(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw, 0.0F, 2.5F, 1.0F);
                     if (playerEntity.abilities.isCreativeMode) {
                        hammerEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                     }

                     worldIn.addEntity(hammerEntity);
                     worldIn.playMovingSound((PlayerEntity)null, hammerEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                     if (!playerEntity.abilities.isCreativeMode) {
                        playerEntity.inventory.deleteStack(stack);
                     }
				}
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
			return ActionResult.resultFail(itemStack);
		} else {
			playerIn.setActiveHand(handIn);
			return ActionResult.resultConsume(itemStack);
		}
	}

}
