package baguchan.earthmobsmod.client.render.layer;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.ModelMuddyPig;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlowerColorLayer extends LayerRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> {
    private static final ResourceLocation FLOWER_TEXTURE = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/muddypig/pig_muddy_flower.png");

    public FlowerColorLayer(IEntityRenderer<MuddyPigEntity,ModelMuddyPig<MuddyPigEntity>> p_i50914_1_) {
        super(p_i50914_1_);
    }

    public void render(MuddyPigEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if (!entityIn.isInvisible() && entityIn.getHasFlower() && !entityIn.isDry()) {
            this.bindTexture(FLOWER_TEXTURE);
            float[] afloat = entityIn.getFlowerColor().getColorComponentValues();
            GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
            this.getEntityModel().render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}