package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.entity.ai.EatGrassOrBloomGoal;
import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthEntitys;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class MooBloomEntity extends CowEntity implements net.minecraftforge.common.IShearable {
    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(Items.GOLDEN_APPLE);
    private static final DataParameter<Boolean> SLEEP = EntityDataManager.createKey(MooBloomEntity.class, DataSerializers.BOOLEAN);


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
        this.goalSelector.addGoal(1, new DoNothingGoal());
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.45D, false));
        this.goalSelector.addGoal(3, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.25D, BREEDING_ITEMS, false));
        this.goalSelector.addGoal(6, new MoveToGoal(this, 4.5D, 1.25D));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(8, this.eatGrassGoal);
        this.goalSelector.addGoal(9, new MoveToBloom(this, 1.4D));
        this.goalSelector.addGoal(10, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.2F);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(0.8D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SLEEP, false);
    }

    protected void updateAITasks() {
        this.grassEatTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();

        if (this.onGround && this.ticksExisted % 200 == 0 && !this.isSleep() && (MooBloomEntity.this.moveStrafing > 0.0F || MooBloomEntity.this.moveVertical > 0.0F || MooBloomEntity.this.moveForward > 0.0F)) {
            FlowerBlock flowerBlock = EarthBlocks.GOLDENBLOOM;
            BlockPos blockpos = this.getPosition().down();
            if (flowerBlock.isValidPosition(flowerBlock.getDefaultState(), world, blockpos) && this.world.isAirBlock(this.getPosition())) {
                this.world.setBlockState(this.getPosition(), flowerBlock.getDefaultState());
            }
        }

        if (isSleep() && this.world.isDaytime() || isSleep() && this.getAttackTarget() != null) {
            setSleep(false);
        }

        if (!isSleep() && !this.world.isDaytime() && this.getAttackTarget() == null) {
            setSleep(true);
        }
    }

    public void livingTick() {
        if (this.world.isRemote) {
            this.grassEatTimer = Math.max(0, this.grassEatTimer - 1);
        }

        super.livingTick();
    }

    protected SoundEvent getAmbientSound() {

        if (!isSleep()) {
            return SoundEvents.ENTITY_COW_AMBIENT;
        } else {
            return null;
        }
    }

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        return true;
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            ret.add(new ItemStack(Item.getItemFromBlock(EarthBlocks.GOLDENBLOOM)));

            CowEntity cowEntity = EntityType.COW.create(this.world);
            cowEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            cowEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                cowEntity.setCustomName(this.getCustomName());
                cowEntity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isChild()) {
                cowEntity.setGrowingAge(this.getGrowingAge());
            }

            this.world.addEntity(cowEntity);

            this.remove();
        }

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    public boolean isSleep() {
        return this.dataManager.get(SLEEP);
    }

    public void setSleep(boolean sleep) {
        this.dataManager.set(SLEEP, sleep);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Sleep", this.isSleep());
        if (this.flowerHomeTarget != null) {
            compound.put("HomeTarget", NBTUtil.writeBlockPos(this.flowerHomeTarget));
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSleep(compound.getBoolean("Sleep"));
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

    class MoveToBloom extends MoveToBlockGoal {
        private final MooBloomEntity cow;

        public MoveToBloom(MooBloomEntity p_i48911_1_, double p_i48911_2_) {
            super(p_i48911_1_, p_i48911_2_, 6, 1);
            this.cow = p_i48911_1_;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return super.shouldExecute();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting();
        }

        /**
         * Return true to set given position as destination
         */
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos) == EarthBlocks.GOLDENBLOOM.getDefaultState() && worldIn.isAirBlock(pos.up());
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            super.resetTask();
        }
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

    class DoNothingGoal extends Goal {
        public DoNothingGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }


        public boolean shouldContinueExecuting() {
            return MooBloomEntity.this.isSleep();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return MooBloomEntity.this.isSleep();
        }


        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            MooBloomEntity.this.setJumping(false);
            MooBloomEntity.this.setSleep(true);
            MooBloomEntity.this.getMoveHelper().setMoveTo(MooBloomEntity.this.posX, MooBloomEntity.this.posY, MooBloomEntity.this.posZ, 0.0D);
        }

        @Override
        public void tick() {
            super.tick();
            MooBloomEntity.this.getNavigator().clearPath();
        }
    }
}
