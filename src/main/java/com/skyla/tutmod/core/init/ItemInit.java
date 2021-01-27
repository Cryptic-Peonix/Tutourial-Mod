package com.skyla.tutmod.core.init;

import java.util.ArrayList;
import java.util.List;

import com.skyla.tutmod.Reference;
import com.skyla.tutmod.common.items.HammerItem;
import com.skyla.tutmod.common.items.ThrowableHammerItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

//Author: Skyla
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemInit {

	// Create Item List
	private static final List<Item> ITEMS = new ArrayList<>();

	// Items
	public static final Item TEST_ITEM = register(new ResourceLocation(Reference.MOD_ID, "test_item"),
			new Item(new Item.Properties().maxStackSize(64).group(ItemGroup.MISC)));
	
	//Food
	public static final Item CHERRY = register(new ResourceLocation(Reference.MOD_ID, "cherry"),
			new Item(new Item.Properties().maxStackSize(64).food(ModFoods.CHERRY).group(ItemGroup.FOOD)));
	
	public static final Item CHERRY_PIE = register(new ResourceLocation(Reference.MOD_ID, "cherry_pie"),
			new Item(new Item.Properties().maxStackSize(64).food(ModFoods.CHERRY_PIE).group(ItemGroup.FOOD)));
	
	//Tools
	public static final Item WOODEN_HAMMER = register(new ResourceLocation(Reference.MOD_ID, "wooden_hammer"),
			new HammerItem(7.0F, -3.4f, ItemTier.WOOD, (new Item.Properties().group(ItemGroup.TOOLS))));
	public static final Item STONE_HAMMER = register(new ResourceLocation(Reference.MOD_ID, "stone_hammer"),
			new HammerItem(7.5F, -3.4F, ItemTier.STONE, (new Item.Properties().group(ItemGroup.TOOLS))));
	public static final Item IRON_HAMMER = register(new ResourceLocation(Reference.MOD_ID, "iron_hammer"), 
			new HammerItem(7.0F, -3.3F, ItemTier.IRON, (new Item.Properties().group(ItemGroup.TOOLS))));
	public static final Item GOLD_HAMMER = register(new ResourceLocation(Reference.MOD_ID, "gold_hammer"), 
			new HammerItem(7.0F, -3.2F, ItemTier.GOLD, (new Item.Properties().group(ItemGroup.TOOLS))));
	public static final Item DIAMOND_HAMMER = register(new ResourceLocation(Reference.MOD_ID, "diamond_hammer"), 
			new HammerItem(5.0F, -3.2F, ItemTier.DIAMOND, (new Item.Properties().group(ItemGroup.TOOLS))));
	public static final Item NETHERITE_HAMMER = register(new ResourceLocation(Reference.MOD_ID, "netherite_hammer"), 
			new ThrowableHammerItem(5.0F, -3.2F, ItemTier.NETHERITE, (new Item.Properties().group(ItemGroup.TOOLS))));

	// Constructor
	private static Item register(ResourceLocation key, Item item) {
		item.setRegistryName(key);
		ITEMS.add(item);
		return item;
	}

	// Register Items to the Registry
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		ITEMS.forEach(registry::register);
		ITEMS.clear();
	}

}
