package baguchan.earthmobsmod.client.model;

import baguchan.earthmobsmod.entity.MooBloomEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMoobloom - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelMoobloom<T extends MooBloomEntity> extends EntityModel<T> {
    private float headRotationAngleX;
    public RendererModel Head;
    public RendererModel Body;
    public RendererModel RightFrontLeg;
    public RendererModel LeftFrontLeg;
    public RendererModel RightBackLeg;
    public RendererModel LeftBackLeg;
    public RendererModel RightHorn;
    public RendererModel LeftHorn;
    public RendererModel Flower1;
    public RendererModel Flower2;
    public RendererModel Udder;
    public RendererModel Flower3;
    public RendererModel Flower4;
    public RendererModel Flower5;
    public RendererModel Flower6;
    public RendererModel Flower7;
    public RendererModel Flower8;
    protected float childYOffset = 8.0F;
    protected float childZOffset = 6.0F;

    public ModelMoobloom() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Udder = new RendererModel(this, 52, 0);
        this.Udder.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Udder.addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1, 0.0F);
        this.LeftFrontLeg = new RendererModel(this, 0, 16);
        this.LeftFrontLeg.mirror = true;
        this.LeftFrontLeg.setRotationPoint(4.0F, 12.0F, -6.0F);
        this.LeftFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Flower6 = new RendererModel(this, -8, 32);
        this.Flower6.setRotationPoint(-4.0F, -2.0F, 1.0F);
        this.Flower6.addBox(-3.0F, 0.0F, 0.0F, 6, 0, 8, 0.0F);
        this.setRotateAngle(Flower6, 0.0F, 0.0F, -1.5707963267948966F);
        this.RightFrontLeg = new RendererModel(this, 0, 16);
        this.RightFrontLeg.setRotationPoint(-4.0F, 12.0F, -6.0F);
        this.RightFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Flower2 = new RendererModel(this, 0, 32);
        this.Flower2.setRotationPoint(2.0F, -2.0F, -3.0F);
        this.Flower2.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
        this.setRotateAngle(Flower2, 0.0F, 1.5707963267948966F, 0.0F);
        this.LeftBackLeg = new RendererModel(this, 0, 16);
        this.LeftBackLeg.mirror = true;
        this.LeftBackLeg.setRotationPoint(4.0F, 12.0F, 7.0F);
        this.LeftBackLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Body = new RendererModel(this, 18, 4);
        this.Body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.Body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.setRotateAngle(Body, 1.5707963267948966F, 0.0F, 0.0F);
        this.Flower5 = new RendererModel(this, -8, 32);
        this.Flower5.setRotationPoint(-4.0F, -2.0F, 1.0F);
        this.Flower5.addBox(-3.0F, 0.0F, 0.0F, 6, 0, 8, 0.0F);
        this.LeftHorn = new RendererModel(this, 22, 0);
        this.LeftHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftHorn.addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.Flower4 = new RendererModel(this, -8, 32);
        this.Flower4.setRotationPoint(4.0F, -5.0F, 2.0F);
        this.Flower4.addBox(-3.0F, 0.0F, 0.0F, 6, 0, 8, 0.0F);
        this.setRotateAngle(Flower4, 0.0F, 0.0F, -1.5707963267948966F);
        this.Flower3 = new RendererModel(this, -8, 32);
        this.Flower3.setRotationPoint(4.0F, -5.0F, 2.0F);
        this.Flower3.addBox(-3.0F, 0.0F, 0.0F, 6, 0, 8, 0.0F);
        this.Flower7 = new RendererModel(this, -8, 32);
        this.Flower7.setRotationPoint(1.0F, 5.0F, 3.0F);
        this.Flower7.addBox(-3.0F, 0.0F, 0.0F, 6, 0, 8, 0.0F);
        this.RightBackLeg = new RendererModel(this, 0, 16);
        this.RightBackLeg.setRotationPoint(-4.0F, 12.0F, 7.0F);
        this.RightBackLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Head = new RendererModel(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 4.0F, -8.0F);
        this.Head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.Flower1 = new RendererModel(this, 0, 32);
        this.Flower1.setRotationPoint(2.0F, -2.0F, -3.0F);
        this.Flower1.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
        this.RightHorn = new RendererModel(this, 22, 0);
        this.RightHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightHorn.addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.Flower8 = new RendererModel(this, -8, 32);
        this.Flower8.setRotationPoint(1.0F, 5.0F, 3.0F);
        this.Flower8.addBox(-3.0F, 0.0F, 0.0F, 6, 0, 8, 0.0F);
        this.setRotateAngle(Flower8, 0.0F, 0.0F, -1.5707963267948966F);
        this.Body.addChild(this.Udder);
        this.Body.addChild(this.Flower6);
        this.Head.addChild(this.Flower2);
        this.Body.addChild(this.Flower5);
        this.Head.addChild(this.LeftHorn);
        this.Body.addChild(this.Flower4);
        this.Body.addChild(this.Flower3);
        this.Body.addChild(this.Flower7);
        this.Head.addChild(this.Flower1);
        this.Head.addChild(this.RightHorn);
        this.Body.addChild(this.Flower8);
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
            this.LeftBackLeg.render(scale);
            this.Body.render(scale);
            this.RightBackLeg.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.Head.render(scale);
            this.LeftFrontLeg.render(scale);
            this.RightFrontLeg.render(scale);
            this.LeftBackLeg.render(scale);
            this.Body.render(scale);
            this.RightBackLeg.render(scale);
        }
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.Head.rotationPointY = 4.0F + entityIn.getHeadRotationPointY(partialTick) * 9.0F;
        this.headRotationAngleX = entityIn.getHeadRotationAngleX(partialTick);

        float l = this.swingProgress;

        if (l > 0) {
            float f = this.func_217167_a(l - partialTick, 10.0F);

            float f1 = (1.0F + f) * 0.5F;
            float f2 = f1 * f1 * f1 * 4.0F;
            this.Head.rotationPointZ = -8.0F - f2;
        } else {
            this.Head.rotationPointZ = -8.0F;
        }
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.RightFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.RightBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.LeftBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.Head.rotateAngleX = this.headRotationAngleX;
        if (entityIn.isSleep()) {
            this.Head.rotationPointY = 4.5F + MathHelper.cos(ageInTicks * 0.04F) * 0.08F;
            this.Head.rotateAngleX = 0.4F;
        } else {
            this.Head.rotationPointY = 4.0F;
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    private float func_217167_a(float p_217167_1_, float p_217167_2_) {
        return (Math.abs(p_217167_1_ % p_217167_2_ - p_217167_2_ * 0.5F) - p_217167_2_ * 0.25F) / (p_217167_2_ * 0.25F);
    }
}
