package baguchan.earthmobsmod.client.render.layer;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.ModelMoobloom;
import baguchan.earthmobsmod.entity.MooBloomEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class EyelidsLayer extends LayerRenderer<MooBloomEntity, ModelMoobloom<MooBloomEntity>> {
    private static final ResourceLocation EYELIDS_TEXTURE = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/moobloom/moobloom_blink.png");

    public EyelidsLayer(IEntityRenderer<MooBloomEntity, ModelMoobloom<MooBloomEntity>> render) {
        super(render);
    }

    @Override
    public void render(MooBloomEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if (entityIn.isSleep()) {
            this.bindTexture(EYELIDS_TEXTURE);
            this.getEntityModel().setModelAttributes(this.getEntityModel());
            ((ModelMoobloom) this.getEntityModel()).render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
