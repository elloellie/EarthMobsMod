package baguchan.earthmobsmod.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

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
}
