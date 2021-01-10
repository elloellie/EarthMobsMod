package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.VilerWitchModel;
import baguchan.earthmobsmod.client.render.layer.VilerWitchHeldItemLayer;
import baguchan.earthmobsmod.entity.VilerWitchEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class VilerWitchRender extends MobRenderer<VilerWitchEntity, VilerWitchModel<VilerWitchEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/viler_witch.png");


    public VilerWitchRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VilerWitchModel<>(), 0.5F);
        this.addLayer(new VilerWitchHeldItemLayer<>(this));
    }

    protected void preRenderCallback(VilerWitchEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 0.9375F;
        matrixStackIn.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(VilerWitchEntity entity) {

        return TEXTURES;
    }
}