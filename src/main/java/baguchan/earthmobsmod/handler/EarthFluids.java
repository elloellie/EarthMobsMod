package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.earthmobsmod.fluid.FluidMudWater;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class EarthFluids {

    public static final FlowingFluid MUD_WATER = registerFluid("mud", new FluidMudWater.Source());
    public static final FlowingFluid MUD_WATER_FLOW = registerFluid("mud_flow", new FluidMudWater.Flowing());

    private static <T extends Fluid> T registerFluid(String key, T p_215710_1_) {

        ResourceLocation location = new ResourceLocation(EarthMobsMod.MODID + ":" + key);

        p_215710_1_.setRegistryName(location);

        return p_215710_1_;
    }

    public static void register(IForgeRegistry<Fluid> registry) {
        registry.register(MUD_WATER);
        registry.register(MUD_WATER_FLOW);
    }
}