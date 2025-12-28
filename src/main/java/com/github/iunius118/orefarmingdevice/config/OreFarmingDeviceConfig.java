package com.github.iunius118.orefarmingdevice.config;


import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceProcessingSpeed;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OreFarmingDeviceConfig {
    public static class Server {
        public final ModConfigSpec.BooleanValue isCobblestoneFeederAvailable;
        public final ModConfigSpec.BooleanValue canAccelerateProcessingSpeedByMod;
        public final ModConfigSpec.BooleanValue canIncreaseFuelConsumptionByMod;
        public final ModConfigSpec.BooleanValue isFarmingEfficiencyEnabled;
        public final ModConfigSpec.EnumValue<OFDeviceProcessingSpeed> deviceProcessingSpeed;

        Server(ModConfigSpec.Builder builder) {
            builder.comment("O.F.Device's game server-side settings.").push("server");

            isCobblestoneFeederAvailable = builder
                    .comment("===========================================================",
                            "isCobblestoneFeederAvailable as boolean",
                            "  Whether OF C Feeders are available for devices.",
                            "  Default: true")
                    .define("isCobblestoneFeederAvailable", true);

            isFarmingEfficiencyEnabled = builder
                    .comment("===========================================================",
                            "enableFarmingEfficiency as boolean",
                            "  Whether mobs around device increase farming efficiency of device.",
                            "  Default: true")
                    .define("enableFarmingEfficiency", true);

            canAccelerateProcessingSpeedByMod = builder
                    .comment("===========================================================",
                            "accelerateProcessingSpeedByMod as boolean",
                            "  Whether to accelerate processing speed of device by modification.",
                            "         Production time       ",
                            "        (Seconds/Product)      ",
                            "           Mod 0  Mod 1  Mod 2 ",
                            "    true    10.0    5.0    2.5 ",
                            "    false   10.0   10.0   10.0 ",
                            "  Default: true")
                    .define("accelerateProcessingSpeedByMod", true);

            canIncreaseFuelConsumptionByMod = builder
                    .comment("===========================================================",
                            "increaseFuelConsumptionByMod as boolean",
                            "  Whether to increase fuel consumption of device by modification.",
                            "         Fuel Consumption      ",
                            "    Ratios of Devices to Mod 0 ",
                            "           Mod 0  Mod 1  Mod 2 ",
                            "    true     1      2      4   ",
                            "    false    1      1      1   ",
                            "  Default: true")
                    .define("increaseFuelConsumptionByMod", true);

            deviceProcessingSpeed = builder
                    .comment("===========================================================",
                            "deviceProcessingSpeed as a string of \"HALF\", \"NORMAL\", or \"DOUBLE\"",
                            "  Processing speed multiplier of OF Devices.",
                            "  Options: HALF (x0.5), NORMAL (x1), DOUBLE (x2)",
                            "  Default: \"NORMAL\"")
                    .defineEnum("deviceProcessingSpeed", OFDeviceProcessingSpeed.NORMAL);

            builder.pop();
        }

        public boolean isCobblestoneFeederAvailable() {
            return SERVER_SPEC.isLoaded() ? isCobblestoneFeederAvailable.get() : isCobblestoneFeederAvailable.getDefault();
        }

        public boolean canAccelerateProcessingSpeedByMod() {
            return SERVER_SPEC.isLoaded() ? canAccelerateProcessingSpeedByMod.get() : canAccelerateProcessingSpeedByMod.getDefault();
        }

        public boolean canIncreaseFuelConsumptionByMod() {
            return SERVER_SPEC.isLoaded() ? canIncreaseFuelConsumptionByMod.get() : canIncreaseFuelConsumptionByMod.getDefault();
        }

        public boolean isFarmingEfficiencyEnabled() {
            return SERVER_SPEC.isLoaded() ? isFarmingEfficiencyEnabled.get() : isFarmingEfficiencyEnabled.getDefault();
        }

        public OFDeviceProcessingSpeed getDeviceProcessingSpeed() {
            return SERVER_SPEC.isLoaded() ? deviceProcessingSpeed.get() : deviceProcessingSpeed.getDefault();
        }
    }

    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    static {
        final Pair<Server, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }
}
