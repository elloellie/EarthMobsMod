package baguchan.earthmobsmod.entity;

import baguchan.earthmobsmod.init.EarthEntitys;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BoulderingZombieEntity extends ZombieEntity {
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(BoulderingZombieEntity.class, DataSerializers.BYTE);
    private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    private AxisAlignedBB climingBoundingBox = ZERO_AABB;

    public BoulderingZombieEntity(World worldIn) {
        this(EarthEntitys.BOULDERING_ZOMBIE, worldIn);
    }

    public BoulderingZombieEntity(EntityType<? extends BoulderingZombieEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CLIMBING, (byte) 0);
    }

    protected PathNavigator createNavigator(World worldIn) {
        return new ClimberPathNavigator(this, worldIn);
    }


    public static AttributeModifierMap.MutableAttribute createMutableAttribute() {
        return ZombieEntity.func_234342_eQ_().createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.24F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D).createMutableAttribute(Attributes.ARMOR, 3.0D).createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return super.onLivingFall(distance, damageMultiplier * 0.85F);
    }

    public void tick() {
        super.tick();
        Vector3d vector3d2 = this.getLook(1.0F).scale(0.6F);
        this.setClimingBoundingBox(this.getBoundingBox().grow(0.0F, 0.2F, 0.0F).offset(vector3d2));

        if (!this.world.isRemote) {
            if (this.getClimingBoundingBox() != null && !this.world.hasNoCollisions(this.getClimingBoundingBox())) {
                this.setBesideClimbableBlock(this.collidedHorizontally);
            } else if (this.isBesideClimbableBlock()) {
                this.setBesideClimbableBlock(false);
            }
        }
    }

    public AxisAlignedBB getClimingBoundingBox() {
        return climingBoundingBox;
    }

    public void setClimingBoundingBox(AxisAlignedBB climingBoundingBox) {
        this.climingBoundingBox = climingBoundingBox;
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.dataManager.get(CLIMBING);
        if (climbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.dataManager.set(CLIMBING, b0);
    }

    @Override
    public boolean isOnLadder() {
        return isBesideClimbableBlock() || super.isOnLadder();
    }
}
