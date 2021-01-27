package com.skyla.tutmod.client.util;

import com.skyla.tutmod.Reference;
import com.skyla.tutmod.client.render.entity.HammerRenderer;
import com.skyla.tutmod.common.entites.HammerEntity;
import com.skyla.tutmod.core.init.EntityTypeInit;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientUtils {
	
	EntityRendererManager manager;

	@SubscribeEvent
	public static void setup(FMLClientSetupEvent event) {
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.HAMMER, new HammerRenderFactory());
	}
	
	private static class HammerRenderFactory implements IRenderFactory<HammerEntity> {
		
		@Override
		public EntityRenderer<? super HammerEntity> createRenderFor(EntityRendererManager manager) {
			return new HammerRenderer(manager);
		}
	}
	
}
