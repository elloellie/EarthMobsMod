package baguchan.earthmobsmod.client;


import baguchan.earthmobsmod.client.render.CluckShroomRender;
import baguchan.earthmobsmod.client.render.MooBloomRender;
import baguchan.earthmobsmod.client.render.MuddyPigRender;
import baguchan.earthmobsmod.entity.CluckShroomEntity;
import baguchan.earthmobsmod.entity.MooBloomEntity;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import baguchan.earthmobsmod.entity.projectile.SmellyEggEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class EarthRender {

    private static final Minecraft MC = Minecraft.getInstance();

    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(MuddyPigEntity.class, MuddyPigRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CluckShroomEntity.class, CluckShroomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(MooBloomEntity.class, MooBloomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SmellyEggEntity.class, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
    }
}