package baguchan.earthmobsmod.event;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthEntitys;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EarthMobsMod.MODID)
public class BiomeLoadHandler {

    public static final ConfiguredFeature<?, ?> LAKE_MUD = register(EarthMobsMod.MODID + ":mud_lake", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(EarthBlocks.MUDWATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void loadingBiome(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if (event.getName().toString().contains("minecraft:flower_forest")) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EarthEntitys.MOOBLOOM, 10, 2, 3));
        }

        if (event.getName().toString().contains("minecraft:mushroom_fields")) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EarthEntitys.CLUCKSHROOM, 8, 2, 3));
        }

        if (event.getName().getNamespace().toString().contains("minecraft") || event.getName().getNamespace().toString().contains("biomesoplenty")) {
            if (event.getCategory() == Biome.Category.SWAMP) {
                event.getGeneration().withFeature(GenerationStage.Decoration.LAKES, LAKE_MUD);
            }
        }
    }
}
