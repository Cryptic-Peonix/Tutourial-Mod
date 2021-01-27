package com.skyla.tutmod.common.entites;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.skyla.tutmod.client.util.ModDamageSource;
import com.skyla.tutmod.common.enchantments.ModEnchantmentHelper;
import com.skyla.tutmod.core.init.EntityTypeInit;
import com.skyla.tutmod.core.init.ItemInit;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * 
 * @author Skyla
 *
 */
public class HammerEntity extends AbstractArrowEntity {
	private static final DataParameter<Byte> THUDNERING_LEVEL = EntityDataManager.createKey(HammerEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> field_226571_aq_ = EntityDataManager.createKey(HammerEntity.class, DataSerializers.BOOLEAN);
	private ItemStack thrownStack = new ItemStack(ItemInit.NETHERITE_HAMMER);
	private boolean dealtDamage;
	public int returningTicks;
	Random rand = new Random();
	
	public HammerEntity(EntityType<? extends HammerEntity> type, World world) {
		super(type, world);
	}
	
	public HammerEntity(World worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
		super(EntityTypeInit.HAMMER, thrower, worldIn);
		this.thrownStack = thrownStackIn.copy();
		this.dataManager.set(THUDNERING_LEVEL, (byte)ModEnchantmentHelper.getThunderingModifier(thrownStackIn));
		this.dataManager.set(field_226571_aq_, thrownStackIn.hasEffect());
	}
	
	@OnlyIn(Dist.CLIENT)
	public HammerEntity(World world, double x, double y, double z) {
		super(EntityTypeInit.HAMMER, x, y, z, world);
	}
	

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(THUDNERING_LEVEL, (byte)0);
		this.dataManager.register(field_226571_aq_, false);
	}
	
	@Override
	public void tick() {
		System.out.println(this.getPosition());
		if (this.timeInGround > 4) {
			this.dealtDamage = true;			
		}
		
		Entity entity = this.func_234616_v_();
		if(this.dealtDamage || this.getNoClip() && entity != null) {
			int i = 3; //Loyalty number
			if (!this.shouldReturnToThrower()) {
				if(!this.world.isRemote && this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
					this.entityDropItem(this.getArrowStack(), 0.1F);
				}
				this.remove();
			//Loyalty Feature currently disabled for bug testing, i know that this code is currently unreachable
			} else if (i > 3) {
				this.setNoClip(true);
				Vector3d vector3d = new Vector3d(entity.getPosX() - this.getPosX(), entity.getPosY() - this.getPosY(), entity.getPosZ() - this.getPosZ());
				this.setPosition(this.getPosX(), this.getPosY() + vector3d.y * 0.015D  * (double)i, this.getPosZ());
				if(this.world.isRemote) {
					this.lastTickPosY = this.getPosY();
				}
				double d0 = 0.05D * (double) i;
				this.setMotion(this.getMotion().scale(0.95D).add(vector3d.normalize().scale(d0)));
				if(this.returningTicks == 0) {
					this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
				}
				
				++this.returningTicks;
			}
		}
		
		super.tick();
	}
	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		super.remove();
	}
	
	@Override
	public void baseTick() {
		System.out.println(this.getPosition());
		super.baseTick();
	}
	
	private boolean shouldReturnToThrower() {
		Entity entity = this.func_234616_v_();
		if(entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	@Override
	protected ItemStack getArrowStack() {
		return this.thrownStack.copy();
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean func_226572_w_() {
		return this.dataManager.get(field_226571_aq_);
	}
	
	@Nullable
	protected EntityRayTraceResult rayTraceEntites(Vector3d startVec, Vector3d endVec) {
		return this.dealtDamage ? null : super.rayTraceEntities(startVec, endVec);
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		Entity entity = result.getEntity();
		float f = 8.0f;
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			f += EnchantmentHelper.getModifierForCreature(this.thrownStack, livingEntity.getCreatureAttribute());
		}
		
		Entity entity1 = this.func_234616_v_();
		DamageSource damageSource = ModDamageSource.causeHammerDamage(this, (Entity)(entity1 == null ? this : entity1));
		this.dealtDamage = true;
		SoundEvent soundEvent = SoundEvents.BLOCK_ANVIL_PLACE;
		if (entity.attackEntityFrom(damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}
			
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity1 = (LivingEntity) entity;
				if (entity1 instanceof LivingEntity) {
					EnchantmentHelper.applyThornEnchantments(livingEntity1, entity);
					EnchantmentHelper.applyArthropodEnchantments(livingEntity1, entity);
				}
				
				this.arrowHit(livingEntity1);
			}
		}
		this.setMotion(this.getMotion().mul(-0.1D, -0.1D, -0.1D));
		float f1 = 1.0F;
		//generates a random x and z postion of 0, 1, or -1
		int randXPos = (rand.nextInt(2) - 1);
		int randZPos = (rand.nextInt(2) - 1);
		int thunderLevel = this.dataManager.get(THUDNERING_LEVEL);
		if (this.world instanceof ServerWorld && this.world.isThundering() && thunderLevel > 0) { 
			BlockPos blockPos = entity.getPosition();
			if (this.world.canSeeSky(blockPos)) {
				LightningBoltEntity light = EntityType.LIGHTNING_BOLT.create(this.world);
				light.moveForced(Vector3d.copyCenteredHorizontally(blockPos));
				light.setCaster(entity1 instanceof ServerPlayerEntity ?(ServerPlayerEntity) entity1 : null);
				this.world.addEntity(light);
				//generates a second lightning bolt at a random fixed location
				if (thunderLevel > 1) {
					BlockPos randomXZ = new BlockPos(blockPos.getX() + randXPos, blockPos.getY(), blockPos.getZ() + randZPos);
					if (this.world.canSeeSky(randomXZ)) {
						LightningBoltEntity light2 = EntityType.LIGHTNING_BOLT.create(this.world);
						light2.moveForced(Vector3d.copyCenteredHorizontally(randomXZ));
						light2.setCaster(entity1 instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity1 : null);
						this.world.addEntity(light2);
					}
				}
				soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER;
				f1 = 5.0F;
			}
		}
		
		this.playSound(soundEvent, f1, 1.0F);
	}
	
	
	@Override
	protected SoundEvent getHitEntitySound() {
		return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
	}
	
	@Override
	public void onCollideWithPlayer(PlayerEntity entityIn) {
		Entity entity = this.func_234616_v_();
		if (entity == null || entity.getUniqueID() == entityIn.getUniqueID()) {
			super.onCollideWithPlayer(entityIn);
		}
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.put("Hammer", this.thrownStack.write(new CompoundNBT()));
		compound.putBoolean("DealtDamage", this.dealtDamage);
	}

	
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Hammer", 10)) {
			this.thrownStack = ItemStack.read(compound.getCompound("Hammer"));
		}
		
		this.dealtDamage = compound.getBoolean("DealtDamage");
		this.dataManager.set(THUDNERING_LEVEL, (byte) ModEnchantmentHelper.getThunderingModifier(this.thrownStack));
	}
	


	@Override
	protected void func_225516_i_() {
		if (this.pickupStatus != AbstractArrowEntity.PickupStatus.ALLOWED) {
			super.func_225516_i_();
		}
	}
	
	@Override
	protected float getWaterDrag() {
		return 0.5F;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return true;
	}
	
	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	 
}
