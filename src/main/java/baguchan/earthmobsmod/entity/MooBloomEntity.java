package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.client.particle.EarthParticles;
import baguchan.earthmobsmod.entity.ai.EatGrassOrBloomGoal;
import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthEntitys;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
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
    private boolean didAttack;

    private int grassEatTimer;
    private int eatDelayTimer;
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
        this.goalSelector.addGoal(6, this.eatGrassGoal);
        this.goalSelector.addGoal(7, new MoveToGoal(this, 3.0F, 1.25D));
        this.goalSelector.addGoal(8, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(9, new MoveToBloom(this, 1.4D));
        this.goalSelector.addGoal(10, new WaterAvoidingRandomWalkingGoal(this, 1.0D) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && (getFlowerHome() == null || getDistanceSq(getFlowerHome().getX(), getFlowerHome().getY(), getFlowerHome().getZ()) < 11.0F);
            }
        });
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal(this, PlayerEntity.class, true) {
            @Override
            public boolean shouldExecute() {
                return getFlowerHome() != null && !isSleep() && super.shouldExecute() && this.nearestTarget.getDistanceSq(getFlowerHome().getX(), getFlowerHome().getY(), getFlowerHome().getZ()) < 11.0F;
            }

            @Override
            public boolean shouldContinueExecuting() {
                return super.shouldContinueExecuting() && this.nearestTarget.getDistanceSq(getFlowerHome().getX(), getFlowerHome().getY(), getFlowerHome().getZ()) < 11.0F;
            }
        }));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.2F);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(1.35D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SLEEP, false);
    }

    protected void updateAITasks() {
        this.grassEatTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();

        if (this.onGround && this.ticksExisted % 200 == 0 && this.world.rand.nextInt(1) == 0 && !this.isSleep() && (MooBloomEntity.this.moveStrafing > 0.0F || MooBloomEntity.this.moveVertical > 0.0F || MooBloomEntity.this.moveForward > 0.0F)) {
            FlowerBlock flowerBlock = EarthBlocks.GOLDENBLOOM;
            BlockPos blockpos = this.getPosition().down();
            if (flowerBlock.isValidPosition(flowerBlock.getDefaultState(), world, blockpos) && this.world.isAirBlock(this.getPosition())) {
                this.world.setBlockState(this.getPosition(), flowerBlock.getDefaultState());
            }
        }

        if (this.eatDelayTimer > 0) {
            --this.eatDelayTimer;
        }

        if (this.onGround && this.world.rand.nextInt(160) == 0 && this.isSleep()) {
            BlockPos blockPos = this.getPosition();

            for (int i = 0; i < 2 + this.rand.nextInt(6); i++) {
                BlockPos pos = new BlockPos(blockPos.getX() + this.rand.nextInt(12) - 6, blockPos.getY() + this.rand.nextInt(6) - 3, blockPos.getZ() + this.rand.nextInt(12) - 6);

                BlockState blockstate = this.world.getBlockState(pos);
                if (blockstate.getBlock() instanceof IGrowable && !(blockstate.getBlock() instanceof GrassBlock)) {
                    IGrowable igrowable = (IGrowable) blockstate.getBlock();
                    if (!this.world.isRemote) {
                        this.world.playEvent(2005, pos, 0);
                    }
                    if (igrowable.canGrow(this.world, pos, blockstate, this.world.isRemote)) {
                        if (!world.isRemote) {
                            if (igrowable.canUseBonemeal(this.world, this.world.rand, pos, blockstate)) {
                                igrowable.grow(this.world, this.world.rand, pos, blockstate);
                            }
                        }
                        for (int i2 = 0; i2 < 12; ++i2) {
                            int j = rand.nextInt(2) * 2 - 1;
                            int k = rand.nextInt(2) * 2 - 1;
                            double d0 = (double) pos.getX() + 0.5D + 0.25D * (double) j;
                            double d1 = (double) ((float) pos.getY() + rand.nextFloat());
                            double d2 = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
                            double d3 = (double) (rand.nextFloat() * (float) j);
                            double d4 = ((double) rand.nextFloat() - 0.5D) * 0.125D;
                            double d5 = (double) (rand.nextFloat() * (float) k);
                            EarthParticles.FLOWER_POLLEN.spawn(world, d0, d1, d2, d3, d4, d5);
                        }
                    }
                }

            }
        }

        if (isSleep() && this.world.isDaytime() || isSleep() && this.getAttackTarget() != null) {
            setSleep(false);
        }

	    if (!isSleep() && !this.world.isDaytime() && this.getAttackTarget() == null && this.rand.nextInt(240) == 0) {
            setSleep(true);
        }
    }

    public void livingTick() {
        if (this.world.isRemote) {
            this.grassEatTimer = Math.max(0, this.grassEatTimer - 1);
        }

        this.updateArmSwingProgress();
        super.livingTick();
    }

    public int getEatDelayTimer() {
        return eatDelayTimer;
    }

    public void setEatDelayTimer(int eatDelayTimer) {
        this.eatDelayTimer = eatDelayTimer;
    }

    protected SoundEvent getAmbientSound() {

        if (!isSleep()) {
            return SoundEvents.ENTITY_COW_AMBIENT;
        } else {
            return null;
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (item == Items.BOWL) {
            return false;
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        return true;
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        this.world.addParticle(ParticleTypes.EXPLOSION, this.posX, this.posY + (double) (this.getHeight() / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D);
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            ret.add(new ItemStack(Item.getItemFromBlock(EarthBlocks.GOLDENBLOOM), 3));

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
        compound.putInt("EatDelay", this.getEatDelayTimer());
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
        this.setEatDelayTimer(compound.getInt("EatDelay"));
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
        float f1 = (float) this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).getValue();
        if (flag) {
            this.applyEnchantments(this, entityIn);
            f1 += (float) EnchantmentHelper.getKnockbackModifier(this);
            this.didAttack = true;
        }

        if (f1 > 0.0F && entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).knockBack(this, f1 * 0.5F, (double) MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F))));
            this.setMotion(this.getMotion().mul(0.6D, 1.0D, 0.6D));
        }

        return flag;
    }

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		setSleep(false);
		return super.attackEntityFrom(source, amount);
	}

    private void setDidAttack(boolean didSpitIn) {
        this.didAttack = didSpitIn;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_ITEMS.test(stack);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EntityType.COW.getLootTable();
    }

    @Override
    public MooBloomEntity createChild(AgeableEntity ageable) {
        return EarthEntitys.MOOBLOOM.create(this.world);
    }

    class MoveToBloom extends MoveToBlockGoal {
        private final MooBloomEntity cow;
        private final int searchLength;
        private final int field_203113_j;
        protected int field_203112_e;

        public MoveToBloom(MooBloomEntity p_i48911_1_, double speed) {
            super(p_i48911_1_, speed, 6, 1);
            this.cow = p_i48911_1_;
            this.searchLength = 6;
            this.field_203112_e = 0;
            this.field_203113_j = 1;
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

        protected boolean searchForDestination() {
            int i = this.searchLength;
            int j = this.field_203113_j;
            BlockPos blockpos = new BlockPos(this.creature);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k = this.field_203112_e; k <= j; k = k > 0 ? -k : 1 - k) {
                for (int l = 0; l < i; ++l) {
                    for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                        for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                            blockpos$mutableblockpos.setPos(blockpos).move(i1, k - 1, j1);
                            if (this.creature.isWithinHomeDistanceFromPosition(blockpos$mutableblockpos) && this.shouldMoveTo(this.creature.world, blockpos$mutableblockpos)) {
                                if (this.cow.getFlowerHome() == null || blockpos$mutableblockpos.withinDistance(this.cow.getFlowerHome(), 2.25F)) {
                                    this.destinationBlock = blockpos$mutableblockpos;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            return false;
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
        final double speed;

        MoveToGoal(MooBloomEntity mooBloomEntity, double distance, double speed) {
            this.moobloom = mooBloomEntity;
            this.distance = distance;
            this.speed = speed;
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
                if (this.func_220846_a(blockpos, 6.0D)) {
                    Vec3d vec3d = (new Vec3d((double) blockpos.getX() - this.moobloom.posX, (double) blockpos.getY() - this.moobloom.posY, (double) blockpos.getZ() - this.moobloom.posZ)).normalize();
                    Vec3d vec3d1 = vec3d.scale(10.0D).add(this.moobloom.posX, this.moobloom.posY, this.moobloom.posZ);
                    MooBloomEntity.this.navigator.tryMoveToXYZ(vec3d1.x, vec3d1.y, vec3d1.z, this.speed);
                } else {
                    MooBloomEntity.this.navigator.tryMoveToXYZ((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speed);
                }
            }

        }

        private boolean func_220846_a(BlockPos p_220846_1_, double p_220846_2_) {
            return !p_220846_1_.withinDistance(this.moobloom.getPositionVec(), p_220846_2_);
        }
    }

    static class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        public HurtByTargetGoal(MooBloomEntity moobloom) {
            super(moobloom);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            //only one attack
            if (this.goalOwner instanceof MooBloomEntity) {
                MooBloomEntity moobloom = (MooBloomEntity) this.goalOwner;
                if (moobloom.didAttack) {
                    moobloom.setDidAttack(false);
                    return false;
                }
            }

            return super.shouldContinueExecuting();
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
