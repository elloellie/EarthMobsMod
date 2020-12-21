package baguchan.earthmobsmod.init;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.entity.projectile.SmellyEggEntity;
import baguchan.earthmobsmod.item.SmellyEggItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

public class EarthItems {

    public static final Item MUD_BUCKET = new BucketItem(EarthFluids.MUD_WATER, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC));
    public static final Item SMELLY_EGG = new SmellyEggItem((new Item.Properties()).maxStackSize(16).group(ItemGroup.MISC));
    public static final Item HORN = new Item((new Item.Properties()).group(ItemGroup.MISC));

    public static final Item CLUCKSHROOM_SPAWNEGG = new SpawnEggItem(EarthEntitys.CLUCKSHROOM, 10489616, 12040119, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item MOOBLOOM_SPAWNEGG = new SpawnEggItem(EarthEntitys.MOOBLOOM, 0xedcd19, 0xfef8ba, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item HORNED_SHEEP_SPAWNEGG = new SpawnEggItem(EarthEntitys.HORNED_SHEEP, 15198183, 0x291511, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item RAINBOW_SHEEP_SPAWNEGG = new SpawnEggItem(EarthEntitys.RAINBOW_SHEEP, 0xA12F22, 0x354D9D, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item BONE_SPIDER_SPAWNEGG = new SpawnEggItem(EarthEntitys.BONE_SPIDER, 0x2F121E, 0x6130B7, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item BOULDERING_ZOMBIE_SPAWNEGG = new SpawnEggItem(EarthEntitys.BOULDERING_ZOMBIE, 0x4D575A, 0x5A2A1D, (new Item.Properties()).group(ItemGroup.MISC));


    public static void register(IForgeRegistry<Item> registry, Item item, String id) {
        item.setRegistryName(new ResourceLocation(EarthMobsMod.MODID, id));

        registry.register(item);
    }

    public static void register(IForgeRegistry<Item> registry, Item item) {

        if (item instanceof BlockItem && item.getRegistryName() == null) {
            item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());

            Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
        }

        registry.register(item);
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        register(registry, MUD_BUCKET, "mud_bucket");
        register(registry, SMELLY_EGG, "smelly_egg");
        register(registry, HORN, "horn");
        register(registry, CLUCKSHROOM_SPAWNEGG, "cluckshroom_spawnegg");
        register(registry, MOOBLOOM_SPAWNEGG, "moobloom_spawnegg");
        register(registry, HORNED_SHEEP_SPAWNEGG, "horned_sheep_spawnegg");
        register(registry, RAINBOW_SHEEP_SPAWNEGG, "rainbow_sheep_spawnegg");
        register(registry, BONE_SPIDER_SPAWNEGG, "bone_spider_spawnegg");
        register(registry, BOULDERING_ZOMBIE_SPAWNEGG, "bouldering_spawnegg");
        DispenserBlock.registerDispenseBehavior(EarthItems.SMELLY_EGG, new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return Util.make(new SmellyEggEntity(worldIn, position.getX(), position.getY(), position.getZ()), (p_218408_1_) -> {
                    p_218408_1_.setItem(stackIn);
                });
            }
        });
    }
}