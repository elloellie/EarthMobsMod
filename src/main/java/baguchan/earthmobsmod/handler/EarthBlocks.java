package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.block.EarthFluidBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistry;

public class EarthBlocks {


    public static final NonNullList<Block> FLUIDBLOCKS = NonNullList.create();

    public static final Block MUDWATER = new EarthFluidBlock(EarthFluids.MUD_WATER, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).lootFrom(Blocks.AIR));

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        //Terrain
        registry.register(MUDWATER.setRegistryName("mud"));
        FLUIDBLOCKS.add(MUDWATER);
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
    }

}