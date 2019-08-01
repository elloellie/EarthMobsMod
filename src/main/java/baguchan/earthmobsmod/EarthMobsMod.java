package baguchan.earthmobsmod;

import baguchan.earthmobsmod.client.EarthRender;
import baguchan.earthmobsmod.entity.MooBloomEntity;
import baguchan.earthmobsmod.handler.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("earthmobsmod")
public class EarthMobsMod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "earthmobsmod";

    public static  EarthMobsMod instance;

    public EarthMobsMod() {
        instance = this;
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::onBlockRegistry);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::onItemRegistry);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, this::onEntityRegistry);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Fluid.class, this::onFluidRegistry);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class, this::onFeatureRegistry);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        EarthEntitys.spawnEntity();
        Biomes.FLOWER_FOREST.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(Biome.createDecoratedFeature(EarthFeatures.GOLDENFLOWER_CIRCLE, new NoFeatureConfig(), Placement.CHANCE_HEIGHTMAP, new ChanceConfig(5)));

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        EarthRender.entityRender();
    }



    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        World world = event.getWorld();
        if (event.getTarget() instanceof CowEntity && !(event.getTarget() instanceof MooBloomEntity)) {
            if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {


                MooBloomEntity cowBloomEntity = EarthEntitys.MOOBLOOM.create(world);
                cowBloomEntity.setLocationAndAngles(event.getTarget().posX, event.getTarget().posY, event.getTarget().posZ, event.getTarget().rotationYaw, event.getTarget().rotationPitch);
                cowBloomEntity.setNoAI(((AnimalEntity) event.getTarget()).isAIDisabled());
                if (event.getTarget().hasCustomName()) {
                    cowBloomEntity.setCustomName(event.getTarget().getCustomName());
                    cowBloomEntity.setCustomNameVisible(event.getTarget().isCustomNameVisible());
                }

                if (((AnimalEntity) event.getTarget()).isChild()) {
                    cowBloomEntity.setGrowingAge(((AnimalEntity) event.getTarget()).getGrowingAge());
                }

                if (!event.getEntityPlayer().isCreative()) {
                    stack.shrink(1);
                }

                event.getWorld().addEntity(cowBloomEntity);

                event.getWorld().playSound(null, cowBloomEntity.getPosition(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 1.5F, 1.0F);
                event.getTarget().remove();
            }
        }
    }

    public void onBlockRegistry(final RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        EarthBlocks.registerBlocks(registry);
    }

    public void onItemRegistry(final RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        EarthBlocks.registerItemBlocks(registry);
        EarthItems.registerItems(registry);
    }

    public void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> registry = event.getRegistry();

        EarthEntitys.registerEntity(registry);
    }

    public void onFeatureRegistry(final RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        EarthFeatures.register(registry);
    }

    public void onFluidRegistry(final RegistryEvent.Register<Fluid> event) {
        IForgeRegistry<Fluid> registry = event.getRegistry();

        EarthFluids.register(registry);
    }
}
