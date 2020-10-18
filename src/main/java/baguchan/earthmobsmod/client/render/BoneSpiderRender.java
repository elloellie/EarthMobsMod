package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.BoneSpiderModel;
import baguchan.earthmobsmod.client.render.layer.BoneSpiderEyesLayer;
import baguchan.earthmobsmod.entity.BoneSpiderEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class BoneSpiderRender extends MobRenderer<BoneSpiderEntity, BoneSpiderModel<BoneSpiderEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/bone_spider/bone_spider.png");

    public BoneSpiderRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BoneSpiderModel<>(), 0.5F);
        this.addLayer(new BoneSpiderEyesLayer<>(this));
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(BoneSpiderEntity entity) {
        return TEXTURES;
    }
}