package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.handler.EarthEntitys;
import baguchan.earthmobsmod.handler.EarthTags;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

public class MuddyPigEntity extends PigEntity implements net.minecraftforge.common.IShearable {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT, Items.POTATO, Items.BEETROOT);
    private static final DataParameter<Integer> FLOWER_COLOR = EntityDataManager.createKey(MuddyPigEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HAS_FLOWER = EntityDataManager.createKey(MuddyPigEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DRY = EntityDataManager.createKey(MuddyPigEntity.class, DataSerializers.BOOLEAN);

    private static final Map<DyeColor, IItemProvider> DYE_BY_COLOR = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
        p_203402_0_.put(DyeColor.WHITE, Items.WHITE_DYE);
        p_203402_0_.put(DyeColor.ORANGE, Items.ORANGE_DYE);
        p_203402_0_.put(DyeColor.MAGENTA, Items.MAGENTA_DYE);
        p_203402_0_.put(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_DYE);
        p_203402_0_.put(DyeColor.YELLOW, Items.YELLOW_DYE);
        p_203402_0_.put(DyeColor.LIME, Items.LIME_DYE);
        p_203402_0_.put(DyeColor.PINK, Items.PINK_DYE);
        p_203402_0_.put(DyeColor.GRAY, Items.GRAY_DYE);
        p_203402_0_.put(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_DYE);
        p_203402_0_.put(DyeColor.CYAN, Items.CYAN_DYE);
        p_203402_0_.put(DyeColor.PURPLE, Items.PURPLE_DYE);
        p_203402_0_.put(DyeColor.BLUE, Items.BLUE_DYE);
        p_203402_0_.put(DyeColor.BROWN, Items.BROWN_DYE);
        p_203402_0_.put(DyeColor.GREEN, Items.GREEN_DYE);
        p_203402_0_.put(DyeColor.RED, Items.RED_DYE);
        p_203402_0_.put(DyeColor.BLACK, Items.BLACK_DYE);
    });

    private boolean isWet;
    private boolean isShaking;
    protected boolean inMud;
    private float timeIsShaking;
    private float prevTimeIsShaking;

    private int dryTime;

    public MuddyPigEntity(EntityType<MuddyPigEntity> type, World p_i48574_2_) {
        super(type, p_i48574_2_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D, PigEntity.class));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.CARROT_ON_A_STICK), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(DRY, false);
        this.dataManager.register(HAS_FLOWER, true);
        this.dataManager.register(FLOWER_COLOR, DyeColor.RED.getId());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EntityType.PIG.getLootTable();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Dry", this.isDry());
        compound.putBoolean("HasFlower", this.getHasFlower());
        compound.putByte("FlowerColor", (byte) this.getFlowerColor().getId());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setDry(compound.getBoolean("Dry"));
        this.setHasFlower(compound.getBoolean("HasFlower"));
        this.setFlowerColor(DyeColor.byId(compound.getInt("FlowerColor")));
    }

    public DyeColor getFlowerColor() {
        return DyeColor.byId(this.dataManager.get(FLOWER_COLOR));
    }

    public void setFlowerColor(DyeColor color) {
        this.dataManager.set(FLOWER_COLOR, color.getId());
    }


    public boolean getHasFlower() {
        return this.dataManager.get(HAS_FLOWER);
    }

    public void setHasFlower(boolean hasFlower) {
        this.dataManager.set(HAS_FLOWER, hasFlower);
    }

    public boolean isDry() {
        return this.dataManager.get(DRY);
    }

    public void setDry(boolean hasFlower) {
        this.dataManager.set(DRY, hasFlower);
    }


    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();

        if (!itemstack.isEmpty()) {
            if (item instanceof DyeItem) {
                DyeColor dyecolor = ((DyeItem) item).getDyeColor();
                if (this.getHasFlower() && dyecolor != this.getFlowerColor()) {
                    this.setFlowerColor(dyecolor);
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return true;
                }
            }
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        return this.getHasFlower() && !this.isDry();
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            this.setHasFlower(false);
            ret.add(new ItemStack(DYE_BY_COLOR.get(this.getFlowerColor())));
        }
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isWet && !this.isShaking && !this.isDry()) {
            this.isShaking = true;
            this.timeIsShaking = 0.0F;
            this.prevTimeIsShaking = 0.0F;
            this.world.setEntityState(this, (byte) 8);
        }
    }

    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setHasFlower(true);

        this.setFlowerColor(DyeColor.byId(worldIn.getRandom().nextInt(DyeColor.values().length)));

        return spawnDataIn;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            if (this.isJumping) {
                if (!(this.submergedHeight > 0.0D) || this.onGround && !(this.submergedHeight > 0.4D)) {
                    this.handleFluidJump(EarthTags.Fluids.MUD_WATER);
                }
            }

            if(this.isDry()){
                if (this.isInMud() && !isShaking) {
                    dryTime = 0;
                    this.isShaking = true;
                    this.timeIsShaking = 0.0F;
                    this.prevTimeIsShaking = 0.0F;
                }

                if (++dryTime >= 2400D || this.isInWaterRainOrBubbleColumn()) {
                    if (!this.world.isRemote) {
                        PigEntity pigEntity = EntityType.PIG.create(world);
                        pigEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                        pigEntity.setNoAI(this.isAIDisabled());
                        if (this.hasCustomName()) {
                            pigEntity.setCustomName(this.getCustomName());
                            pigEntity.setCustomNameVisible(this.isCustomNameVisible());
                        }

                        if (this.getSaddled()) {
                            pigEntity.setSaddled(true);
                        }

                        if (this.isChild()) {
                            pigEntity.setGrowingAge(this.getGrowingAge());
                        }

                        this.world.addEntity(pigEntity);

                        this.remove();
                    }
                }
            }else {
                if (this.isInMud()) {
                    dryTime = 0;
                } else {
                    if (++dryTime >= 2400D && !isShaking) {
                        dryTime = 0;
                        this.isShaking = true;
                        this.timeIsShaking = 0.0F;
                        this.prevTimeIsShaking = 0.0F;
                    }
                }
            }

            if (this.isInWaterRainOrBubbleColumn() && !this.isWet && !isShaking && !this.isDry()) {
                this.isWet = true;
                this.isShaking = true;
                this.timeIsShaking = 0.0F;
                this.prevTimeIsShaking = 0.0F;
            } else if ((this.isWet || this.isShaking) && this.isShaking) {
                if (this.timeIsShaking == 0.0F) {
                    this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                }

                this.prevTimeIsShaking = this.timeIsShaking;
                this.timeIsShaking += 0.05F;
                if (this.prevTimeIsShaking >= 2.0F) {
                    if (!this.world.isRemote) {
                        if (this.isWet && !this.isDry()) {
                            this.setDry(true);
                            this.setHasFlower(false);
                        } else if (!this.isWet && this.isDry()) {
                            this.setDry(false);
                            this.setHasFlower(true);
                        } else if (!this.isWet && !this.isDry()) {
                            this.setDry(true);
                            this.setHasFlower(false);
                        }
                    }
                    this.isWet = false;
                    this.isShaking = false;
                    this.prevTimeIsShaking = 0.0F;
                    this.timeIsShaking = 0.0F;
                }

                if (this.timeIsShaking > 0.4F) {
                    float f = (float) this.getBoundingBox().minY;
                    int i = (int) (MathHelper.sin((this.timeIsShaking - 0.4F) * (float) Math.PI) * 7.0F);
                    Vec3d vec3d = this.getMotion();

                    for (int j = 0; j < i; ++j) {
                        float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.6F;
                        float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.6F;
                        if (isWet) {
                            this.world.addParticle(ParticleTypes.SPLASH, this.posX + (double) f1, (double) (f + 0.85F), this.posZ + (double) f2, vec3d.x, vec3d.y, vec3d.z);
                        }
                    }
                }
            }

        }
    }

    public boolean handleWaterMovement() {
        if (this.getRidingEntity() instanceof BoatEntity) {
            this.inWater = false;
        } else if (this.handleFluidAcceleration(EarthTags.Fluids.MUD_WATER)) {
            if (!this.inWater && !this.firstUpdate) {
                this.doWaterSplashEffect();
            }

            this.fallDistance = 0.0F;
            this.inMud = true;
            this.inWater = true;
            this.extinguish();
        } else {
            this.inMud = false;
            super.handleWaterMovement();
        }
        return this.isInWater();
    }

    public boolean isInWaterRainOrBubbleColumn() {
        return !this.isInMud() && super.isInWaterRainOrBubbleColumn();
    }

    public boolean isInMud() {
        return inMud;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 8) {
            this.isShaking = true;
            this.timeIsShaking = 0.0F;
            this.prevTimeIsShaking = 0.0F;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @Override
    public void onDeath(DamageSource cause) {
        this.isWet = false;
        this.isShaking = false;
        this.prevTimeIsShaking = 0.0F;
        this.timeIsShaking = 0.0F;
        super.onDeath(cause);
    }


    @OnlyIn(Dist.CLIENT)
    public boolean isWet() {
        return this.isWet;
    }

    /**
     * Used when calculating the amount of shading to apply while the wolf is wet.
     */
    @OnlyIn(Dist.CLIENT)
    public float getShadingWhileWet(float p_70915_1_) {
        return 0.75F + MathHelper.lerp(p_70915_1_, this.prevTimeIsShaking, this.timeIsShaking) / 2.0F * 0.25F;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
        float f = (MathHelper.lerp(p_70923_1_, this.prevTimeIsShaking, this.timeIsShaking) + p_70923_2_) / 1.8F;
        if (f < 0.0F) {
            f = 0.0F;
        } else if (f > 1.0F) {
            f = 1.0F;
        }

        return MathHelper.sin(f * (float) Math.PI) * MathHelper.sin(f * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    public MuddyPigEntity createChild(AgeableEntity ageable) {
        return EarthEntitys.MUDDYPIG.create(this.world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (otherAnimal instanceof PigEntity) {
            return this.isInLove() && otherAnimal.isInLove();
        } else {
            return false;
        }
    }
}