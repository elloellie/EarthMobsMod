package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.handler.EarthBlocks;
import baguchan.earthmobsmod.handler.EarthEntitys;
import baguchan.earthmobsmod.handler.EarthLootTables;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class RainbowSheepEntity extends SheepEntity {
    public RainbowSheepEntity(EntityType<? extends SheepEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute createMutableAttribute() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.23F);
    }

    @Override
    public RainbowSheepEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        RainbowSheepEntity sheepentity = (RainbowSheepEntity) p_241840_2_;
        RainbowSheepEntity sheepentity1 = EarthEntitys.RAINBOW_SHEEP.create(p_241840_1_);
        return sheepentity1;
    }

    public ResourceLocation getLootTable() {
        if (this.getSheared()) {
            return this.getType().getLootTable();
        } else {
            return EarthLootTables.ENTITIES_RAINBOW_SHEEP_WOOL;
        }
    }

    public void shear(SoundCategory category) {
        this.world.playMovingSound((PlayerEntity) null, this, SoundEvents.ENTITY_SHEEP_SHEAR, category, 1.0F, 1.0F);
        this.setSheared(true);
        int i = 1 + this.rand.nextInt(3);

        for (int j = 0; j < i; ++j) {
            ItemEntity itementity = this.entityDropItem(EarthBlocks.RAINBOW_WOOL, 1);
            if (itementity != null) {
                itementity.setMotion(itementity.getMotion().add((double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double) (this.rand.nextFloat() * 0.05F), (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
            }
        }

    }

    @Override
    public java.util.List<ItemStack> onSheared(@Nullable PlayerEntity player, @javax.annotation.Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isRemote) {
            this.setSheared(true);
            int i = 1 + this.rand.nextInt(3);

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            for (int j = 0; j < i; ++j) {
                items.add(new ItemStack(EarthBlocks.RAINBOW_WOOL));
            }
            return items;
        }
        return java.util.Collections.emptyList();
    }
}
