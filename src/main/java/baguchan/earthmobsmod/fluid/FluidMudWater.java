package baguchan.earthmobsmod.fluid;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthFluids;
import baguchan.earthmobsmod.handler.EarthItems;
import baguchan.earthmobsmod.handler.EarthTags;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    @OnlyIn(Dist.CLIENT)
    public void animateTick(World worldIn, BlockPos pos, IFluidState state, Random random) {
      /*  if (!state.isSource() && !state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            worldIn.addParticle(ParticleTypes.UNDERWATER, (double)((float)pos.getX() + random.nextFloat()), (double)((float)pos.getY() + random.nextFloat()), (double)((float)pos.getZ() + random.nextFloat()), 0.0D, 0.0D, 0.0D);
        }*/

    }

/*    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IParticleData getDripParticleData() {
        return ParticleTypes.DRIPPING_WATER;
    }*/

    protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
        TileEntity tileentity = state.getBlock().hasTileEntity() ? worldIn.getTileEntity(pos) : null;
        Block.spawnDrops(state, worldIn.getWorld(), pos, tileentity);
    }

    public int getSlopeFindDistance(IWorldReader worldIn) {
        return 4;
    }

    public BlockState getBlockState(IFluidState state) {
        return EarthBlocks.MUDWATER.getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
    }

    public boolean isEquivalentTo(Fluid fluidIn) {
        return fluidIn == EarthFluids.MUD_WATER || fluidIn == EarthFluids.MUD_WATER_FLOW;
    }

    public int getLevelDecreasePerBlock(IWorldReader worldIn) {
        return 2;
    }

    public int getTickRate(IWorldReader p_205569_1_) {
        return 15;
    }

    public boolean func_215665_a(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return p_215665_5_ == Direction.DOWN && !p_215665_4_.isIn(FluidTags.WATER) && !p_215665_4_.isIn(EarthTags.Fluids.MUD_WATER);
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }


    protected boolean canSourcesMultiply() {
        return false;
    }

    protected net.minecraftforge.fluids.FluidAttributes createAttributes(Fluid fluid) {
        return net.minecraftforge.fluids.FluidAttributes.builder("mud",
                new net.minecraft.util.ResourceLocation(EarthMobsMod.MODID, "blocks/mud"),
                new net.minecraft.util.ResourceLocation(EarthMobsMod.MODID, "blocks/mud"))
                .block(() -> EarthBlocks.MUDWATER)
                .density(1100).viscosity(2000).build();
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
            return 8;
        }

        public boolean isSource(IFluidState state) {
            return true;
        }
    }
}