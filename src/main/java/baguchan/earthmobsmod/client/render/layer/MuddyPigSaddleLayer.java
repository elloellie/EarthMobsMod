package baguchan.earthmobsmod.client.render.layer;

import baguchan.earthmobsmod.client.model.ModelMuddyPig;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class MuddyPigSaddleLayer extends LayerRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");

    public MuddyPigSaddleLayer(IEntityRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> render) {
        super(render);
    }

    public void render(MuddyPigEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if (entityIn.getSaddled()) {
            this.bindTexture(TEXTURE);
            this.getEntityModel().setModelAttributes(this.getEntityModel());
            ((ModelMuddyPig) this.getEntityModel()).render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}