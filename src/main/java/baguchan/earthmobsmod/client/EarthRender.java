package baguchan.earthmobsmod.client;


import baguchan.earthmobsmod.client.render.CluckShroomRender;
import baguchan.earthmobsmod.client.render.MuddyPigRender;
import baguchan.earthmobsmod.entity.CluckShroomEntity;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class EarthRender {
    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(MuddyPigEntity.class, MuddyPigRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CluckShroomEntity.class, CluckShroomRender::new);
    }
}
