package baguchan.earthmobsmod.entity.ai;

import baguchan.earthmobsmod.entity.MooBloomEntity;
import baguchan.earthmobsmod.handler.EarthBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatGrassOrBloomGoal extends Goal {
    private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
    private static final Predicate<BlockState> IS_BLOOM = BlockStateMatcher.forBlock(EarthBlocks.GOLDENBLOOM);
    private final MooBloomEntity grassEaterEntity;
    private final World entityWorld;
    private int eatingGrassTimer;

    public EatGrassOrBloomGoal(MooBloomEntity grassEaterEntityIn) {
        this.grassEaterEntity = grassEaterEntityIn;
        this.entityWorld = grassEaterEntityIn.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        BlockPos blockpos = new BlockPos(this.grassEaterEntity);
        if (this.grassEaterEntity.getFlowerHome() == null || blockpos.withinDistance(this.grassEaterEntity.getFlowerHome(), 1.5F)) {
            if (IS_BLOOM.test(this.entityWorld.getBlockState(blockpos))) {
                return true;
            } else if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 80 : 1000) != 0) {
                return false;
            } else {

                if (IS_BLOOM.test(this.entityWorld.getBlockState(blockpos))) {
                    return true;
                } else if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
                    return true;
                } else {
                    return this.entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK;
                }


            }
        } else {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte) 10);
        this.grassEaterEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.eatingGrassTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.eatingGrassTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat grass
     */
    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        if (this.eatingGrassTimer == 4) {
            BlockPos blockpos = new BlockPos(this.grassEaterEntity);
            if (IS_BLOOM.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }
                this.grassEaterEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 160, 0));

                this.grassEaterEntity.eatGrassBonus();
            } else if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }

                this.grassEaterEntity.eatGrassBonus();
            } else {
                BlockPos blockpos1 = blockpos.down();
                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS_BLOCK) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                        this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
                    }

                    this.grassEaterEntity.eatGrassBonus();
                }
            }

        }
    }
}