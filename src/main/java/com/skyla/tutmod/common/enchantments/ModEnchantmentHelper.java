package com.skyla.tutmod.common.enchantments;

import com.skyla.tutmod.core.init.EnchantmentInit;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

//Created by: Skyla
public class ModEnchantmentHelper {
	
	public static int getThunderingModifier(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.THUNDERING, stack);
	}
}
