package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.entity.CluckShroomEntity;
import baguchan.earthmobsmod.entity.MooBloomEntity;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import baguchan.earthmobsmod.entity.projectile.SmellyEggEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Set;

import static baguchan.earthmobsmod.EarthMobsMod.MODID;


public class EarthEntitys {

    public static final EntityType<MuddyPigEntity> MUDDYPIG = EntityType.Builder.create(MuddyPigEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.8F, 0.8F).build(prefix("muddypig"));
    public static final EntityType<CluckShroomEntity> CLUCKSHROOM = EntityType.Builder.create(CluckShroomEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.4F, 0.7F).build(prefix("cluckshroom"));
    public static final EntityType<MooBloomEntity> MOOBLOOM = EntityType.Builder.create(MooBloomEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.4F).build(prefix("moobloom"));
    public static final EntityType<SmellyEggEntity> SMELLYEGG = EntityType.Builder.<SmellyEggEntity>create(SmellyEggEntity::new, EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).setCustomClientFactory(SmellyEggEntity::new).size(0.25F, 0.25F).build(prefix("smellyegg"));

    public static void registerEntity(IForgeRegistry<EntityType<?>> event) {
        event.register(MUDDYPIG.setRegistryName("muddypig"));
        event.register(CLUCKSHROOM.setRegistryName("cluckshroom"));
        event.register(MOOBLOOM.setRegistryName("moobloom"));
        event.register(SMELLYEGG.setRegistryName("smellyegg"));
        EntitySpawnPlacementRegistry.register(CLUCKSHROOM, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CluckShroomEntity::spawnHandler);
    }

    private static String prefix(String path) {

        return MODID + "." + path;

    }

    public static void spawnEntity() {

        Biomes.MUSHROOM_FIELDS.getSpawns(EntityClassification.AMBIENT).add(new Biome.SpawnListEntry(EarthEntitys.CLUCKSHROOM, 8, 2, 3));
        Biomes.MUSHROOM_FIELD_SHORE.getSpawns(EntityClassification.AMBIENT).add(new Biome.SpawnListEntry(EarthEntitys.CLUCKSHROOM, 8, 2, 3));

        for (Biome biome : ForgeRegistries.BIOMES) {

            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);


            if (biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS || biome.getRegistryName().equals("biomesoplenty:mire") || biome.getRegistryName().equals("biomesoplenty:lush_swamp") || biome.getRegistryName().equals("biomesoplenty:marsh")) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EarthEntitys.MUDDYPIG, 10, 3, 4));
            }
        }

    }
}