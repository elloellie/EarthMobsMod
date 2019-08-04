package baguchan.earthmobsmod.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum EarthParticles {
	FLOWER_POLLEN;

	EarthParticles() {
	}

	@OnlyIn(Dist.CLIENT)
	public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... params) {
		return getFactory().makeParticle(world, x, y, z, velocityX, velocityY, velocityZ, params);
	}

	@OnlyIn(Dist.CLIENT)
	public IEarthParticle getFactory() {
		switch (this) {
			case FLOWER_POLLEN:
				return new FlowerPollenParticle.Factory();
		}
		return new FlowerPollenParticle.Factory();
	}

	public static EarthParticles getDefaultParticle() {
		return FLOWER_POLLEN;
	}

	public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
		if (world.isRemote) {
			spawn(create(world, x, y, z, velocityX, velocityY, velocityZ, parameters));
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void spawn(Particle particle) {
		Minecraft.getInstance().particles.addEffect(particle);
	}

	public static EarthParticles fromId(int id) {
		return values()[MathHelper.clamp(id, 0, values().length - 1)];
	}
}