package baguchan.earthmobsmod.entity.ai;

import baguchan.earthmobsmod.init.EarthTags;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SwimMudGoal extends Goal {
    private final MobEntity entity;

    public SwimMudGoal(MobEntity entityIn) {
        this.entity = entityIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP));
        entityIn.getNavigator().setCanSwim(true);
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        return this.entity.func_233571_b_(EarthTags.Fluids.MUD_WATER) > this.entity.func_233579_cu_();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.entity.getRNG().nextFloat() < 0.8F) {
            this.entity.getJumpController().setJumping();
        }
    }
}