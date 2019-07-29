package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.ModelCluckShroom;
import baguchan.earthmobsmod.entity.CluckShroomEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class CluckShroomRender extends MobRenderer<CluckShroomEntity, ModelCluckShroom<CluckShroomEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/cluckshroom.png");

    public CluckShroomRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelCluckShroom<>(), 0.3F);

    }

    protected float handleRotationFloat(CluckShroomEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(CluckShroomEntity entity) {
        return TEXTURES;
    }
}