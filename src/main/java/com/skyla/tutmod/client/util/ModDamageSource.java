package com.skyla.tutmod.client.util;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class ModDamageSource {

	public static DamageSource causeHammerDamage(Entity source, @Nullable Entity indirectEntityIn) {
		return (new IndirectEntityDamageSource("hammer", source, indirectEntityIn)).setProjectile();
	}


}
