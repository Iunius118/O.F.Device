package com.github.iunius118.orefarmingdevice.gametest;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegisterData;

import java.util.Optional;

public class ModGameTestInstanceProvider {
    private final static String PACK_PATH = "ofdevice_mod_game_tests";
    private final static ResourceLocation PACK_ID = OreFarmingDevice.makeId(PACK_PATH);

    public static void addListeners(final IEventBus modEventBus) {
        modEventBus.addListener(ModGameTestInstanceProvider::addProviders);
        modEventBus.addListener(ModGameTestInstanceProvider::addPackFinders);
    }

    private static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var packGenerator = dataGenerator.getBuiltinDatapack(event.includeServer(), PACK_PATH);

        // Add pack metadata
        packGenerator.addProvider(o -> PackMetadataGenerator.forFeaturePack(packOutput, Component.literal("O.F.Device - mod game test instances")));
        // Add mod game test instance provider
        var gameTestInstanceRegister = DeferredRegisterData.create(Registries.TEST_INSTANCE, OreFarmingDevice.MOD_ID);
        OFDeviceLootTableTest.registerTestInstance(gameTestInstanceRegister);
        CobblestoneDeviceTest.registerTestInstance(gameTestInstanceRegister);
        var gameTestInstanceProvider = new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(),
                VanillaRegistries.builder().add(gameTestInstanceRegister), OreFarmingDevice.MOD_ID);
        packGenerator.addProvider(o -> gameTestInstanceProvider);
    }

    private static void addPackFinders(final AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }

        var pack = Pack.readMetaAndCreate(
                new PackLocationInfo(PACK_ID.toString(), Component.literal(PACK_PATH),
                        // This is an optional pack
                        PackSource.create(PackSource.NO_DECORATION, false),
                        // Add KnownPack as this is not an experimental data pack
                        Optional.of(new KnownPack(OreFarmingDevice.MOD_ID, PACK_PATH, "1.0"))),
                new PathPackResources.PathResourcesSupplier(ModList.get().getModFileById(OreFarmingDevice.MOD_ID).getFile().findResource(PACK_PATH)),
                PackType.SERVER_DATA,
                new PackSelectionConfig(false, Pack.Position.BOTTOM, false));

        if (pack != null) {
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }

    private ModGameTestInstanceProvider() {}
}
