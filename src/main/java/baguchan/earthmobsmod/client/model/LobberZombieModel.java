package baguchan.earthmobsmod.client.model;

import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LobberZombieModel<T extends ZombieEntity> extends ZombieModel<T> {
    public LobberZombieModel(float p_i48915_1_, float p_i48915_2_, int p_i48915_3_, int p_i48915_4_) {
        super(p_i48915_1_, p_i48915_2_, p_i48915_3_, p_i48915_4_);
        this.bipedRightArm = new ModelRenderer(this, 0, 32);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_i48915_1_);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i48915_2_, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 16, 32);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i48915_1_);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i48915_2_, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 47);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_i48915_1_);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i48915_2_, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 48, 46);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_i48915_1_);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i48915_2_, 0.0F);
    }

    public LobberZombieModel(float p_i49398_1_, boolean p_i49398_2_) {
        super(p_i49398_1_, 0.0F, 64, p_i49398_2_ ? 32 : 64);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
