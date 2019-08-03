package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.client.model.ModelMuddyPig;
import baguchan.earthmobsmod.client.render.layer.FlowerColorLayer;
import baguchan.earthmobsmod.client.render.layer.MuddyPigSaddleLayer;
import baguchan.earthmobsmod.client.render.layer.PigSkinLayer;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MuddyPigRender extends MobRenderer<MuddyPigEntity, ModelMuddyPig<MuddyPigEntity>> {
    private static final ResourceLocation PIG_TEXTURES = new ResourceLocation("textures/entity/pig/pig.png");

    public MuddyPigRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelMuddyPig<>(), 0.5F);
        this.addLayer(new MuddyPigSaddleLayer(this));
        this.addLayer(new PigSkinLayer(this));
        this.addLayer(new FlowerColorLayer(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(MuddyPigEntity entity)
    {
        return PIG_TEXTURES;
    }
}