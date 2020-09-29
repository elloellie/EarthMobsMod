package baguchan.earthmobsmod.client;


import baguchan.earthmobsmod.client.render.CluckShroomRender;
import baguchan.earthmobsmod.client.render.MooBloomRender;
import baguchan.earthmobsmod.client.render.MuddyPigRender;
import baguchan.earthmobsmod.handler.EarthEntitys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class EarthRender {

    private static final Minecraft MC = Minecraft.getInstance();

    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.MUDDYPIG, MuddyPigRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.CLUCKSHROOM, CluckShroomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.MOOBLOOM, MooBloomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.SMELLYEGG, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
    }

}
