package baguchan.earthmobsmod.client.model;

import baguchan.earthmobsmod.entity.BoulderingZombieEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoulderingZombieModel<T extends BoulderingZombieEntity> extends ZombieModel<T> {
    public BoulderingZombieModel(float modelSize, float yOffsetIn, int textureWidthIn, int textureHeightIn) {
        super(modelSize, yOffsetIn, textureWidthIn, textureHeightIn);
        this.bipedRightArm = new ModelRenderer(this);
        this.bipedRightArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedRightArm.setTextureOffset(34, 32).addBox(-4.0F, -2.0F, -2.0F, 5.0F, 14.0F, 4.0F, 0.0F, false);
        this.bipedLeftArm = new ModelRenderer(this, 34, 32);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 5.0F, 14.0F, 4.0F, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + yOffsetIn, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this);
        this.bipedRightLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedRightLeg.setTextureOffset(0, 16).addBox(-5.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        this.bipedRightLeg.setTextureOffset(48, 46).addBox(-5.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        this.bipedLeftLeg = new ModelRenderer(this, 48, 46);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + yOffsetIn, 0.0F);
    }

    public BoulderingZombieModel(float p_i49398_1_, boolean p_i49398_2_) {
        super(p_i49398_1_, 0.0F, 64, p_i49398_2_ ? 32 : 64);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.rightArmPose = BipedModel.ArmPose.EMPTY;
        this.leftArmPose = BipedModel.ArmPose.EMPTY;

        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entityIn.isBesideClimbableBlock()) {
            this.bipedRightArm.rotateAngleX = -0.6F;
            this.bipedLeftArm.rotateAngleX = -0.6F;
            this.bipedRightArm.rotateAngleZ = (float) Math.PI + MathHelper.cos(ageInTicks * 0.6662F + (float) Math.PI) * 0.4F;
            this.bipedLeftArm.rotateAngleZ = (float) Math.PI + MathHelper.cos(ageInTicks * 0.6662F) * 0.4F;
        }

    }
}
