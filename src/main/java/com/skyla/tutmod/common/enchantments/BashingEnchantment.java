package com.skyla.tutmod.common.enchantments;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

//Author Skyla
public class BashingEnchantment extends Enchantment {

	public BashingEnchantment(Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, ModEnchantmentType.hammerType, slots);
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 1 + (enchantmentLevel - 1) * 11;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 20;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}
	
	//Not compatible with damage enchants (shaprness etc)
	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof DamageEnchantment || ench instanceof BashingEnchantment);
	}
	
	//Can also be put on an axe
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof AxeItem ? true : super.canApply(stack);
	}
	
	@Override
	public float calcDamageByCreature(int level, CreatureAttribute creatureType) {
		return 1.0f + (float) Math.max(0, level -1) * 0.5F;
	}
}
