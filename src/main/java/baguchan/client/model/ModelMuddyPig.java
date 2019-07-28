package baguchan.client.model;

import baguchan.earthmobsmod.entity.MuddyPigEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMuddyPig - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelMuddyPig <T extends MuddyPigEntity> extends EntityModel<T> {
    public RendererModel RightFrontLeg;
    public RendererModel LeftFrontLeg;
    public RendererModel RightBackLeg;
    public RendererModel LeftBackLeg;
    public RendererModel Body;
    public RendererModel Head;
    public RendererModel Snout;
    public RendererModel Dirt;
    public RendererModel Flower;

    protected float childYOffset = 6.0F;
    protected float childZOffset = 4.0F;

    public ModelMuddyPig() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftFrontLeg = new RendererModel(this, 0, 16);
        this.LeftFrontLeg.setRotationPoint(3.0F, 18.0F, -7.0F);
        this.LeftFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.RightFrontLeg = new RendererModel(this, 0, 16);
        this.RightFrontLeg.setRotationPoint(-3.0F, 18.0F, -7.0F);
        this.RightFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.Flower = new RendererModel(this, 24, 0);
        this.Flower.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Flower.addBox(-2.0F, -11.0F, -5.0F, 6, 6, 0, 0.0F);
        this.Body = new RendererModel(this, 28, 8);
        this.Body.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.Body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, 0.0F);
        this.setRotateAngle(Body, 1.5707963267948966F, 0.0F, 0.0F);
        this.Snout = new RendererModel(this, 16, 16);
        this.Snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Snout.addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, 0.0F);
        this.Head = new RendererModel(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 12.0F, -8.0F);
        this.Head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.LeftBackLeg = new RendererModel(this, 0, 16);
        this.LeftBackLeg.setRotationPoint(3.0F, 18.0F, 5.0F);
        this.LeftBackLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.RightBackLeg = new RendererModel(this, 0, 16);
        this.RightBackLeg.setRotationPoint(-3.0F, 18.0F, 5.0F);
        this.RightBackLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.Dirt = new RendererModel(this, 48, 0);
        this.Dirt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Dirt.addBox(-1.0F, -5.0F, -7.0F, 4, 1, 4, 0.0F);
        this.Head.addChild(this.Flower);
        this.Head.addChild(this.Snout);
        this.Head.addChild(this.Dirt);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (this.isChild) {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.0F, this.childYOffset * scale, this.childZOffset * scale);
            this.Head.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
            this.LeftFrontLeg.render(scale);
            this.RightFrontLeg.render(scale);
            this.Body.render(scale);

            this.LeftBackLeg.render(scale);
            this.RightBackLeg.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.LeftFrontLeg.render(scale);
            this.RightFrontLeg.render(scale);
            this.Body.render(scale);
            this.Head.render(scale);
            this.LeftBackLeg.render(scale);
            this.RightBackLeg.render(scale);
        }


    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.RightFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.RightBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.LeftBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

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
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
