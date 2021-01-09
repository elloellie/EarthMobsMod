package baguchan.earthmobsmod.client.model;
// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import baguchan.earthmobsmod.entity.JollyLlamaEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JollyLlamaModel<T extends JollyLlamaEntity> extends EntityModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer chest1;
    private final ModelRenderer chest2;

    public JollyLlamaModel() {
        textureWidth = 128;
        textureHeight = 64;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 7.0F, -6.0F);
        head.setTextureOffset(0, 0).addBox(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, 0.0F, true);
        head.setTextureOffset(0, 14).addBox(-4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, 0.0F, true);
        head.setTextureOffset(35, 57).addBox(-2.5F, -14.1F, -9.0F, 5.0F, 5.0F, 2.0F, 0.0F, true);
        head.setTextureOffset(57, 52).addBox(-4.5F, -9.1F, -6.999F, 9.0F, 5.0F, 7.0F, 0.0F, true);
        head.setTextureOffset(0, 50).addBox(-4.5F, -15.1F, -6.5F, 9.0F, 7.0F, 7.0F, 0.0F, true);
        head.setTextureOffset(27, 0).addBox(1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, 0.0F, true);
        head.setTextureOffset(17, 0).addBox(-4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, 0.0F, true);
        head.setTextureOffset(73, 16).addBox(4.0F, -30.0F, -2.0F, 7.0F, 16.0F, 0.0F, 0.0F, true);
        head.setTextureOffset(73, 0).addBox(-11.0F, -30.0F, -2.0F, 7.0F, 16.0F, 0.0F, 0.0F, true);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 5.0F, 2.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        body.setTextureOffset(29, 0).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, 0.0F, true);

        this.chest1 = new ModelRenderer(this, 45, 28);
        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, 0.0F);
        this.chest1.setRotationPoint(-8.5F, 3.0F, 3.0F);
        this.chest1.rotateAngleY = ((float) Math.PI / 2F);
        this.chest2 = new ModelRenderer(this, 45, 41);
        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, 0.0F);
        this.chest2.setRotationPoint(5.5F, 3.0F, 3.0F);
        this.chest2.rotateAngleY = ((float) Math.PI / 2F);

        leg1 = new ModelRenderer(this);
        leg1.setRotationPoint(3.5F, 10.0F, 6.0F);
        leg1.setTextureOffset(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, true);

        leg2 = new ModelRenderer(this);
        leg2.setRotationPoint(-3.5F, 10.0F, 6.0F);
        leg2.setTextureOffset(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, true);

        leg3 = new ModelRenderer(this);
        leg3.setRotationPoint(3.5F, 10.0F, -5.0F);
        leg3.setTextureOffset(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, true);

        leg4 = new ModelRenderer(this);
        leg4.setRotationPoint(-3.5F, 10.0F, -5.0F);
        leg4.setTextureOffset(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.body.rotateAngleX = ((float) Math.PI / 2F);
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        boolean flag = !entityIn.isChild() && entityIn.hasChest();
        this.chest1.showModel = flag;
        this.chest2.showModel = flag;
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.isChild) {
            float f = 2.0F;
            matrixStackIn.push();
            float f1 = 0.7F;
            matrixStackIn.scale(0.71428573F, 0.64935064F, 0.7936508F);
            matrixStackIn.translate(0.0D, 1.3125D, (double) 0.22F);
            this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.pop();
            matrixStackIn.push();
            float f2 = 1.1F;
            matrixStackIn.scale(0.625F, 0.45454544F, 0.45454544F);
            matrixStackIn.translate(0.0D, 2.0625D, 0.0D);
            this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.pop();
            matrixStackIn.push();
            matrixStackIn.scale(0.45454544F, 0.41322312F, 0.45454544F);
            matrixStackIn.translate(0.0D, 2.0625D, 0.0D);
            ImmutableList.of(this.leg3, this.leg4, this.leg1, this.leg2, this.chest1, this.chest2).forEach((p_228280_8_) -> {
                p_228280_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.pop();
        } else {
            ImmutableList.of(this.head, this.body, this.leg3, this.leg4, this.leg1, this.leg2, this.chest1, this.chest2).forEach((p_228279_8_) -> {
                p_228279_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
        }

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}