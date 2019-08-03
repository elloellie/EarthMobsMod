package baguchan.earthmobsmod.client.render.layer;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.ModelMuddyPig;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PigSkinLayer extends LayerRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> {
    private static final ResourceLocation MUDDYSKIN_TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/muddypig/muddy_skin.png");
    private static final ResourceLocation DRY_MUDSKIN = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/muddypig/pig_dry_mud.png");

    public PigSkinLayer(IEntityRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> render) {
        super(render);
    }

    public void render(MuddyPigEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if(entityIn.isDry()) {
            this.bindTexture(DRY_MUDSKIN);
            this.getEntityModel().setModelAttributes(this.getEntityModel());
            ((ModelMuddyPig) this.getEntityModel()).render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        } else {
            this.bindTexture(MUDDYSKIN_TEXTURES);
            this.getEntityModel().setModelAttributes(this.getEntityModel());
            ((ModelMuddyPig) this.getEntityModel()).render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}