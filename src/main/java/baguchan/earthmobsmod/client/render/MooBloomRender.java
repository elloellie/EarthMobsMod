package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.ModelMoobloom;
import baguchan.earthmobsmod.client.render.layer.EyelidsLayer;
import baguchan.earthmobsmod.entity.MooBloomEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MooBloomRender extends MobRenderer<MooBloomEntity, ModelMoobloom<MooBloomEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/moobloom/moobloom.png");

    public MooBloomRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelMoobloom<>(), 0.5F);
        this.addLayer(new EyelidsLayer(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(MooBloomEntity entity) {
        return TEXTURES;
    }
}