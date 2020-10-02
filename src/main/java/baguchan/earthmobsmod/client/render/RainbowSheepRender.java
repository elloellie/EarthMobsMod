package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.RainbowSheepModel;
import baguchan.earthmobsmod.client.render.layer.RainbowSheepWoolLayer;
import baguchan.earthmobsmod.entity.RainbowSheepEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RainbowSheepRender extends MobRenderer<RainbowSheepEntity, RainbowSheepModel<RainbowSheepEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/rainbow_sheep/rainbow_sheep.png");

    public RainbowSheepRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new RainbowSheepModel<>(), 0.5F);
        this.addLayer(new RainbowSheepWoolLayer(this));
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(RainbowSheepEntity entity) {
        return TEXTURES;
    }
}