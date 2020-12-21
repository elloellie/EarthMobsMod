package baguchan.earthmobsmod.entity.ai;

import baguchan.earthmobsmod.entity.MooBloomEntity;
import baguchan.earthmobsmod.init.EarthBlocks;
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
    private static final Predicate<BlockState> IS_BUTTERCUP = BlockStateMatcher.forBlock(EarthBlocks.BUTTERCUP);
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
        BlockPos blockpos = new BlockPos(this.grassEaterEntity.getPosition());
        if (this.grassEaterEntity.getEatDelayTimer() <= 0) {
         if (IS_BUTTERCUP.test(this.entityWorld.getBlockState(blockpos))) {
             return true;
         } else if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 100 : 1000) != 0) {
             return false;
         } else {

             if (IS_BUTTERCUP.test(this.entityWorld.getBlockState(blockpos))) {
                 return true;
             } else {
                 return false;
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
            BlockPos blockpos = new BlockPos(this.grassEaterEntity.getPosition());
            if (IS_BUTTERCUP.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }
                this.grassEaterEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 160, 0));

                this.grassEaterEntity.eatGrassBonus();

                this.grassEaterEntity.setEatDelayTimer(200);
            }
        }
    }
}