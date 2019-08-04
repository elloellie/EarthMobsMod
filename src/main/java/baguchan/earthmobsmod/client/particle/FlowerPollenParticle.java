package baguchan.earthmobsmod.client.particle;

import baguchan.earthmobsmod.EarthMobsMod;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlowerPollenParticle extends EarthParticle {
	private int currentFlame = 1;
	private final double portalPosX;
	private final double portalPosY;
	private final double portalPosZ;

	public FlowerPollenParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int i) {
		super(world, x, y, z, xSpeed, ySpeed, zSpeed);

		this.motionX = xSpeed;
		this.motionY = ySpeed;
		this.motionZ = zSpeed;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.portalPosX = this.posX;
		this.portalPosY = this.posY;
		this.portalPosZ = this.posZ;

		float f1 = 1.0F - (float) (Math.random() * (double) 0.3F);

		this.particleRed = f1;
		this.particleGreen = f1;
		this.particleBlue = f1;
		this.particleAlpha = 1f;

		this.particleScale *= 0.6F;
		int i2 = (int) (8.0D / (Math.random() * 0.8D + 0.3D));

		this.maxAge = (int) (Math.random() * 10.0D) + 40;
		this.particleGravity = 0f;
	}

	@Override
	public void onPreRender(BufferBuilder buffer, ActiveRenderInfo activeInfo, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		Entity entity = activeInfo.getRenderViewEntity();
		int ageFlame = (this.age / this.maxAge) * 3;

		if (ageFlame > 1) {
			this.currentFlame = (this.age / this.maxAge) * 3;
		} else {
			this.currentFlame = 1;
		}
	}

	public void move(double x, double y, double z) {
		this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
		this.resetPositionToBB();
	}

	public float getScale(float p_217561_1_) {
		float f = ((float) this.age + p_217561_1_) / (float) this.maxAge;
		f = 1.0F - f;
		f = f * f;
		f = 1.0F - f;
		return this.particleScale * f;
	}

	public int getBrightnessForRender(float partialTick) {
		int i = super.getBrightnessForRender(partialTick);
		float f = (float) this.age / (float) this.maxAge;
		f = f * f;
		f = f * f;
		int j = i & 255;
		int k = i >> 16 & 255;
		k = k + (int) (f * 15.0F * 16.0F);
		if (k > 240) {
			k = 240;
		}

		return j | k << 16;
	}

	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		} else {
			float f = (float) this.age / (float) this.maxAge;
			float f1 = -f + f * f * 2.0F;
			float f2 = 1.0F - f1;
			this.posX = this.portalPosX + this.motionX * (double) f2;
			this.posY = this.portalPosY + this.motionY * (double) f2 + (double) (1.0F - f);
			this.posZ = this.portalPosZ + this.motionZ * (double) f2;
		}
	}

	@Override
	ResourceLocation getTexture() {
		return new ResourceLocation(EarthMobsMod.MODID, "textures/particles/flowerpollen/pollen" + "_" + currentFlame + ".png");
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IEarthParticle {

		@Override
		public Particle makeParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... params) {
			return new FlowerPollenParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, params.length > 0 ? params[0] : 0xffffff);
		}

	}
}
