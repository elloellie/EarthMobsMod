package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.handler.EarthEntitys;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class HornedSheepEntity extends SheepEntity {
    public HornedSheepEntity(EntityType<? extends SheepEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.goals.stream().map(it -> it.inner).filter(it -> it instanceof PanicGoal).findFirst().ifPresent(panicGoal -> {
            this.goalSelector.removeGoal(panicGoal);
            this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, false));
        });
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }


    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void livingTick() {
        this.updateArmSwingProgress();
        super.livingTick();
    }

    public static AttributeModifierMap.MutableAttribute createMutableAttribute() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.23F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0F).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.35F);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue()));
        float f1 = (float) this.getAttribute(Attributes.ATTACK_KNOCKBACK).getValue();
        if (flag) {
            this.applyEnchantments(this, entityIn);
            f1 += (float) EnchantmentHelper.getKnockbackModifier(this);

        }

        if (f1 > 0.0F && entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).applyKnockback(f1 * 0.5F, (double) MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F))));
            this.setMotion(this.getMotion().mul(0.6D, 1.0D, 0.6D));
        }

        return flag;
    }

    @Override
    public HornedSheepEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        HornedSheepEntity sheepentity = (HornedSheepEntity) p_241840_2_;
        HornedSheepEntity sheepentity1 = EarthEntitys.HORNED_SHEEP.create(p_241840_1_);
        sheepentity1.setFleeceColor(this.getDyeColorMixFromParents(this, sheepentity));
        return sheepentity1;
    }

    private DyeColor getDyeColorMixFromParents(AnimalEntity father, AnimalEntity mother) {
        DyeColor dyecolor = ((HornedSheepEntity) father).getFleeceColor();
        DyeColor dyecolor1 = ((HornedSheepEntity) mother).getFleeceColor();
        CraftingInventory craftinginventory = createDyeColorCraftingInventory(dyecolor, dyecolor1);
        return this.world.getRecipeManager().getRecipe(IRecipeType.CRAFTING, craftinginventory, this.world).map((p_213614_1_) -> {
            return p_213614_1_.getCraftingResult(craftinginventory);
        }).map(ItemStack::getItem).filter(DyeItem.class::isInstance).map(DyeItem.class::cast).map(DyeItem::getDyeColor).orElseGet(() -> {
            return this.world.rand.nextBoolean() ? dyecolor : dyecolor1;
        });
    }

    private static CraftingInventory createDyeColorCraftingInventory(DyeColor color, DyeColor color1) {
        CraftingInventory craftinginventory = new CraftingInventory(new Container((ContainerType) null, -1) {
            /**
             * Determines whether supplied player can use this container
             */
            public boolean canInteractWith(PlayerEntity playerIn) {
                return false;
            }
        }, 2, 1);
        craftinginventory.setInventorySlotContents(0, new ItemStack(DyeItem.getItem(color)));
        craftinginventory.setInventorySlotContents(1, new ItemStack(DyeItem.getItem(color1)));
        return craftinginventory;
    }
}
