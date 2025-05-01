package com.github.iunius118.orefarmingdevice.gametest;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

import java.util.List;

public class ModGameTest {
    public static final ResourceLocation DEFAULT_STRUCTURE = OreFarmingDevice.makeId("empty3x3x3");

    public static void register(IEventBus modEventBus) {
        // Register game test functions
        OFDeviceLootTableTest.register(modEventBus);
        CobblestoneDeviceTest.register(modEventBus);

        // Register game test instances on RegisterGameTestsEvent
        modEventBus.addListener(ModGameTest::onRegisterGameTestsEvent);
    }

    public static void onRegisterGameTestsEvent(final RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition> testEnvironment = event.registerEnvironment(
                OreFarmingDevice.makeId("default"), new TestEnvironmentDefinition.AllOf(List.of()));
        // Register game test instances
        OFDeviceLootTableTest.registerTestInstance(event, testEnvironment);
        CobblestoneDeviceTest.registerTestInstance(event, testEnvironment);
    }
}
