package baguchan.earthmobsmod.client.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import baguchan.earthmobsmod.entity.WoolyCowEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class WoolyCowModel<T extends WoolyCowEntity> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer body_sub_0;
    private final ModelRenderer bone3;
    private final ModelRenderer body_sub_2;
    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer head;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    protected float childYOffset = 8.0F;
    protected float childZOffset = 6.0F;

    public WoolyCowModel() {
        textureWidth = 64;
        textureHeight = 32;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 5.0F, 2.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        body.setTextureOffset(18, 4).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, 0.0F, true);

        body_sub_0 = new ModelRenderer(this);
        body_sub_0.setRotationPoint(2.5F, 20.0F, 32.0F);
        body.addChild(body_sub_0);
        setRotationAngle(body_sub_0, 1.5708F, -1.5708F, 0.0F);
        body_sub_0.setTextureOffset(52, 6).addBox(-42.0F, -1.5F, 30.0F, 3.0F, 8.0F, 0.0F, 0.0F, true);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(-40.5F, 2.5F, 12.0F);
        body_sub_0.addChild(bone3);
        setRotationAngle(bone3, 3.1416F, 0.0F, 0.0F);


        body_sub_2 = new ModelRenderer(this);
        body_sub_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone3.addChild(body_sub_2);
        body_sub_2.setTextureOffset(52, 6).addBox(-1.5F, -4.0F, 0.0F, 3.0F, 8.0F, 0.0F, 0.0F, true);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(6.0F, -10.0F, -8.0F);
        body.addChild(bone);
        setRotationAngle(bone, 0.0F, 0.0F, 1.5708F);
        bone.setTextureOffset(26, 0).addBox(1.0F, -0.01F, -2.0F, 16.0F, 0.0F, 3.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(9.0F, 12.01F, -0.5F);
        bone.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 0.0F, -3.1416F);
        bone2.setTextureOffset(26, 0).addBox(-8.0F, 0.0F, -1.5F, 16.0F, 0.0F, 3.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 4.0F, -8.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F, true);
        head.setTextureOffset(22, 0).addBox(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F, true);
        head.setTextureOffset(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F, true);

        leg1 = new ModelRenderer(this);
        leg1.setRotationPoint(4.0F, 12.0F, 7.0F);
        leg1.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg2 = new ModelRenderer(this);
        leg2.setRotationPoint(-4.0F, 12.0F, 7.0F);
        leg2.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg3 = new ModelRenderer(this);
        leg3.setRotationPoint(4.0F, 12.0F, -6.0F);
        leg3.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg4 = new ModelRenderer(this);
        leg4.setRotationPoint(-4.0F, 12.0F, -6.0F);
        leg4.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.body.rotateAngleX = ((float) Math.PI / 2F);
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.isChild) {
            float f = 2.0F;
            matrixStackIn.push();
            matrixStackIn.translate(0.0F, this.childYOffset / 16.0F, this.childZOffset / 16.0F);
            this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.pop();
            matrixStackIn.push();
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0F, 24.0F / 16.0F, 0.0F);
            this.leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.pop();
        } else {
            this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}