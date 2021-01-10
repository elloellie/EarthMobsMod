package baguchan.earthmobsmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class VilerWitchEntity extends WitchEntity {
    public VilerWitchEntity(EntityType<? extends WitchEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute createMutableAttribute() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 30.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        if (!this.isDrinkingPotion()) {
            Vector3d vector3d = target.getMotion();
            double d0 = target.getPosX() + vector3d.x - this.getPosX();
            double d1 = target.getPosYEye() - (double) 1.1F - this.getPosY();
            double d2 = target.getPosZ() + vector3d.z - this.getPosZ();
            float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
            Potion potion = Potions.HARMING;
            if (target instanceof AbstractRaiderEntity) {
                if (target.getHealth() <= 4.0F) {
                    potion = Potions.HEALING;
                } else {
                    potion = Potions.LONG_REGENERATION;
                }

                this.setAttackTarget((LivingEntity) null);
            } else if (f >= 8.0F && !target.isPotionActive(Effects.SLOWNESS)) {
                potion = Potions.SLOWNESS;
            } else if (target.getHealth() >= 8.0F && !target.isPotionActive(Effects.POISON)) {
                potion = Potions.POISON;
            } else if (f <= 3.0F && !target.isPotionActive(Effects.WEAKNESS) && this.rand.nextFloat() < 0.25F) {
                potion = Potions.WEAKNESS;
            }

            PotionEntity potionentity = new PotionEntity(this.world, this);
            potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), potion));
            potionentity.rotationPitch -= -20.0F;
            potionentity.shoot(d0, d1 + (double) (f * 0.2F), d2, 0.75F, 8.5F);
            if (!this.isSilent()) {
                this.world.playSound((PlayerEntity) null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
            }

            this.world.addEntity(potionentity);
        }
    }
}
