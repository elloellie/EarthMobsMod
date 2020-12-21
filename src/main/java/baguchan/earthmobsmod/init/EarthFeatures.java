package baguchan.earthmobsmod.init;

import baguchan.earthmobsmod.world.gen.ButtercupCircleFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.IForgeRegistry;

public class EarthFeatures {
    public static final Feature<NoFeatureConfig> BUTTERCUP_CIRCLE = new ButtercupCircleFeature(NoFeatureConfig.field_236558_a_);

    public static void register(IForgeRegistry<Feature<?>> registry) {
        registry.register(BUTTERCUP_CIRCLE.setRegistryName("buttercup_circle"));
    }

    public static void addFeature() {
    }

}