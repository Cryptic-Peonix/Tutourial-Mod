package com.skyla.tutmod.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

//Created by Skyla
public class MagicProtection extends Enchantment {

	public MagicProtection(Rarity rarityIn, EquipmentSlotType[] slots) {
		super(rarityIn, EnchantmentType.ARMOR, slots);

	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 5 + (enchantmentLevel - 1) * 8;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 8;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		if (source.canHarmInCreative()) {
			return 0;
		} else {
			return source.isMagicDamage() ? level * 2 : 0;
		}
	}

	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		if (ench instanceof ProtectionEnchantment) {
			ProtectionEnchantment protectionenchantment = (ProtectionEnchantment) ench;
			if (protectionenchantment.protectionType == ProtectionEnchantment.Type.FALL) {
				return super.canApplyTogether(ench);
			} else {
				return false;
			}
		} else {
			return super.canApplyTogether(ench);
		}
	}

}
