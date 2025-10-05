package com.github.iunius118.orefarmingdevice.data;

import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.data.event.GatherDataEvent;

public final class ModDataGenerator {
    public static void gatherData(GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();

        // Server
        boolean includesServer = event.includeServer();
        dataGenerator.addProvider(includesServer, new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(includesServer, new ModLootTableProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(includesServer, new ModRecipeProvider.Runner(packOutput, lookupProvider));
        // Disable data pack Experimental_1202 since 1.20.2
        // Experimental1202DataProvider.addProviders(event);

        // Client
        boolean includesClient = event.includeClient();
        dataGenerator.addProvider(includesClient, new PackMetadataGenerator(packOutput)
                .add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(
                        Component.literal("${mod_id} resources"),
                        DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange()
                ))
        );
        dataGenerator.addProvider(includesClient, new ModModelProvider(packOutput));
        ModLanguageProvider.addProviders(includesClient, dataGenerator);
    }
}
