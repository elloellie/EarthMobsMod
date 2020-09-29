package baguchan.earthmobsmod.client.render.layer;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.ModelMuddyPig;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PigSkinLayer extends LayerRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> {
    private static final ResourceLocation MUDDYSKIN_TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/muddypig/pig_muddy.png");
    private static final ResourceLocation DRY_MUDSKIN = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/muddypig/pig_dry_mud.png");
    private final ModelMuddyPig<MuddyPigEntity> model = new ModelMuddyPig<>();

    public PigSkinLayer(IEntityRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> render) {
        super(render);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MuddyPigEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.isDry()) {
            this.getEntityModel().copyModelAttributesTo(this.model);
            this.model.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.model.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(DRY_MUDSKIN));
            this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.getEntityModel().copyModelAttributesTo(this.model);
            this.model.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.model.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(MUDDYSKIN_TEXTURES));
            this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}