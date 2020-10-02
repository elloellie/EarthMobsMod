package baguchan.earthmobsmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.world.World;

public class RainbowSheepEntity extends SheepEntity {
    public RainbowSheepEntity(EntityType<? extends SheepEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public DyeColor getFleeceColor() {
        return super.getFleeceColor();
    }
}
