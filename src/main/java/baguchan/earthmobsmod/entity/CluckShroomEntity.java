package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.handler.EarthEntitys;
import baguchan.earthmobsmod.handler.EarthItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CluckShroomEntity extends ChickenEntity {
    public CluckShroomEntity(EntityType<? extends CluckShroomEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    public ItemEntity entityDropItem(IItemProvider itemProvider) {

        if (itemProvider == Items.EGG) {
            itemProvider = EarthItems.SMELLY_EGG;
        }
        return this.entityDropItem(itemProvider, 0);
    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getBlockState(pos.down()).getBlock() == Blocks.MYCELIUM ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
    }

    public CluckShroomEntity createChild(AgeableEntity ageable) {
        return EarthEntitys.CLUCKSHROOM.create(this.world);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EntityType.CHICKEN.getLootTable();
    }
}
