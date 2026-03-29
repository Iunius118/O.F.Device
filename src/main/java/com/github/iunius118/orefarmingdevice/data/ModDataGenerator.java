package com.github.iunius118.orefarmingdevice.data;

import com.github.iunius118.orefarmingdevice.data.experimental.OFCFeederTRecipeDataProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ModDataGenerator {
    public static void gatherData(GatherDataEvent.Client event) {
        // Data
        event.createProvider(ModBlockTagsProvider::new);
        event.createProvider(ModLootTableProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
        OFCFeederTRecipeDataProvider.addProviders(event);
        // Disable data pack Experimental_1202 since 1.20.2
        // Experimental1202DataProvider.addProviders(event);

        // Assets
        event.createProvider(ModModelProvider::new);
        ModLanguageProvider.addProviders(event);
    }
}
