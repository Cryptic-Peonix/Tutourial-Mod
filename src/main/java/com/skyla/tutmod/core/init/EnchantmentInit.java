package com.skyla.tutmod.core.init;

import java.util.ArrayList;
import java.util.List;

import com.skyla.tutmod.Reference;
import com.skyla.tutmod.common.enchantments.BashingEnchantment;
//import com.skyla.tutmod.common.enchantments.LavaWalkerEnchantment;
import com.skyla.tutmod.common.enchantments.MagicProtection;
import com.skyla.tutmod.common.enchantments.ThunderingEnchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

//Author Skyla
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnchantmentInit {

	// Enchantment List
	private static final List<Enchantment> ENCHANTMENTS = new ArrayList<>();

	// All equipment slots
	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[] { EquipmentSlotType.HEAD,
			EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET };

	// Enchantments
	public static final Enchantment MAGIC_PROTECTION = register(
			new ResourceLocation(Reference.MOD_ID, "magic_protection"),
			new MagicProtection(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS));
	
	public static final Enchantment HAMMER_BASHING = register(new ResourceLocation(Reference.MOD_ID, "hammer_bashing"), 
			new BashingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND));
	
	public static final Enchantment THUNDERING = register(new ResourceLocation(Reference.MOD_ID, "thundering"),
			new ThunderingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));

	/* DOES NOT WORK
	public static final Enchantment LAVA_WALKER = register(new ResourceLocation(Reference.MOD_ID, "lava_walker"),
			new LavaWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.FEET));
	*/

	// Constructor
	private static Enchantment register(ResourceLocation key, Enchantment ench) {
		ench.setRegistryName(key);
		ENCHANTMENTS.add(ench);
		return ench;
	}

	// Register Enchants
	@SubscribeEvent
	public static void registerEnchants(RegistryEvent.Register<Enchantment> event) {
		IForgeRegistry<Enchantment> registry = event.getRegistry();
		ENCHANTMENTS.forEach(registry::register);
		ENCHANTMENTS.clear();
	}
}
