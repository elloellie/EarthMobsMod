package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.world.gen.GoldenFlowerCircleFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
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


    public static void register(IForgeRegistry<Feature<?>> registry) {
        registry.register(GOLDENFLOWER_CIRCLE.setRegistryName("goldenflower_circle"));
    }

    public static void addFeature() {

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS || biome.getRegistryName().equals("biomesoplenty:bog") || biome.getRegistryName().equals("biomesoplenty:lush_swamp") || biome.getRegistryName().equals("biomesoplenty:marsh")) {
                biome.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(Biome.createDecoratedFeature(Feature.LAKE, new LakesConfig(EarthBlocks.MUDWATER.getDefaultState()), Placement.WATER_LAKE, new LakeChanceConfig(4)));
            }
        }

    }

}