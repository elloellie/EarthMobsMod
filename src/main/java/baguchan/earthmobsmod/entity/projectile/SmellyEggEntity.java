package baguchan.earthmobsmod.entity.projectile;

import baguchan.earthmobsmod.entity.CluckShroomEntity;
import baguchan.earthmobsmod.handler.EarthEntitys;
import baguchan.earthmobsmod.handler.EarthItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class SmellyEggEntity extends ProjectileItemEntity {
    public SmellyEggEntity(EntityType<? extends SmellyEggEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public SmellyEggEntity(World worldIn, LivingEntity throwerIn) {
        super(EarthEntitys.SMELLYEGG, throwerIn, worldIn);
    }

    public SmellyEggEntity(World worldIn, double x, double y, double z) {
        super(EarthEntitys.SMELLYEGG, x, y, z, worldIn);
    }

    public SmellyEggEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(EarthEntitys.SMELLYEGG, world);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.posX, this.posY, this.posZ, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    protected Item func_213885_i() {
        return EarthItems.SMELLY_EGG;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (entity instanceof ChickenEntity && !(entity instanceof CluckShroomEntity)) {
                if (!this.world.isRemote) {
                    CluckShroomEntity cluckshroom = EarthEntitys.CLUCKSHROOM.create(this.world);
                    cluckshroom.setLocationAndAngles(((ChickenEntity) entity).posX, ((ChickenEntity) entity).posY, ((ChickenEntity) entity).posZ, ((ChickenEntity) entity).rotationYaw, ((ChickenEntity) entity).rotationPitch);
                    cluckshroom.setNoAI(((ChickenEntity) entity).isAIDisabled());
                    if (this.hasCustomName()) {
                        cluckshroom.setCustomName(((ChickenEntity) entity).getCustomName());
                        cluckshroom.setCustomNameVisible(((ChickenEntity) entity).isCustomNameVisible());
                    }

                    if (((ChickenEntity) entity).isChild()) {
                        cluckshroom.setGrowingAge(((ChickenEntity) entity).getGrowingAge());
                    }

                    this.world.addEntity(cluckshroom);

                    entity.remove();
                }
            } else {
                double i = 0.5;
                entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) 0);
            }
            if (entity instanceof LivingEntity) {
                if (!this.world.isRemote) {
                    ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.NAUSEA, 120));
                }
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}