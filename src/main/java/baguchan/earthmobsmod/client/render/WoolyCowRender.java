package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.WoolyCowModel;
import baguchan.earthmobsmod.entity.WoolyCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class WoolyCowRender extends MobRenderer<WoolyCowEntity, WoolyCowModel<WoolyCowEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/wooly_cow.png");

    public WoolyCowRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new WoolyCowModel<>(), 0.5F);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(WoolyCowEntity entity) {
        return TEXTURES;
    }
}