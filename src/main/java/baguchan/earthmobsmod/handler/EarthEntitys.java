package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.entity.*;
import baguchan.earthmobsmod.entity.projectile.SmellyEggEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.IForgeRegistry;

import static baguchan.earthmobsmod.EarthMobsMod.MODID;


public class EarthEntitys {

    public static final EntityType<MuddyPigEntity> MUDDYPIG = EntityType.Builder.create(MuddyPigEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.8F, 0.8F).build(prefix("muddypig"));
    public static final EntityType<CluckShroomEntity> CLUCKSHROOM = EntityType.Builder.create(CluckShroomEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.4F, 0.7F).build(prefix("cluckshroom"));
    public static final EntityType<MooBloomEntity> MOOBLOOM = EntityType.Builder.create(MooBloomEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.4F).build(prefix("moobloom"));
    public static final EntityType<HornedSheepEntity> HORNED_SHEEP = EntityType.Builder.create(HornedSheepEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.4F).build(prefix("horned_sheep"));
    public static final EntityType<RainbowSheepEntity> RAINBOW_SHEEP = EntityType.Builder.create(RainbowSheepEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.4F).build(prefix("rainbow_sheep"));

    public static final EntityType<SmellyEggEntity> SMELLYEGG = EntityType.Builder.<SmellyEggEntity>create(SmellyEggEntity::new, EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).setCustomClientFactory(SmellyEggEntity::new).size(0.25F, 0.25F).build(prefix("smellyegg"));

    public static void registerEntity(IForgeRegistry<EntityType<?>> event) {
        event.register(MUDDYPIG.setRegistryName("muddypig"));
        event.register(CLUCKSHROOM.setRegistryName("cluckshroom"));
        event.register(MOOBLOOM.setRegistryName("moobloom"));
        event.register(SMELLYEGG.setRegistryName("smellyegg"));
        event.register(HORNED_SHEEP.setRegistryName("horned_sheep"));
        event.register(RAINBOW_SHEEP.setRegistryName("rainbow_sheep"));

        GlobalEntityTypeAttributes.put(MUDDYPIG, MuddyPigEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(CLUCKSHROOM, CluckShroomEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(MOOBLOOM, MooBloomEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(HORNED_SHEEP, HornedSheepEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(RAINBOW_SHEEP, RainbowSheepEntity.createMutableAttribute().create());

        EntitySpawnPlacementRegistry.register(MOOBLOOM, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MooBloomEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(CLUCKSHROOM, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CluckShroomEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(MUDDYPIG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MuddyPigEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(HORNED_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(RAINBOW_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
    }

    private static String prefix(String path) {
        return MODID + "." + path;
    }
}