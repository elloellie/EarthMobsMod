package baguchan.earthmobsmod.init;

import baguchan.earthmobsmod.entity.*;
import baguchan.earthmobsmod.entity.projectile.FleshEntity;
import baguchan.earthmobsmod.entity.projectile.SmellyEggEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.registries.IForgeRegistry;

import static baguchan.earthmobsmod.EarthMobsMod.MODID;


public class EarthEntitys {

    public static final EntityType<MuddyPigEntity> MUDDYPIG = EntityType.Builder.create(MuddyPigEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.8F, 0.8F).build(prefix("muddypig"));
    public static final EntityType<CluckShroomEntity> CLUCKSHROOM = EntityType.Builder.create(CluckShroomEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.4F, 0.7F).build(prefix("cluckshroom"));
    public static final EntityType<MooBloomEntity> MOOBLOOM = EntityType.Builder.create(MooBloomEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.4F).build(prefix("moobloom"));
    public static final EntityType<HornedSheepEntity> HORNED_SHEEP = EntityType.Builder.create(HornedSheepEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.4F).build(prefix("horned_sheep"));
    public static final EntityType<RainbowSheepEntity> RAINBOW_SHEEP = EntityType.Builder.create(RainbowSheepEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.4F).build(prefix("rainbow_sheep"));
    public static final EntityType<JollyLlamaEntity> JOLLY_LLAMA = EntityType.Builder.create(JollyLlamaEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.87F).build(prefix("jolly_llama"));
    public static final EntityType<BoneSpiderEntity> BONE_SPIDER = EntityType.Builder.create(BoneSpiderEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(1.4F, 0.9F).build(prefix("bone_spider"));
    public static final EntityType<BoulderingZombieEntity> BOULDERING_ZOMBIE = EntityType.Builder.<BoulderingZombieEntity>create(BoulderingZombieEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("bouldering_zombie"));
    public static final EntityType<LobberZombieEntity> LOBBER_ZOMBIE = EntityType.Builder.<LobberZombieEntity>create(LobberZombieEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("lobber_zombie"));

    public static final EntityType<VilerWitchEntity> VILER_WITCH = EntityType.Builder.create(VilerWitchEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("viler_witch"));


    public static final EntityType<SmellyEggEntity> SMELLYEGG = EntityType.Builder.<SmellyEggEntity>create(SmellyEggEntity::new, EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).setCustomClientFactory(SmellyEggEntity::new).size(0.25F, 0.25F).build(prefix("smellyegg"));
    public static final EntityType<FleshEntity> FLESH = EntityType.Builder.<FleshEntity>create(FleshEntity::new, EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).setCustomClientFactory(FleshEntity::new).size(0.3F, 0.3F).build(prefix("flesh"));


    public static void registerEntity(IForgeRegistry<EntityType<?>> event) {
        event.register(MUDDYPIG.setRegistryName("muddypig"));
        event.register(CLUCKSHROOM.setRegistryName("cluckshroom"));
        event.register(MOOBLOOM.setRegistryName("moobloom"));
        event.register(SMELLYEGG.setRegistryName("smellyegg"));
        event.register(HORNED_SHEEP.setRegistryName("horned_sheep"));
        event.register(RAINBOW_SHEEP.setRegistryName("rainbow_sheep"));
        event.register(JOLLY_LLAMA.setRegistryName("jolly_llama"));
        event.register(BONE_SPIDER.setRegistryName("bone_spider"));
        event.register(BOULDERING_ZOMBIE.setRegistryName("bouldering_zombie"));
        event.register(LOBBER_ZOMBIE.setRegistryName("lobber_zombie"));
        event.register(VILER_WITCH.setRegistryName("viler_witch"));
        event.register(FLESH.setRegistryName("flesh"));

        GlobalEntityTypeAttributes.put(MUDDYPIG, MuddyPigEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(CLUCKSHROOM, CluckShroomEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(MOOBLOOM, MooBloomEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(HORNED_SHEEP, HornedSheepEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(RAINBOW_SHEEP, RainbowSheepEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(JOLLY_LLAMA, JollyLlamaEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(BONE_SPIDER, BoneSpiderEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(BOULDERING_ZOMBIE, BoulderingZombieEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(LOBBER_ZOMBIE, LobberZombieEntity.createMutableAttribute().create());
        GlobalEntityTypeAttributes.put(VILER_WITCH, VilerWitchEntity.createMutableAttribute().create());


        EntitySpawnPlacementRegistry.register(MOOBLOOM, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MooBloomEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(CLUCKSHROOM, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CluckShroomEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(MUDDYPIG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MuddyPigEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(HORNED_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(RAINBOW_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(JOLLY_LLAMA, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(BONE_SPIDER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(BOULDERING_ZOMBIE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(LOBBER_ZOMBIE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(VILER_WITCH, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);

        Raid.WaveMember.create("viler_witch", VILER_WITCH, new int[]{0, 0, 0, 0, 0, 1, 1, 1});
    }

    private static String prefix(String path) {
        return MODID + "." + path;
    }
}