package baguchan.earthmobsmod.client;

import baguchan.earthmobsmod.client.render.*;
import baguchan.earthmobsmod.init.EarthBlocks;
import baguchan.earthmobsmod.init.EarthEntitys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientRegistrar {
    private static final Minecraft MC = Minecraft.getInstance();

    public static void renderBlock() {
        RenderTypeLookup.setRenderLayer(EarthBlocks.BUTTERCUP, RenderType.getCutout());
    }

    private static void renderItem() {
    }

    public static void setup(final FMLCommonSetupEvent event) {
        ClientRegistrar.entityRender();
        ClientRegistrar.renderBlock();
        ClientRegistrar.renderItem();
    }

    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.MUDDYPIG, MuddyPigRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.CLUCKSHROOM, CluckShroomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.MOOBLOOM, MooBloomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.HORNED_SHEEP, HornedSheepRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.RAINBOW_SHEEP, RainbowSheepRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.JOLLY_LLAMA, JollyLlamaRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.WOOLY_COW, WoolyCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.BONE_SPIDER, BoneSpiderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.BOULDERING_ZOMBIE, BoulderingZombieRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.LOBBER_ZOMBIE, LobberZombieRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.VILER_WITCH, VilerWitchRender::new);


        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.SMELLYEGG, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EarthEntitys.FLESH, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
    }
}