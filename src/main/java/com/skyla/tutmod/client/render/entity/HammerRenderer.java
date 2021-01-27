package com.skyla.tutmod.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.skyla.tutmod.Reference;
import com.skyla.tutmod.client.render.entity.model.HammerModel;
import com.skyla.tutmod.common.entites.HammerEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

//Created by Skyla
public class HammerRenderer extends EntityRenderer<HammerEntity> {
	public static final ResourceLocation HAMMER = new ResourceLocation(Reference.MOD_ID, "textures/entites/hammer.png");
	private final HammerModel hammerModel = new HammerModel();

	public HammerRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(HammerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(
				MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
		matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(
				MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + 90.0F));
		IVertexBuilder ivertexbuilder = ItemRenderer.getEntityGlintVertexBuilder(bufferIn,
				this.hammerModel.getRenderType(this.getEntityTexture(entityIn)), false, entityIn.func_226572_w_());
		this.hammerModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(HammerEntity entity) {
		return HAMMER;
	}

}
