package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.entity.ai.MoveToMushroom;
import baguchan.earthmobsmod.handler.EarthEntitys;
import baguchan.earthmobsmod.handler.EarthItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CluckShroomEntity extends ChickenEntity implements IShearable, IForgeShearable {
    private static final DataParameter<String> CLUCKSHROOM_TYPE = EntityDataManager.createKey(CluckShroomEntity.class, DataSerializers.STRING);

    private UUID lightningUUID;

    public CluckShroomEntity(EntityType<? extends CluckShroomEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.45D));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.45D) {
            @Override
            public boolean shouldExecute() {
                if (world.getLightFor(LightType.SKY, getPosition()) > 14 && world.isDaytime()) {
                    return findRandomPosition();
                } else {
                    return super.shouldExecute();
                }
            }
        });
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new MoveToMushroom(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(CLUCKSHROOM_TYPE, CluckShroomEntity.Type.RED.name);
    }

    public static AttributeModifierMap.MutableAttribute createMutableAttribute() {
        return ChickenEntity.func_234187_eI_();
    }

    @Nullable
    public ItemEntity entityDropItem(IItemProvider itemProvider) {

        if (itemProvider == Items.EGG) {
            itemProvider = EarthItems.SMELLY_EGG;
        }
        return this.entityDropItem(itemProvider, 0);
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    protected void updateAITasks() {
        super.updateAITasks();

        if (this.world.rand.nextInt(300) == 0 && this.world.getLightSubtracted(this.getPosition(), 0) < 12 && !this.isChild()) {
            BlockPos blockPos = this.getPosition();

            for (int i = 0; i < 2 + this.rand.nextInt(6); i++) {
                BlockPos pos = new BlockPos(blockPos.getX() + this.rand.nextInt(12) - 6, blockPos.getY() + this.rand.nextInt(4) - 2, blockPos.getZ() + this.rand.nextInt(12) - 6);

                BlockState blockstate = this.world.getBlockState(pos);
                if (blockstate.getBlock() == Blocks.RED_MUSHROOM || blockstate.getBlock() == Blocks.BROWN_MUSHROOM) {

                    if (!this.world.isRemote) {
                        this.world.playEvent(2005, pos, 0);
                        CluckShroomEntity cluckShroomEntity = EarthEntitys.CLUCKSHROOM.create(this.world);
                        cluckShroomEntity.setLocationAndAngles(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, 0.0F, 0.0F);
                        cluckShroomEntity.setNoAI(this.isAIDisabled());

                        cluckShroomEntity.setGrowingAge(-24000);

                        this.world.addEntity(cluckShroomEntity);

                        this.world.destroyBlock(pos, false);
                    }

                }

            }
        }
    }

    private void setCluckShroomType(CluckShroomEntity.Type typeIn) {
        this.dataManager.set(CLUCKSHROOM_TYPE, typeIn.name);
    }

    public CluckShroomEntity.Type getCluckShroomType() {
        return CluckShroomEntity.Type.getTypeByName(this.dataManager.get(CLUCKSHROOM_TYPE));
    }

    public CluckShroomEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity ageable) {
        CluckShroomEntity cluckshroomentity = EarthEntitys.CLUCKSHROOM.create(this.world);
        cluckshroomentity.setCluckShroomType(this.func_213445_a((CluckShroomEntity) ageable));
        return cluckshroomentity;
    }

    private CluckShroomEntity.Type func_213445_a(CluckShroomEntity p_213445_1_) {
        CluckShroomEntity.Type cluckshroomentity$type = this.getCluckShroomType();
        CluckShroomEntity.Type cluckshroomentity$type1 = p_213445_1_.getCluckShroomType();
        CluckShroomEntity.Type cluckshroomentity$type2;
        if (cluckshroomentity$type == cluckshroomentity$type1 && this.rand.nextInt(1024) == 0) {
            cluckshroomentity$type2 = cluckshroomentity$type == CluckShroomEntity.Type.BROWN ? CluckShroomEntity.Type.RED : CluckShroomEntity.Type.BROWN;
        } else {
            cluckshroomentity$type2 = this.rand.nextBoolean() ? cluckshroomentity$type : cluckshroomentity$type1;
        }

        return cluckshroomentity$type2;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EntityType.CHICKEN.getLootTable();
    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return 1.0F - worldIn.getBrightness(pos);
    }


    public static boolean spawnHandler(EntityType<? extends CluckShroomEntity> entity, IWorld world, SpawnReason p_223325_2_, BlockPos p_223325_3_, Random p_223325_4_) {
        return world.getBlockState(p_223325_3_.down()).getBlock() == Blocks.MYCELIUM && lightCheck(world, p_223325_3_, p_223325_4_);
    }

    public static boolean lightCheck(IWorld world, BlockPos pos, Random rand) {
        if (world.canBlockSeeSky(pos) && world.getLightFor(LightType.SKY, pos) < rand.nextInt(8)) {
            return true;
        } else {
            return world.getLightFor(LightType.SKY, pos) < rand.nextInt(20);
        }
    }

    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        UUID uuid = lightningBolt.getUniqueID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setCluckShroomType(this.getCluckShroomType() == CluckShroomEntity.Type.RED ? CluckShroomEntity.Type.BROWN : CluckShroomEntity.Type.RED);
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }

    }

    @Override
    public void shear(SoundCategory category) {
        world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, category, 1.0F, 1.0F);
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY() + (double) (this.getHeight() / 2.0F), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        if (!this.world.isRemote) {

            this.entityDropItem(new ItemStack(Item.getItemFromBlock(this.getCluckShroomType().getState().getBlock()), 2));

            ChickenEntity cluckShroomEntity = EntityType.CHICKEN.create(this.world);
            cluckShroomEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            cluckShroomEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                cluckShroomEntity.setCustomName(this.getCustomName());
                cluckShroomEntity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isChild()) {
                cluckShroomEntity.setGrowingAge(this.getGrowingAge());
            }

            this.world.addEntity(cluckShroomEntity);

            this.remove();
        }
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
    public List<ItemStack> onSheared(PlayerEntity player, ItemStack item, World world, BlockPos pos, int fortune) {
        world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY() + (double) (this.getHeight() / 2.0F), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {

            ret.add(new ItemStack(Item.getItemFromBlock(this.getCluckShroomType().getState().getBlock()), 2));

            ChickenEntity cluckShroomEntity = EntityType.CHICKEN.create(this.world);
            cluckShroomEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            cluckShroomEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                cluckShroomEntity.setCustomName(this.getCustomName());
                cluckShroomEntity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isChild()) {
                cluckShroomEntity.setGrowingAge(this.getGrowingAge());
            }

            this.world.addEntity(cluckShroomEntity);

            this.remove();
        }
        return ret;
    }

    public static enum Type {
        RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

        private final String name;
        private final BlockState renderState;

        private Type(String nameIn, BlockState renderStateIn) {
            this.name = nameIn;
            this.renderState = renderStateIn;
        }

        /**
         * A block state that is rendered on the back of the cluckshroom.
         */
        public BlockState getState() {
            return this.renderState;
        }

        private static CluckShroomEntity.Type getTypeByName(String nameIn) {
            for (CluckShroomEntity.Type cluckshroomentity$type : values()) {
                if (cluckshroomentity$type.name.equals(nameIn)) {
                    return cluckshroomentity$type;
                }
            }

            return RED;
        }
    }
}
