package baguchan.earthmobsmod.entity.ai;

import baguchan.earthmobsmod.entity.CluckShroomEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class MoveToMushroom extends MoveToBlockGoal {
    private final CluckShroomEntity cluckShroomEntity;

    public MoveToMushroom(CluckShroomEntity cluckShroomEntity, double speed) {
        super(cluckShroomEntity, speed, 8);
        this.cluckShroomEntity = cluckShroomEntity;
    }

    public boolean shouldExecute() {
        return !this.cluckShroomEntity.isChild() && super.shouldExecute() && !this.cluckShroomEntity.world.canBlockSeeSky(cluckShroomEntity.getPosition());
    }

    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting();
    }

    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        Block block = blockstate.getBlock();
        return block instanceof MushroomBlock;
    }
}
