package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.world.gen.GoldenFlowerCircleFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.IForgeRegistry;

public class EarthFeatures {
    public static final Feature<NoFeatureConfig> GOLDENFLOWER_CIRCLE = new GoldenFlowerCircleFeature(NoFeatureConfig::deserialize);


    public static void register(IForgeRegistry<Feature<?>> registry) {
        registry.register(GOLDENFLOWER_CIRCLE.setRegistryName("goldenflower_circle"));
    }

}