package baguchan.earthmobsmod.client.render.layer;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.ModelMoobloom;
import baguchan.earthmobsmod.entity.MooBloomEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class EyelidsLayer extends LayerRenderer<MooBloomEntity, ModelMoobloom<MooBloomEntity>> {
    private static final ResourceLocation EYELIDS_TEXTURE = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/moobloom/moobloom_blink.png");
    private final ModelMoobloom<MooBloomEntity> model = new ModelMoobloom<>();

    public EyelidsLayer(IEntityRenderer<MooBloomEntity, ModelMoobloom<MooBloomEntity>> render) {
        super(render);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MooBloomEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.isSleep()) {
            this.getEntityModel().copyModelAttributesTo(this.model);
            this.model.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.model.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(EYELIDS_TEXTURE));
            this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
