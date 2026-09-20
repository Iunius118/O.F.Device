package com.github.iunius118.orefarmingdevice.data;

import com.github.iunius118.orefarmingdevice.data.experimental.OFCFeederTRecipeDataProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ModDataGenerator {
    public static void gatherData(GatherDataEvent.Client event) {
        // Data
        var builder = new RegistrySetBuilder()
        // Register reloadable data providers
                .add(Registries.LOOT_TABLE, new ModLootTableProvider())
                .add(ModRecipeProvider.create());
        event.createReloadableRegistryObjects(builder);
        event.createProvider(ModBlockTagsProvider::new);
        OFCFeederTRecipeDataProvider.addProviders(event);
        // Disable data pack Experimental_1202 since 1.20.2
        // Experimental1202DataProvider.addProviders(event);

        // Assets
        event.createProvider(ModModelProvider::new);
        ModLanguageProvider.addProviders(event);
    }
}
