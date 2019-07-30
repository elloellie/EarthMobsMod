package baguchan.earthmobsmod.client.model;

import baguchan.earthmobsmod.entity.CluckShroomEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

/**
 * ModelCluckshroom - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelCluckShroom<T extends CluckShroomEntity> extends EntityModel<T> {
    public RendererModel Head;
    public RendererModel Body;
    public RendererModel RightWing;
    public RendererModel LeftWing;
    public RendererModel RightLeg;
    public RendererModel LeftLeg;
    public RendererModel Beak;
    public RendererModel Waddle;
    public RendererModel Mushroom1;
    public RendererModel Mushroom2;
    public RendererModel Mushroom3;
    public RendererModel Mushroom4;

    public ModelCluckShroom() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Waddle = new RendererModel(this, 14, 4);
        this.Waddle.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Waddle.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
        this.Mushroom3 = new RendererModel(this, 0, 19);
        this.Mushroom3.setRotationPoint(-1.0F, 1.5F, 3.0F);
        this.Mushroom3.addBox(0.0F, -5.0F, -2.5F, 0, 5, 5, 0.0F);
        this.setRotateAngle(Mushroom3, -1.5707963267948966F, 0.0F, 3.141592653589793F);
        this.RightLeg = new RendererModel(this, 26, 0);
        this.RightLeg.setRotationPoint(-1.5F, 19.0F, 1.0F);
        this.RightLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
        this.Body = new RendererModel(this, 0, 10);
        this.Body.setRotationPoint(0.0F, 16.0F, 1.0F);
        this.Body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
        this.setRotateAngle(Body, 1.5707963267948966F, 0.0F, 0.0F);
        this.Mushroom1 = new RendererModel(this, 0, 24);
        this.Mushroom1.setRotationPoint(1.0F, -6.0F, 0.0F);
        this.Mushroom1.addBox(-2.5F, -4.0F, 0.0F, 5, 5, 0, 0.0F);
        this.Beak = new RendererModel(this, 14, 0);
        this.Beak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Beak.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
        this.LeftLeg = new RendererModel(this, 26, 0);
        this.LeftLeg.setRotationPoint(1.5F, 19.0F, 1.0F);
        this.LeftLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
        this.Mushroom4 = new RendererModel(this, 0, 19);
        this.Mushroom4.setRotationPoint(-1.0F, 1.5F, 3.0F);
        this.Mushroom4.addBox(0.0F, -5.0F, -2.5F, 0, 5, 5, 0.0F);
        this.setRotateAngle(Mushroom4, -1.5707963267948966F, 0.0F, -1.5707963267948966F);
        this.RightWing = new RendererModel(this, 24, 14);
        this.RightWing.setRotationPoint(-4.0F, 13.0F, 0.0F);
        this.RightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6, 0.0F);
        this.Head = new RendererModel(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 15.0F, -4.0F);
        this.Head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
        this.LeftWing = new RendererModel(this, 24, 14);
        this.LeftWing.setRotationPoint(4.0F, 13.0F, 0.0F);
        this.LeftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6, 0.0F);
        this.Mushroom2 = new RendererModel(this, 0, 24);
        this.Mushroom2.setRotationPoint(1.0F, -6.0F, 0.0F);
        this.Mushroom2.addBox(-2.5F, -4.0F, 0.0F, 5, 5, 0, 0.0F);
        this.setRotateAngle(Mushroom2, 0.0F, 1.5707963267948966F, 0.0F);
        this.Head.addChild(this.Waddle);
        this.Body.addChild(this.Mushroom3);
        this.Head.addChild(this.Mushroom1);
        this.Head.addChild(this.Beak);
        this.Body.addChild(this.Mushroom4);
        this.Head.addChild(this.Mushroom2);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (this.isChild) {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.0F, 5.0F * scale, 2.0F * scale);
            this.Head.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
            this.RightLeg.render(scale);
            this.Body.render(scale);
            this.LeftLeg.render(scale);
            this.RightWing.render(scale);
            this.LeftWing.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.Head.render(scale);
            this.RightLeg.render(scale);
            this.Body.render(scale);
            this.LeftLeg.render(scale);
            this.RightWing.render(scale);
            this.LeftWing.render(scale);
        }
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.RightWing.rotateAngleZ = ageInTicks;
        this.LeftWing.rotateAngleZ = -ageInTicks;
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
