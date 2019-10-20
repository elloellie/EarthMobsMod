package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.world.gen.GoldenFlowerCircleFeature;
import baguchan.earthmobsmod.world.gen.ModdedLakeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class EarthFeatures {
    public static final Feature<NoFeatureConfig> GOLDENFLOWER_CIRCLE = new GoldenFlowerCircleFeature(NoFeatureConfig::deserialize);
    public static final Feature<LakesConfig> LAKE = new ModdedLakeFeature(LakesConfig::deserialize);


    public static void register(IForgeRegistry<Feature<?>> registry) {
        registry.register(GOLDENFLOWER_CIRCLE.setRegistryName("goldenflower_circle"));
        registry.register(LAKE.setRegistryName("lake"));
    }

    public static void addFeature() {

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.SWAMP) {
                biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(Biome.createDecoratedFeature(LAKE, new LakesConfig(EarthBlocks.MUDWATER.getDefaultState()), Placement.WATER_LAKE, new LakeChanceConfig(4)));
            }
        }

    }

}