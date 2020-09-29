package baguchan.earthmobsmod.client.model;

import baguchan.earthmobsmod.entity.MuddyPigEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMuddyPig - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelMuddyPig<T extends MuddyPigEntity> extends EntityModel<T> {
    public ModelRenderer RightFrontLeg;
    public ModelRenderer LeftFrontLeg;
    public ModelRenderer RightBackLeg;
    public ModelRenderer LeftBackLeg;
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer Snout;
    public ModelRenderer Dirt;
    public ModelRenderer Flower;

    protected float childYOffset = 6.0F;
    protected float childZOffset = 4.0F;

    public ModelMuddyPig() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftFrontLeg = new ModelRenderer(this, 0, 16);
        this.LeftFrontLeg.setRotationPoint(3.0F, 18.0F, -7.0F);
        this.LeftFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.RightFrontLeg = new ModelRenderer(this, 0, 16);
        this.RightFrontLeg.setRotationPoint(-3.0F, 18.0F, -7.0F);
        this.RightFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.Flower = new ModelRenderer(this, 24, 0);
        this.Flower.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Flower.addBox(-2.0F, -11.0F, -5.0F, 6, 6, 0, 0.0F);
        this.Body = new ModelRenderer(this, 28, 8);
        this.Body.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.Body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, 0.0F);
        this.setRotateAngle(Body, 1.5707963267948966F, 0.0F, 0.0F);
        this.Snout = new ModelRenderer(this, 16, 16);
        this.Snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Snout.addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 12.0F, -8.0F);
        this.Head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.LeftBackLeg = new ModelRenderer(this, 0, 16);
        this.LeftBackLeg.setRotationPoint(3.0F, 18.0F, 5.0F);
        this.LeftBackLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.RightBackLeg = new ModelRenderer(this, 0, 16);
        this.RightBackLeg.setRotationPoint(-3.0F, 18.0F, 5.0F);
        this.RightBackLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.Dirt = new ModelRenderer(this, 48, 0);
        this.Dirt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Dirt.addBox(-1.0F, -5.0F, -7.0F, 4, 1, 4, 0.0F);
        this.Head.addChild(this.Flower);
        this.Head.addChild(this.Snout);
        this.Head.addChild(this.Dirt);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.isChild) {
            float f = 2.0F;
            matrixStackIn.push();
            matrixStackIn.translate(0.0F, this.childYOffset, this.childZOffset);
            this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.pop();
            matrixStackIn.push();
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0F, 24.0F, 0.0F);
            this.LeftFrontLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.RightFrontLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.LeftBackLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.RightBackLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.pop();
        } else {
            this.LeftFrontLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.RightFrontLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.LeftBackLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.RightBackLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.RightFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.RightBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.LeftBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        if (entityIn.isRunning() && Entity.horizontalMag(entityIn.getMotion()) > 1.0E-7D) {
            this.RightFrontLeg.rotateAngleX = MathHelper.cos(ageInTicks * 0.32F) * 1.2F;
            this.LeftFrontLeg.rotateAngleX = -MathHelper.cos(ageInTicks * 0.32F) * 1.2F;
            this.RightBackLeg.rotateAngleX = -MathHelper.cos(ageInTicks * 0.32F) * 1.2F;
            this.LeftBackLeg.rotateAngleX = MathHelper.cos(ageInTicks * 0.32F) * 1.2F;
        }

        this.Flower.showModel = entityIn.getHasFlower();
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.Head.rotateAngleZ = entityIn.getShakeAngle(partialTick, 0.0F);
        this.Body.rotateAngleZ = entityIn.getShakeAngle(partialTick, -0.16F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
