package com.github.iunius118.orefarmingdevice.data.experimental;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.loot.ModLootTables;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class Experimental1202DataProvider {
    private final static String PACK_PATH = "experimental_1202";
    private final static Identifier PACK_ID = OreFarmingDevice.makeId(PACK_PATH);
    private final static String PACK_NAME = "O.F.Device Experimental 1202";

    private Experimental1202DataProvider() {}

    public static void addProviders(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var lookupProvider = event.getLookupProvider();

        final boolean includesServer = event.includeServer();
        var packGenerator = dataGenerator.getBuiltinDatapack(includesServer, PACK_PATH);

        packGenerator.addProvider((o) -> PackMetadataGenerator.forFeaturePack(packOutput, Component.literal("O.F.Device - experimental data pack 1.20.2")));
        packGenerator.addProvider((o) -> new ExperimentalLootTableProvider(packOutput, lookupProvider));
    }

    public static void addPackFinders(final AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }

        var knownPack = new KnownPack(OreFarmingDevice.MOD_ID, PACK_PATH, "1.0");
        var packInfo = new PackLocationInfo(PACK_ID.toString(), Component.literal(PACK_PATH), PackSource.FEATURE, Optional.of(knownPack));
        var resourcePath = ModList.get().getModFileById(OreFarmingDevice.MOD_ID).getFile().findResource(PACK_PATH);
        var packConfig = new PackSelectionConfig(false, Pack.Position.TOP, false);
        var pack = Pack.readMetaAndCreate(packInfo, new PathPackResources.PathResourcesSupplier(resourcePath), PackType.SERVER_DATA, packConfig);

        if (pack != null) {
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }

    private static class ExperimentalLootTableProvider extends LootTableProvider {
        public ExperimentalLootTableProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup) {
            super(packOutput, Set.of(), VanillaLootTableProvider.create(packOutput, lookup).getTables(), lookup);
        }

        @Override
        public List<LootTableProvider.SubProviderEntry> getTables() {
            return ImmutableList.of(
                    new LootTableProvider.SubProviderEntry(ExperimentalDeviceLootTables::new, LootContextParamSets.EMPTY)
            );
        }

        @Override
        protected void validate(Registry<LootTable> map, ValidationContext validationcontext, ProblemReporter report) {
        }
    }

    private static class ExperimentalDeviceLootTables implements LootTableSubProvider {
        private final HolderLookup.Provider provider;

        public ExperimentalDeviceLootTables(HolderLookup.Provider lookupProvider) {
            provider = lookupProvider;
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
            // OF Device Mod 1
            consumer.accept(ModLootTables.DEVICE_1_DEEP.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            // DEEPSLATE - 3, DEEPSLATE_DIAMOND_ORE + 3
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE).setWeight(941).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_COAL_ORE).setWeight(3))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_COPPER_ORE).setWeight(6))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_LAPIS_ORE).setWeight(4))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_IRON_ORE).setWeight(11))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_GOLD_ORE).setWeight(6))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_REDSTONE_ORE).setWeight(20))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_DIAMOND_ORE).setWeight(9))
                    )
            );

            // OF Device Mod 2
            consumer.accept(ModLootTables.DEVICE_2.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            // STONE - 2, AMETHYST_SHARD + 2
                            .add(LootItem.lootTableItem(Blocks.STONE).setWeight(839).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.COAL_ORE).setWeight(50))
                            .add(LootItem.lootTableItem(Blocks.COPPER_ORE).setWeight(47))
                            .add(LootItem.lootTableItem(Blocks.LAPIS_ORE).setWeight(4))
                            .add(LootItem.lootTableItem(Blocks.IRON_ORE).setWeight(30))
                            .add(LootItem.lootTableItem(Blocks.GOLD_ORE).setWeight(20))
                            .add(LootItem.lootTableItem(Blocks.REDSTONE_ORE).setWeight(5))
                            .add(LootItem.lootTableItem(Blocks.DIAMOND_ORE).setWeight(1))
                            .add(LootItem.lootTableItem(Blocks.EMERALD_ORE).setWeight(2))
                            .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(2))
                    )
            );

            consumer.accept(ModLootTables.DEVICE_2_DEEP.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            // DEEPSLATE - 7, DEEPSLATE_DIAMOND_ORE + 3, AMETHYST_SHARD + 4
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE).setWeight(931).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_COAL_ORE).setWeight(3))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_COPPER_ORE).setWeight(11))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_LAPIS_ORE).setWeight(4))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_IRON_ORE).setWeight(11))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_GOLD_ORE).setWeight(6))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_REDSTONE_ORE).setWeight(20))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_DIAMOND_ORE).setWeight(9))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_EMERALD_ORE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(4))
                    )
            );
        }
    }
}
