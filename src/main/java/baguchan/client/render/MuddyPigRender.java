package baguchan.client.render;

import baguchan.client.model.ModelMuddyPig;
import baguchan.client.render.layer.DrySkinLayer;
import baguchan.client.render.layer.FlowerColorLayer;
import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MuddyPigRender extends MobRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> {
    private static final ResourceLocation MUDDYPIG_TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/pig_muddy.png");
    private static final ResourceLocation PIG_TEXTURES = new ResourceLocation("textures/entity/pig/pig.png");
    private float field_205127_a;

    public MuddyPigRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelMuddyPig(), 0.5F);
        this.addLayer(new DrySkinLayer(this));
        this.addLayer(new FlowerColorLayer(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(MuddyPigEntity entity)
    {
        if(entity.isDry()){
            return PIG_TEXTURES;
        }else {
            return MUDDYPIG_TEXTURES;
        }
    }
}