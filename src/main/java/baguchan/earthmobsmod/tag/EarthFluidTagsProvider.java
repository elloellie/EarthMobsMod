package baguchan.earthmobsmod.tag;

import baguchan.earthmobsmod.handler.EarthFluids;
import baguchan.earthmobsmod.handler.EarthTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;

public class EarthFluidTagsProvider extends FluidTagsProvider {
    public EarthFluidTagsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void registerTags() {
        this.getBuilder(EarthTags.Fluids.MUD_WATER).add(EarthFluids.MUD_WATER, EarthFluids.MUD_WATER_FLOW);
    }

    @Override
    public String getName() {
        return "Earth Fluid Tags";
    }
}