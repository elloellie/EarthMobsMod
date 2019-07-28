package baguchan.client.render.layer;

import baguchan.client.model.ModelMuddyPig;
import baguchan.client.render.MuddyPigRender;
import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DrySkinLayer extends LayerRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> {
    private static final ResourceLocation DRY_MADSKIN = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/pig_dry_mud.png");

    public DrySkinLayer(IEntityRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> p_i50921_1_) {
        super(p_i50921_1_);
    }

    public void render(MuddyPigEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if(entityIn.isDry()) {
            this.bindTexture(DRY_MADSKIN);

            ((ModelMuddyPig) this.getEntityModel()).render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}