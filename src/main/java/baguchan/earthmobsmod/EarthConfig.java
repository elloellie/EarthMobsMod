package baguchan.earthmobsmod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = EarthMobsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EarthConfig {
    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    public static boolean spawnEarthMobsMoobloom;

    static {
        Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void bakeConfig() {
        spawnEarthMobsMoobloom = COMMON.spawnEarthMobsMoobloom.get();
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == EarthConfig.COMMON_SPEC) {
            bakeConfig();
        }
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue spawnEarthMobsMoobloom;

        public Common(ForgeConfigSpec.Builder builder) {
            spawnEarthMobsMoobloom = builder
                    .translation(EarthMobsMod.MODID + ".config.spawnEarthMobsMoobloom")
                    .define("make spawn EarthMobsMoobloom", true);
        }
    }

}