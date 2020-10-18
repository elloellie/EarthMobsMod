package baguchan.earthmobsmod.client.render.layer;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.BoneSpiderModel;
import baguchan.earthmobsmod.entity.BoneSpiderEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoneSpiderEyesLayer<T extends BoneSpiderEntity, M extends BoneSpiderModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(EarthMobsMod.MODID, "textures/entity/bone_spider/bone_spider_eye.png"));

    public BoneSpiderEyesLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}