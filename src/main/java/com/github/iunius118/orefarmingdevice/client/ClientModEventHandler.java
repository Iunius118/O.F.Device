package com.github.iunius118.orefarmingdevice.client;

import com.github.iunius118.orefarmingdevice.client.gui.CobblestoneDeviceScreen;
import com.github.iunius118.orefarmingdevice.client.gui.OFDeviceScreen;
import com.github.iunius118.orefarmingdevice.inventory.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientModEventHandler {
    public static void setup(FMLCommonSetupEvent event) {
        MenuScreens.register(ModMenuTypes.DEVICE, OFDeviceScreen::new);
        MenuScreens.register(ModMenuTypes.COBBLESTONE_DEVICE, CobblestoneDeviceScreen::new);
    }
}
