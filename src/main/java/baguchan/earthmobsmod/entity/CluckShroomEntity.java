package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.handler.EarthEntitys;
import baguchan.earthmobsmod.handler.EarthItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class CluckShroomEntity extends ChickenEntity implements net.minecraftforge.common.IShearable {
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
                if (world.isSkyLightMax(new BlockPos(this.creature.posX, this.creature.getBoundingBox().minY, this.creature.posZ)) && world.isDaytime()) {
                    return findRandomPosition();
                } else {
                    return super.shouldExecute();
                }
            }
        });
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));

    }

    @Nullable
    public ItemEntity entityDropItem(IItemProvider itemProvider) {

        if (itemProvider == Items.EGG) {
            itemProvider = EarthItems.SMELLY_EGG;
        }
        return this.entityDropItem(itemProvider, 0);
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
            ret.add(new ItemStack(Item.getItemFromBlock(Blocks.RED_MUSHROOM), 2));

            ChickenEntity cluckShroomEntity = EntityType.CHICKEN.create(this.world);
            cluckShroomEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
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

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
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

    public CluckShroomEntity createChild(AgeableEntity ageable) {
        return EarthEntitys.CLUCKSHROOM.create(this.world);
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
        if (world.canBlockSeeSky(pos) && !world.getWorld().isDaytime()) {
            return true;
        } else {
            return !world.getWorld().isDaytime() && world.getLightFor(LightType.SKY, pos) < rand.nextInt(20);
        }
    }
}
