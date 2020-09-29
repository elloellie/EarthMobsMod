package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.ModelCluckShroom;
import baguchan.earthmobsmod.entity.CluckShroomEntity;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class CluckShroomRender extends MobRenderer<CluckShroomEntity, ModelCluckShroom<CluckShroomEntity>> {
    private static final Map<CluckShroomEntity.Type, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (p_217773_0_) -> {
        p_217773_0_.put(CluckShroomEntity.Type.BROWN, new ResourceLocation(EarthMobsMod.MODID, "textures/entity/cluckshroom/brown_cluckshroom.png"));
        p_217773_0_.put(CluckShroomEntity.Type.RED, new ResourceLocation(EarthMobsMod.MODID, "textures/entity/cluckshroom/cluckshroom.png"));
    });
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
    public ResourceLocation getEntityTexture(CluckShroomEntity entity) {
        return TEXTURES.get(entity.getCluckShroomType());
    }
}