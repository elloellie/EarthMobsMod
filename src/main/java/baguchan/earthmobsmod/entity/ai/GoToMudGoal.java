package baguchan.earthmobsmod.entity.ai;

import baguchan.earthmobsmod.entity.MuddyPigEntity;
import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthTags;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class GoToMudGoal extends MoveToBlockGoal {
    private final PigEntity pig;
    private float oldWaterCost;

    public GoToMudGoal(PigEntity pig, double p_i48819_2_) {
        super(pig, p_i48819_2_, 24);
        this.pig = pig;
        this.field_203112_e = -1;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return !this.pig.handleFluidAcceleration(EarthTags.Fluids.MUD_WATER, 0.014D) && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.pig.world, this.destinationBlock);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (!this.pig.handleFluidAcceleration(EarthTags.Fluids.MUD_WATER, 0.014D)) {
            return super.shouldExecute();
        } else {
            return false;
        }
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        if (this.pig instanceof MuddyPigEntity) {
            ((MuddyPigEntity) this.pig).setRunning(true);
        }
    }

    @Override
    public void resetTask() {
        super.resetTask();
        if (this.pig instanceof MuddyPigEntity) {
            ((MuddyPigEntity) this.pig).setRunning(false);
        }
    }

    public boolean shouldMove() {
        return this.timeoutCounter % 160 == 0;
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();
        return block == EarthBlocks.MUDWATER;
    }
}
