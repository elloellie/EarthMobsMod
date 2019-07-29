package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.entity.CluckShroomEntity;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Set;

import static net.minecraftforge.common.BiomeDictionary.Type.NETHER;

public class EarthEntitys {

    public static final EntityType<MuddyPigEntity> MUDDYPIG = createEntity(MuddyPigEntity::new, EntityClassification.CREATURE, "muddypig", 0.8F, 0.8F);
    public static final EntityType<CluckShroomEntity> CLUCKSHROOM = createEntity(CluckShroomEntity::new, EntityClassification.CREATURE, "cluckshroom", 0.4F, 0.7F);


    private static <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height) {
        ResourceLocation location = new ResourceLocation(EarthMobsMod.MODID + ":" + name);

        EntityType<T> entity = EntityType.Builder.create(factory, entityClassification).size(width, height).build(location.toString());
        entity.setRegistryName(location);

        return entity;
    }

    @SubscribeEvent
    public static void registerEntity(IForgeRegistry<EntityType<?>> event) {
        event.register(MUDDYPIG);
        event.register(CLUCKSHROOM);
    }


    private static String prefix(String path) {

        return EarthMobsMod.MODID + "." + path;

    }

    public static void spawnEntity() {

        for (Biome biome : Biome.BIOMES) {

            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);

            if (biome == Biomes.MUSHROOM_FIELDS || biome == Biomes.MUSHROOM_FIELD_SHORE) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EarthEntitys.CLUCKSHROOM, 3, 2, 3));
            }

            if (types.contains(BiomeDictionary.Type.SWAMP) && !types.contains(NETHER) && !biome.getSpawns(EntityClassification.CREATURE).isEmpty()) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EarthEntitys.MUDDYPIG, 10, 3, 4));
            }
        }

    }
}