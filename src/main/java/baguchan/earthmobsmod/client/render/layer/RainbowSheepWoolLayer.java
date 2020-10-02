package baguchan.earthmobsmod.client.render.layer;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.RainbowSheepFurModel;
import baguchan.earthmobsmod.client.model.RainbowSheepModel;
import baguchan.earthmobsmod.entity.RainbowSheepEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RainbowSheepWoolLayer extends LayerRenderer<RainbowSheepEntity, RainbowSheepModel<RainbowSheepEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/rainbow_sheep/rainbow_sheep_fur.png");
    private final RainbowSheepFurModel<RainbowSheepEntity> sheepModel = new RainbowSheepFurModel<>();

    public RainbowSheepWoolLayer(IEntityRenderer<RainbowSheepEntity, RainbowSheepModel<RainbowSheepEntity>> rendererIn) {
        super(rendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, RainbowSheepEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible()) {

            renderCopyCutoutModel(this.getEntityModel(), this.sheepModel, TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
        }
    }
}
