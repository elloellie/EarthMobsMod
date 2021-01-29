package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.BoulderingZombieModel;
import baguchan.earthmobsmod.entity.BoulderingZombieEntity;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoulderingZombieRender extends AbstractZombieRenderer<BoulderingZombieEntity, ZombieModel<BoulderingZombieEntity>> {
    private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/bouldering_zombie/bouldering_zombie.png");

    public BoulderingZombieRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BoulderingZombieModel<>(0.0F, 0.0F, 64, 64), new ZombieModel<>(0.5F, true), new ZombieModel<>(1.0F, true));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(BoulderingZombieEntity entity) {
        return ZOMBIE_LOCATION;
    }
}
