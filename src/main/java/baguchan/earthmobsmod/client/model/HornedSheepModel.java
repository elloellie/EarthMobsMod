package baguchan.earthmobsmod.client.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import baguchan.earthmobsmod.entity.HornedSheepEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class HornedSheepModel<T extends HornedSheepEntity> extends QuadrupedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer bone;
    private final ModelRenderer leg2;
    private final ModelRenderer leg1;
    private final ModelRenderer leg4;
    private final ModelRenderer leg3;

    private float headRotationAngleX;

    public HornedSheepModel() {
        super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        textureWidth = 64;
        textureHeight = 32;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 12.0F, -1.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        body.setTextureOffset(22, 10).addBox(-4.0F, -6.0F, 0.0F, 8.0F, 16.0F, 6.0F, 0.0F, true);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 6.0F, -8.0F);
        head.setTextureOffset(0, 0).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F, 0.0F, true);
        head.setTextureOffset(20, 0).addBox(3.0F, -1.0F, -7.5F, 4.0F, 3.0F, 3.0F, 0.0F, true);
        head.setTextureOffset(20, 0).addBox(-7.0F, -1.0F, -7.5F, 4.0F, 3.0F, 3.0F, 0.0F, true);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(bone);
        setRotationAngle(bone, 0.0F, 0.0F, -1.5708F);
        bone.setTextureOffset(28, 0).addBox(-2.0F, 3.0F, -4.5F, 7.0F, 4.0F, 6.0F, 0.0F, false);
        bone.setTextureOffset(28, 0).addBox(-2.0F, -7.0F, -4.5F, 7.0F, 4.0F, 6.0F, 0.0F, false);

        leg2 = new ModelRenderer(this);
        leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
        leg2.setTextureOffset(0, 16).addBox(-8.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg1 = new ModelRenderer(this);
        leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
        leg1.setTextureOffset(0, 16).addBox(4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg4 = new ModelRenderer(this);
        leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
        leg4.setTextureOffset(0, 16).addBox(-8.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg3 = new ModelRenderer(this);
        leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
        leg3.setTextureOffset(0, 16).addBox(4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.head.rotateAngleX = this.headRotationAngleX;
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        float l = this.swingProgress;

        if (l > 0) {
            float f = this.swingRotation(l - partialTick, 10.0F);

            float f1 = (1.0F + f) * 0.5F;
            float f2 = f1 * f1 * f1 * 4.0F;
            this.head.rotationPointZ = -8.0F - f2;
        } else {
            this.head.rotationPointZ = -8.0F;
        }

        this.head.rotationPointY = 6.0F + entityIn.getHeadRotationPointY(partialTick) * 9.0F;
        this.headRotationAngleX = entityIn.getHeadRotationAngleX(partialTick);
    }

    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.body, this.leg1, this.leg2, this.leg3, this.leg4);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    private float swingRotation(float p_217167_1_, float p_217167_2_) {
        return (Math.abs(p_217167_1_ % p_217167_2_ - p_217167_2_ * 0.5F) - p_217167_2_ * 0.25F) / (p_217167_2_ * 0.25F);
    }
}