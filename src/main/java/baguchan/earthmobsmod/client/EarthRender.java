package baguchan.earthmobsmod.client;


import baguchan.earthmobsmod.client.render.*;
import baguchan.earthmobsmod.init.EarthEntitys;
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
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.HORNED_SHEEP, HornedSheepRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.RAINBOW_SHEEP, RainbowSheepRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.BONE_SPIDER, BoneSpiderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.BOULDERING_ZOMBIE, BoulderingZombieRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.LOBBER_ZOMBIE, LobberZombieRender::new);


        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.SMELLYEGG, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.FLESH, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
    }

}
