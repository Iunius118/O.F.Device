package com.github.iunius118.orefarmingdevice;

import com.github.iunius118.orefarmingdevice.client.ClientModEventHandler;
import com.github.iunius118.orefarmingdevice.common.RegisterEventHandler;
import com.github.iunius118.orefarmingdevice.config.OreFarmingDeviceConfig;
import com.github.iunius118.orefarmingdevice.data.ModDataGenerator;
import com.github.iunius118.orefarmingdevice.gametest.ModGameTest;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(OreFarmingDevice.MOD_ID)
public class OreFarmingDevice {
    public static final String MOD_ID = "orefarmingdevice";
    public static final String MOD_NAME = "O.F.Device";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OreFarmingDevice(FMLJavaModLoadingContext context) {
        final var modBusGroup = context.getModBusGroup();
        // Register mod lifecycle event handlers

        // Register config handlers
        context.registerConfig(ModConfig.Type.SERVER, OreFarmingDeviceConfig.SERVER_SPEC);

        // Register event handlers
        RegisterEventHandler.registerGameObjects(modBusGroup);
        GatherDataEvent.getBus(modBusGroup).addListener(ModDataGenerator::gatherData);
        /* Disable data pack Experimental_1202 since 1.20.2
        // Register optional data pack handlers
        AddPackFindersEvent.getBus(modBusGroup).addListener(Experimental1202DataProvider::addPackFinders);
        //*/

        // Register game test handlers
        ModGameTest.register(modBusGroup);

        // Register client-side mod event handler
        if (FMLEnvironment.dist.isClient()) {
            FMLClientSetupEvent.getBus(modBusGroup).addListener(ClientModEventHandler::setup);
        }
    }

    public static ResourceLocation makeId(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
