package baguchan.earthmobsmod.fluid;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthFluids;
import baguchan.earthmobsmod.handler.EarthItems;
import baguchan.earthmobsmod.handler.EarthTags;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
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
public abstract class FluidMudWater extends FlowingFluid {
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
        return fluidIn.isIn(EarthTags.Fluids.MUD_WATER);
    }

    public int getLevelDecreasePerBlock(IWorldReader worldIn) {
        return 2;
    }

    public int getTickRate(IWorldReader p_205569_1_) {
        return 10;
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }


    protected boolean canSourcesMultiply() {
        return false;
    }

    protected void flowInto(IWorld worldIn, BlockPos pos, BlockState blockStateIn, Direction direction, IFluidState fluidStateIn) {
        if (direction == Direction.DOWN) {
            IFluidState ifluidstate = worldIn.getFluidState(pos);
            if (ifluidstate.isTagged(FluidTags.LAVA)) {

                worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState(), Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.BLOCK_UPDATE);
                worldIn.playEvent(1501, pos, 0);

                this.triggerEffects(worldIn, pos);
                return;
            }
        }

        super.flowInto(worldIn, pos, blockStateIn, direction, fluidStateIn);
    }

    private void triggerEffects(IWorld p_205581_1_, BlockPos p_205581_2_) {
        p_205581_1_.playEvent(1501, p_205581_2_, 0);
    }

    @Override
    protected boolean canDisplace(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return p_215665_5_ == Direction.DOWN && !p_215665_4_.isIn(EarthTags.Fluids.MUD_WATER);
    }

    @Override
    public boolean isEntityInside(IFluidState state, IWorldReader world, BlockPos pos, Entity entity, double yToTest, Tag<Fluid> tag, boolean testingHead) {

        if (testingHead) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setAir(decreaseAirSupply(livingEntity.getAir(), livingEntity));
                if (livingEntity.getAir() == -20) {
                    livingEntity.setAir(0);
                    Vec3d vec3d = livingEntity.getMotion();

                    for (int i = 0; i < 8; ++i) {
                        float f = livingEntity.world.rand.nextFloat() - livingEntity.world.rand.nextFloat();
                        float f1 = livingEntity.world.rand.nextFloat() - livingEntity.world.rand.nextFloat();
                        float f2 = livingEntity.world.rand.nextFloat() - livingEntity.world.rand.nextFloat();
                        livingEntity.world.addParticle(ParticleTypes.BUBBLE, livingEntity.posX + (double) f, livingEntity.posY + (double) f1, livingEntity.posZ + (double) f2, vec3d.x, vec3d.y, vec3d.z);
                    }

                    livingEntity.attackEntityFrom(DamageSource.DROWN, 2.0F);
                }
            }
        }

        return super.isEntityInside(state, world, pos, entity, yToTest, tag, testingHead);
    }

    protected int decreaseAirSupply(int air, LivingEntity livingEntity) {
        int i = EnchantmentHelper.getRespirationModifier(livingEntity);
        return i > 0 && livingEntity.world.rand.nextInt(i + 1) > 0 ? air : air - 1;
    }

    @Override
    protected net.minecraftforge.fluids.FluidAttributes createAttributes() {
        return net.minecraftforge.fluids.FluidAttributes.builder(
                new net.minecraft.util.ResourceLocation(EarthMobsMod.MODID, "blocks/mud"),
                new net.minecraft.util.ResourceLocation(EarthMobsMod.MODID, "blocks/flow_mud"))
                .overlay(new net.minecraft.util.ResourceLocation(EarthMobsMod.MODID, "blocks/mud_overlay")).density(1100).viscosity(2000).build(this);
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