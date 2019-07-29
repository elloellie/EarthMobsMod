package baguchan.earthmobsmod.fluid;

import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthFluids;
import baguchan.earthmobsmod.handler.EarthItems;
import baguchan.earthmobsmod.handler.EarthTags;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class FluidMudWater extends WaterFluid {
    @Override
    public net.minecraft.fluid.Fluid getFlowingFluid() {
        return EarthFluids.MUD_WATER_FLOW;
    }

    @Override
    public net.minecraft.fluid.Fluid getStillFluid() {
        return EarthFluids.MUD_WATER;
    }

    public Item getFilledBucket() {
        return EarthItems.MUD_BUCKET;
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(World world, BlockPos pos, IFluidState state, Random random) {
    }

    public int getTickRate(IWorldReader p_205569_1_) {
        return 15;
    }

    @Override
    public BlockState getBlockState(IFluidState state) {
        return EarthBlocks.MUDWATER.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    public boolean isEquivalentTo(Fluid fluidIn) {
        return fluidIn.isIn(EarthTags.Fluids.MUD_WATER);
    }

    @Override
    protected void flowInto(IWorld world, BlockPos intoPos, BlockState intoBlock, Direction direction, IFluidState state) {
        if (direction == Direction.DOWN) {
            IFluidState intoFluid = world.getFluidState(intoPos);
            if (intoFluid.isTagged(FluidTags.LAVA)) {
                this.mixInto(world, intoPos, Blocks.STONE.getDefaultState());
                return;
            }
        }

        super.flowInto(world, intoPos, intoBlock, direction, state);
    }

    protected boolean canSourcesMultiply() {
        return false;
    }

    private void mixInto(IWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.NOTIFY_LISTENERS);
        world.playEvent(1501, pos, 0);
    }

    public static class Flowing extends FluidMudWater {
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        public int getLevel(IFluidState p_207192_1_) {
            return p_207192_1_.get(LEVEL_1_8);
        }

        public boolean isSource(IFluidState state) {
            return false;
        }
    }

    public static class Source extends FluidMudWater {
        public int getLevel(IFluidState p_207192_1_) {
            return 5;
        }

        public boolean isSource(IFluidState state) {
            return true;
        }
    }
}