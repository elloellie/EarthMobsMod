package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.block.EarthFluidBlock;
import baguchan.earthmobsmod.block.ButtercupBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.IForgeRegistry;

public class EarthBlocks {

    public static final Block MUDWATER = new EarthFluidBlock(EarthFluids.MUD_WATER, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    public static final FlowerBlock BUTTERCUP = new ButtercupBlock(Effects.REGENERATION, 160, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    public static final FlowerPotBlock POTTED_BUTTERCUP = new FlowerPotBlock(BUTTERCUP, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));


    public static void registerBlocks(IForgeRegistry<Block> registry) {
        //Terrain
        registry.register(MUDWATER.setRegistryName("mud"));
        registry.register(BUTTERCUP.setRegistryName("buttercup"));
        registry.register(POTTED_BUTTERCUP.setRegistryName("potted_buttercup"));
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        EarthItems.register(registry, new BlockItem(BUTTERCUP, (new Item.Properties()).group(ItemGroup.DECORATIONS)));
    }

}