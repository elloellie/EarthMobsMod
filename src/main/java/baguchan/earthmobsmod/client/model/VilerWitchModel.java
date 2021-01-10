package baguchan.earthmobsmod.client.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import baguchan.earthmobsmod.entity.VilerWitchEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class VilerWitchModel<T extends VilerWitchEntity> extends EntityModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer nose;
    private final ModelRenderer body;
    private final ModelRenderer arms;
    private final ModelRenderer mirrored;
    private final ModelRenderer arms_sub_1;
    private final ModelRenderer right_leg;
    private final ModelRenderer right_leg_sub_0;
    private final ModelRenderer left_leg;
    private final ModelRenderer bodywear;
    private final ModelRenderer headwear;
    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer bone3;
    private final ModelRenderer headwear2;
    private final ModelRenderer mole;

    public VilerWitchModel() {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);
        head.setTextureOffset(28, 46).addBox(-4.5F, -4.5F, -4.5F, 9.0F, 5.0F, 9.0F, 0.0F, false);

        nose = new ModelRenderer(this);
        nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        nose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, 0.0F, false);

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, 2.0F, 0.0F);
        arms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);
        arms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

        mirrored = new ModelRenderer(this);
        mirrored.setRotationPoint(0.0F, 22.0F, 0.0F);
        arms.addChild(mirrored);
        mirrored.setTextureOffset(44, 22).addBox(4.0F, -24.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, true);
        mirrored.setTextureOffset(32, 0).addBox(4.0F, -25.5F, -3.0F, 5.0F, 5.0F, 6.0F, 0.0F, true);

        arms_sub_1 = new ModelRenderer(this);
        arms_sub_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        mirrored.addChild(arms_sub_1);
        arms_sub_1.setTextureOffset(32, 0).addBox(-9.0F, -25.5F, -3.0F, 5.0F, 5.0F, 6.0F, 0.0F, true);

        right_leg = new ModelRenderer(this);
        right_leg.setRotationPoint(2.0F, 12.0F, 0.0F);
        right_leg.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        right_leg_sub_0 = new ModelRenderer(this);
        right_leg_sub_0.setRotationPoint(-2.0F, 12.0F, 0.0F);
        right_leg.addChild(right_leg_sub_0);
        right_leg_sub_0.setTextureOffset(52, 11).addBox(-0.5F, -5.0F, -2.5F, 5.0F, 3.0F, 5.0F, 0.0F, true);

        left_leg = new ModelRenderer(this);
        left_leg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        left_leg.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        left_leg.setTextureOffset(32, 11).addBox(-2.5F, 7.0F, -2.5F, 5.0F, 3.0F, 5.0F, 0.0F, false);

        bodywear = new ModelRenderer(this);
        bodywear.setRotationPoint(0.0F, 0.0F, 0.0F);
        bodywear.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.5F, false);
        bodywear.setTextureOffset(0, 95).addBox(-5.0F, 0.0F, -3.5F, 10.0F, 19.0F, 7.0F, 0.0F, false);
        bodywear.setTextureOffset(30, 60).addBox(-5.5F, 9.0F, -4.0F, 11.0F, 2.0F, 8.0F, 0.0F, false);

        headwear = new ModelRenderer(this);
        headwear.setRotationPoint(5.0F, -10.0F, -5.0F);
        headwear.setTextureOffset(0, 64).addBox(-10.0F, -0.1F, 0.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
        headwear.setTextureOffset(14, 106).addBox(-15.0F, -0.1F, -5.0F, 20.0F, 2.0F, 20.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(-8.5F, -0.1F, 1.5F);
        headwear.addChild(bone);
        setRotationAngle(bone, -0.0524F, 0.0F, 0.0349F);
        bone.setTextureOffset(0, 76).addBox(0.0F, -4.0F, 0.0F, 7.0F, 4.0F, 7.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(1.5F, -4.0F, 1.5F);
        bone.addChild(bone2);
        setRotationAngle(bone2, -0.1222F, 0.0F, 0.0698F);
        bone2.setTextureOffset(0, 87).addBox(0.0F, -4.0F, 0.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(1.5F, -4.0F, 1.5F);
        bone2.addChild(bone3);
        setRotationAngle(bone3, -0.2618F, 0.0F, 0.1047F);
        bone3.setTextureOffset(0, 95).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        headwear2 = new ModelRenderer(this);
        headwear2.setRotationPoint(0.0F, -4.0F, 14.0F);


        mole = new ModelRenderer(this);
        mole.setRotationPoint(0.0F, -4.0F, 0.0F);
        mole.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, -0.25F, false);

        this.head.addChild(this.headwear);
        this.head.addChild(this.nose);
        this.headwear.addChild(this.headwear2);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleZ = 0.0F;

        float f = 0.01F * (float) (entity.getEntityId() % 10);

        this.arms.rotationPointY = 3.0F;
        this.arms.rotationPointZ = -1.0F;
        this.arms.rotateAngleX = -0.75F;
        this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.right_leg.rotateAngleY = 0.0F;
        this.left_leg.rotateAngleY = 0.0F;
        this.nose.rotateAngleX = MathHelper.sin((float) entity.ticksExisted * f) * 4.5F * ((float) Math.PI / 180F);
        this.nose.rotateAngleY = 0.0F;
        this.nose.rotateAngleZ = MathHelper.cos((float) entity.ticksExisted * f) * 2.5F * ((float) Math.PI / 180F);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        arms.render(matrixStack, buffer, packedLight, packedOverlay);
        right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        bodywear.render(matrixStack, buffer, packedLight, packedOverlay);
        mole.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public ModelRenderer getNose() {
        return nose;
    }

    public ModelRenderer getModelHead() {
        return head;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}