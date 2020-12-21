package baguchan.earthmobsmod.init;

import baguchan.earthmobsmod.EarthMobsMod;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;

public class EarthTags {
    public static class Fluids {

        public static final ITag.INamedTag<Fluid> MUD_WATER = tag("mud");

        private static ITag.INamedTag<Fluid> tag(String name) {
            return FluidTags.makeWrapperTag(EarthMobsMod.MODID + ":" + name);
        }
    }
}
