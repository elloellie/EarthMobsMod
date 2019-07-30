package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.entity.ai.EatGrassOrBloomGoal;
import baguchan.earthmobsmod.handler.EarthEntitys;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class MooBloomEntity extends CowEntity {
    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(Items.GOLDEN_APPLE);
    private int grassEatTimer;
    private EatGrassOrBloomGoal eatGrassGoal;
    @Nullable
    private BlockPos flowerHomeTarget;

    public MooBloomEntity(EntityType<? extends MooBloomEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassOrBloomGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3D, false));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.GOLDEN_APPLE), false));
        this.goalSelector.addGoal(4, new MoveToGoal(this, 4.5D, 1.25D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(6, this.eatGrassGoal);
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.2F);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(0.4D);
    }

    protected void updateAITasks() {
        this.grassEatTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    public void livingTick() {
        if (this.world.isRemote) {
            this.grassEatTimer = Math.max(0, this.grassEatTimer - 1);
        }

        super.livingTick();
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.flowerHomeTarget != null) {
            compound.put("HomeTarget", NBTUtil.writeBlockPos(this.flowerHomeTarget));
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("HomeTarget")) {
            this.flowerHomeTarget = NBTUtil.readBlockPos(compound.getCompound("HomeTarget"));
        }

        this.setGrowingAge(Math.max(0, this.getGrowingAge()));
    }

    public void setFlowerHome(@Nullable BlockPos p_213726_1_) {
        this.flowerHomeTarget = p_213726_1_;
    }

    @Nullable
    public BlockPos getFlowerHome() {
        return this.flowerHomeTarget;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.grassEatTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.grassEatTimer <= 0) {
            return 0.0F;
        } else if (this.grassEatTimer >= 4 && this.grassEatTimer <= 36) {
            return 1.0F;
        } else {
            return this.grassEatTimer < 4 ? ((float) this.grassEatTimer - p_70894_1_) / 4.0F : -((float) (this.grassEatTimer - 40) - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.grassEatTimer > 4 && this.grassEatTimer <= 36) {
            float f = ((float) (this.grassEatTimer - 4) - p_70890_1_) / 32.0F;
            return ((float) Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.grassEatTimer > 0 ? ((float) Math.PI / 5F) : this.rotationPitch * ((float) Math.PI / 180F);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_ITEMS.test(stack);
    }

    @Override
    public MooBloomEntity createChild(AgeableEntity ageable) {
        return EarthEntitys.MOOBLOOM.create(this.world);
    }

    class MoveToGoal extends Goal {
        final MooBloomEntity moobloom;
        final double distance;
        final double field_220849_c;

        MoveToGoal(MooBloomEntity mooBloomEntity, double distance, double p_i50459_5_) {
            this.moobloom = mooBloomEntity;
            this.distance = distance;
            this.field_220849_c = p_i50459_5_;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            MooBloomEntity.this.navigator.clearPath();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            BlockPos blockpos = this.moobloom.getFlowerHome();
            return blockpos != null && this.func_220846_a(blockpos, this.distance);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = this.moobloom.getFlowerHome();
            if (blockpos != null && MooBloomEntity.this.navigator.noPath()) {
                if (this.func_220846_a(blockpos, 10.0D)) {
                    Vec3d vec3d = (new Vec3d((double) blockpos.getX() - this.moobloom.posX, (double) blockpos.getY() - this.moobloom.posY, (double) blockpos.getZ() - this.moobloom.posZ)).normalize();
                    Vec3d vec3d1 = vec3d.scale(10.0D).add(this.moobloom.posX, this.moobloom.posY, this.moobloom.posZ);
                    MooBloomEntity.this.navigator.tryMoveToXYZ(vec3d1.x, vec3d1.y, vec3d1.z, this.field_220849_c);
                } else {
                    MooBloomEntity.this.navigator.tryMoveToXYZ((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.field_220849_c);
                }
            }

        }

        private boolean func_220846_a(BlockPos p_220846_1_, double p_220846_2_) {
            return !p_220846_1_.withinDistance(this.moobloom.getPositionVec(), p_220846_2_);
        }
    }
}
