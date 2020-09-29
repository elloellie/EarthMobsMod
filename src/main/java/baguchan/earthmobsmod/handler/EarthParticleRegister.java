package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.client.particle.FlowerPollenParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EarthMobsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EarthParticleRegister {

    @SubscribeEvent
    public static void registerParticleFactory(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(EarthParticles.FLOWER_POLLEN, FlowerPollenParticle.Factory::new);
    }

    @SubscribeEvent
    public static void registerParticles(final RegistryEvent.Register<ParticleType<?>> event) {
        event.getRegistry().register(EarthParticles.FLOWER_POLLEN.setRegistryName("flower_pollen"));
    }
}