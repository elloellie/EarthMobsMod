package baguchan.earthmobsmod.client;

import baguchan.earthmobsmod.handler.EarthBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientRegistrar {

    public static void renderBlock() {
        RenderTypeLookup.setRenderLayer(EarthBlocks.BUTTERCUP, RenderType.getCutout());
    }

    private static void renderItem() {
    }

    public static void setup(final FMLCommonSetupEvent event) {
        EarthRender.entityRender();
        ClientRegistrar.renderBlock();
        ClientRegistrar.renderItem();
    }
}