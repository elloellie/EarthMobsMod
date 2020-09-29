package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.HornedSheepModel;
import baguchan.earthmobsmod.client.render.layer.HornedSheepWoolLayer;
import baguchan.earthmobsmod.entity.HornedSheepEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class HornedSheepRender extends MobRenderer<HornedSheepEntity, HornedSheepModel<HornedSheepEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/horned_sheep.png");

    public HornedSheepRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new HornedSheepModel<>(), 0.5F);
        this.addLayer(new HornedSheepWoolLayer(this));
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(HornedSheepEntity entity) {
        return TEXTURES;
    }
}