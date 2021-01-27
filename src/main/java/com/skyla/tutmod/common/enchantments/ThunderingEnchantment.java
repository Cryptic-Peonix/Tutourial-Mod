package com.skyla.tutmod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ThunderingEnchantment extends Enchantment {

	public ThunderingEnchantment(Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, ModEnchantmentType.hammerThrowType, slots);
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 25;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 2;
	}
	
	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return super.canApplyTogether(ench);
	}

}
