package com.skyla.tutmod.core.init;

import java.util.ArrayList;
import java.util.List;
import com.skyla.tutmod.Reference;
import com.skyla.tutmod.common.entites.HammerEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeInit<T extends Entity> extends net.minecraftforge.registries.ForgeRegistryEntry<EntityType<?>> {

	//Create list
	private static final List<EntityType<?>> ENTITES = new ArrayList<>();
	
	//Entites
	public static final EntityType<HammerEntity> HAMMER = build(new ResourceLocation(Reference.MOD_ID, "hammer"), EntityType.Builder.<HammerEntity>create(HammerEntity::new, EntityClassification.MISC).size(1.0F, -0.2F).trackingRange(4).func_233608_b_(20));
	
	//Constructor
	private static <T extends Entity> EntityType<T> build(ResourceLocation id, EntityType.Builder<T> builder) {
		EntityType<T> type = builder.build(id.toString());
		type.setRegistryName(id);
		ENTITES.add(type);
		return type;
	}
	
	//Register Entities
	@SubscribeEvent
	public static void registerTypes(final RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> registry = event.getRegistry();
		ENTITES.forEach(registry::register);
	}
}
