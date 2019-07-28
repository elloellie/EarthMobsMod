package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.entity.MuddyPigEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Set;

public class EarthEntity {

    public static final EntityType<MuddyPigEntity> MUDDYPIG = createEntity(MuddyPigEntity::new, EntityClassification.CREATURE, "muddypig", 0.8F, 0.8F);

    private static <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height) {
        ResourceLocation location = new ResourceLocation(EarthMobsMod.MODID + ":" + name);

        EntityType<T> entity = EntityType.Builder.create(factory, entityClassification).size(width, height).build(location.toString());
        entity.setRegistryName(location);

        return entity;
    }

    @SubscribeEvent
    public static void registerEntity(IForgeRegistry<EntityType<?>> event) {
        event.register(MUDDYPIG);
    }


    private static String prefix(String path) {

        return EarthMobsMod.MODID + "." + path;

    }

    public static void spawnEntity() {

        for (Biome biome : Biome.BIOMES) {

            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);

        }

    }
}