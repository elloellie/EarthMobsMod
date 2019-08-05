package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.handler.EarthEntitys;
import baguchan.earthmobsmod.handler.EarthItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
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
        super.registerGoals();
        this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.4D));
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

            ChickenEntity chickenEntity = EntityType.CHICKEN.create(this.world);
            chickenEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            chickenEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                chickenEntity.setCustomName(this.getCustomName());
                chickenEntity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isChild()) {
                chickenEntity.setGrowingAge(this.getGrowingAge());
            }

            this.world.addEntity(chickenEntity);

            this.remove();
        }

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
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
        return lightCheck(world, p_223325_3_, p_223325_4_) && func_223315_a(entity, world, p_223325_2_, p_223325_3_, p_223325_4_);
    }

    public static boolean lightCheck(IWorld world, BlockPos pos, Random rand) {
        if (world.getLightFor(LightType.SKY, pos) > rand.nextInt(32)) {
            return false;
        } else {
            int i = world.getWorld().isThundering() ? world.getNeighborAwareLightSubtracted(pos, 10) : world.getLight(pos);
            return i <= rand.nextInt(12);
        }
    }
}
