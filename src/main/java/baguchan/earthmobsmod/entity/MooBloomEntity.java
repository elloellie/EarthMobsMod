package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.entity.ai.EatGrassOrBloomGoal;
import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthEntitys;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;

import java.util.EnumSet;
import java.util.List;

public class MooBloomEntity extends CowEntity implements IShearable, IForgeShearable {
    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(Items.GOLDEN_APPLE);
    private static final DataParameter<Boolean> SLEEP = EntityDataManager.createKey(MooBloomEntity.class, DataSerializers.BOOLEAN);

    private int grassEatTimer;
    private int eatDelayTimer;
    private EatGrassOrBloomGoal eatGrassGoal;

    public MooBloomEntity(EntityType<? extends MooBloomEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassOrBloomGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new DoNothingGoal());
        this.goalSelector.addGoal(2, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, BREEDING_ITEMS, false));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, BeeEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createMutableAttribute() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SLEEP, false);
    }

    protected void updateAITasks() {
        this.grassEatTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();

        if (this.onGround && this.ticksExisted % 140 == 0 && this.world.rand.nextInt(1) == 0 && !this.isSleep() && (MooBloomEntity.this.moveStrafing > 0.0F || MooBloomEntity.this.moveVertical > 0.0F || MooBloomEntity.this.moveForward > 0.0F)) {
            FlowerBlock flowerBlock = EarthBlocks.BUTTERCUP;
            BlockPos blockpos = this.getPosition().down();

            this.setEatDelayTimer(120);
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

                        if (this.world.getServer() != null && igrowable.canUseBonemeal(this.world, this.world.rand, pos, blockstate)) {
                            igrowable.grow(this.world.getServer().getWorld(this.world.getDimensionKey()), this.world.rand, pos, blockstate);
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
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (item == Items.BOWL) {
            return ActionResultType.FAIL;
        }
        return super.applyPlayerInteraction(player, vec, hand);
    }

    @Override
    public boolean isShearable() {
        return true;
    }

    @Override
    public boolean isShearable(ItemStack item, World world, BlockPos pos) {
        return true;
    }

    @Override
    public void shear(SoundCategory category) {
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY() + (double) (this.getHeight() / 2.0F), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        if (!this.world.isRemote) {
            this.entityDropItem(new ItemStack(Item.getItemFromBlock(EarthBlocks.BUTTERCUP), 3));

            CowEntity cowEntity = EntityType.COW.create(this.world);
            cowEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
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
    }

    @Override
    public List<ItemStack> onSheared(PlayerEntity player, ItemStack item, World world, BlockPos pos, int fortune) {
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY() + (double) (this.getHeight() / 2.0F), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            ret.add(new ItemStack(Item.getItemFromBlock(EarthBlocks.BUTTERCUP), 3));

            CowEntity cowEntity = EntityType.COW.create(this.world);
            cowEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
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

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSleep(compound.getBoolean("Sleep"));
        this.setEatDelayTimer(compound.getInt("EatDelay"));

        this.setGrowingAge(Math.max(0, this.getGrowingAge()));
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
    public boolean attackEntityFrom(DamageSource source, float amount) {
        setSleep(false);
        return super.attackEntityFrom(source, amount);
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
    public MooBloomEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity ageable) {
        return EarthEntitys.MOOBLOOM.create(this.world);
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
            MooBloomEntity.this.getMoveHelper().setMoveTo(MooBloomEntity.this.getPosX(), MooBloomEntity.this.getPosY(), MooBloomEntity.this.getPosZ(), 0.0D);
        }

        @Override
        public void tick() {
            super.tick();
            MooBloomEntity.this.getNavigator().clearPath();
        }
    }
}
