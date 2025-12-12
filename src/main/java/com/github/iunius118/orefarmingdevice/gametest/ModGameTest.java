package com.github.iunius118.orefarmingdevice.gametest;

import net.minecraft.resources.Identifier;
import net.minecraftforge.eventbus.api.bus.BusGroup;

/**
 * Game test registration class for Ore Farming Device mod.
 *
 * <pre>
 *     /datapack enable "orefarmingdevice:ofdevice_mod_game_tests"
 *     /reload
 *     /test runmultiple orefarmingdevice:*
 * </pre>
 */
public class ModGameTest {
    static final Identifier DEFAULT_STRUCTURE = Identifier.fromNamespaceAndPath("forge", "empty3x3x3");

    public static void register(BusGroup modBusGroup) {
        // Register game test functions
        OFDeviceLootTableTest.register(modBusGroup);
        CobblestoneDeviceTest.register(modBusGroup);

        // Generate and register game test instances in ModGameTestInstanceProvider
        ModGameTestInstanceProvider.addListeners(modBusGroup);
    }
}
