package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.entity.CluckShroomEntity;
import baguchan.earthmobsmod.entity.MooBloomEntity;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import baguchan.earthmobsmod.entity.projectile.SmellyEggEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Set;

import static baguchan.earthmobsmod.EarthMobsMod.MODID;
import static net.minecraftforge.common.BiomeDictionary.Type.NETHER;


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
    }

    private static String prefix(String path) {

        return MODID + "." + path;

    }

    public static void spawnEntity() {

        for (Biome biome : Biome.BIOMES) {

            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);

            if (biome == Biomes.FLOWER_FOREST) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EarthEntitys.MOOBLOOM, 8, 3, 4));
            }

            if (biome == Biomes.MUSHROOM_FIELDS || biome == Biomes.MUSHROOM_FIELD_SHORE) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EarthEntitys.CLUCKSHROOM, 3, 2, 3));
            }

            if (types.contains(BiomeDictionary.Type.SWAMP) && !types.contains(NETHER) && !biome.getSpawns(EntityClassification.CREATURE).isEmpty()) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EarthEntitys.MUDDYPIG, 10, 3, 4));
            }
        }

    }
}