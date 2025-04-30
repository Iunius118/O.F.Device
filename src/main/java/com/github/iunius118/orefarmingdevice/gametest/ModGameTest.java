package com.github.iunius118.orefarmingdevice.gametest;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModGameTest {
    static final ResourceLocation DEFAULT_STRUCTURE = ResourceLocation.fromNamespaceAndPath("forge", "empty3x3x3");

    public static void register(IEventBus modEventBus) {
        // Register game test functions
        OFDeviceLootTableTest.register(modEventBus);
        CobblestoneDeviceTest.register(modEventBus);

        // Generate and register game test instances in ModGameTestInstanceProvider
        ModGameTestInstanceProvider.addListeners(modEventBus);
    }
}
