package baguchan.earthmobsmod.client.render;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.model.LobberZombieModel;
import baguchan.earthmobsmod.entity.LobberZombieEntity;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LobberZombieRender extends AbstractZombieRenderer<LobberZombieEntity, LobberZombieModel<LobberZombieEntity>> {
    private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation(EarthMobsMod.MODID, "textures/entity/lobber_zombie/lobber_zombie.png");

    public LobberZombieRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new LobberZombieModel<>(0.0F, 0.0F, 64, 64), new LobberZombieModel<>(0.5F, true), new LobberZombieModel<>(1.0F, true));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(LobberZombieEntity entity) {
        return ZOMBIE_LOCATION;
    }
}
