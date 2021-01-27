package com.skyla.tutmod.common.enchantments;

import java.util.function.Predicate;

import com.skyla.tutmod.common.items.HammerItem;
import com.skyla.tutmod.common.items.ThrowableHammerItem;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.Item;

public class ModEnchantmentType {
 
	private static Predicate<Item> hammerAll = (Item) -> (Item instanceof HammerItem || Item instanceof ThrowableHammerItem);
	private static Predicate<Item> hammerThrow = (Item) -> (Item instanceof ThrowableHammerItem);
	
	public static EnchantmentType hammerType = EnchantmentType.create("hammer", hammerAll);
	public static EnchantmentType hammerThrowType = EnchantmentType.create("hammer_throw", hammerThrow);
}
