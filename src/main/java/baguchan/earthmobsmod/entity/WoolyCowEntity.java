package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.init.EarthEntitys;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WoolyCowEntity extends CowEntity {
    public WoolyCowEntity(EntityType<? extends CowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute createMutableAttribute() {
        return func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.2F).createMutableAttribute(Attributes.ARMOR, 1.0D).createMutableAttribute(Attributes.MAX_HEALTH, 10.0F);
    }

    public CowEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return EarthEntitys.WOOLY_COW.create(this.world);
    }
}
