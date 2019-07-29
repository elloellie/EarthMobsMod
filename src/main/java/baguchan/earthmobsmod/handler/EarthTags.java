package baguchan.earthmobsmod.handler;

import baguchan.earthmobsmod.EarthMobsMod;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class EarthTags {
    public static class Fluids {

        public static final Tag<Fluid> MUD_WATER = tag("mud");

        private static Tag<Fluid> tag(String name) {
            return new FluidTags.Wrapper(new ResourceLocation(EarthMobsMod.MODID, name));
        }
    }
}
