package com.github.iunius118.orefarmingdevice;

import com.github.iunius118.orefarmingdevice.client.ClientModEventHandler;
import com.github.iunius118.orefarmingdevice.common.RegisterEventHandler;
import com.github.iunius118.orefarmingdevice.config.OreFarmingDeviceConfig;
import com.github.iunius118.orefarmingdevice.data.ModDataGenerator;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import org.slf4j.Logger;

@Mod(OreFarmingDevice.MOD_ID)
public class OreFarmingDevice {
    public static final String MOD_ID = "orefarmingdevice";
    public static final String MOD_NAME = "O.F.Device";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OreFarmingDevice(IEventBus modEventBus, ModContainer modContainer) {
        // Register mod lifecycle event handlers

        // Register config handlers
        modContainer.registerConfig(ModConfig.Type.SERVER, OreFarmingDeviceConfig.SERVER_SPEC);

        // Register event handlers
        RegisterEventHandler.registerGameObjects(modEventBus);
        modEventBus.addListener(ModDataGenerator::gatherData);
        /* Disable data pack Experimental_1202 since 1.20.2
        // Register optional data pack handlers
        modEventBus.addListener(Experimental1202DataProvider::addPackFinders);
         */

        // Register client-side mod event handler
        if (FMLLoader.getDist().isClient()) {
            modEventBus.register(ClientModEventHandler.class);
        }
    }

    public static ResourceLocation makeId(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
