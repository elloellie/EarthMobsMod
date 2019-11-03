package baguchan.earthmobsmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EarthFluidBlock extends FlowingFluidBlock {
    public EarthFluidBlock(FlowingFluid fluid, Block.Properties properties) {
        super(fluid, properties);
    }

    @Override

    public boolean reactWithNeighbors(World world, BlockPos pos, BlockState state) {
        FlowingFluid fluid = this.getFluid();
        return true;
    }

}