package com.github.iunius118.orefarmingdevice.data;

import com.github.iunius118.orefarmingdevice.loot.OFDeviceLootTables;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider() {
        super(Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModDeviceLootTables::new, LootContextParamSets.EMPTY)
        ));
    }

    private static class ModBlockLootTables extends BlockLootSubProvider {
        private final List<Block> ofDeviceBlocks = Stream.of(
                ModBlocks.DEVICE_0,
                ModBlocks.DEVICE_1,
                ModBlocks.DEVICE_2,
                ModBlocks.COBBLESTONE_DEVICE_0
        ).collect(ImmutableList.toImmutableList());

        public ModBlockLootTables(LootTableSubProvider.Context output) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
        }

        @Override
        protected void generate() {
            ofDeviceBlocks.forEach(b -> add(b, this::createNameableBlockEntityTable));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ofDeviceBlocks;
        }
    }

    private record ModDeviceLootTables(LootTableSubProvider.Context output) implements LootTableSubProvider {
        @Override
        public void run() {
            // OF Device
            output.accept(OFDeviceLootTables.DEVICE_0.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Blocks.STONE).setWeight(892).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.COAL_ORE).setWeight(50))
                            .add(LootItem.lootTableItem(Blocks.COPPER_ORE).setWeight(24))
                            .add(LootItem.lootTableItem(Blocks.LAPIS_ORE).setWeight(4))
                            .add(LootItem.lootTableItem(Blocks.IRON_ORE).setWeight(30))
                    )
            );

            output.accept(OFDeviceLootTables.DEVICE_0_DEEP.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE).setWeight(976).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_COAL_ORE).setWeight(3))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_COPPER_ORE).setWeight(6))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_LAPIS_ORE).setWeight(4))
                            .add(LootItem.lootTableItem(Blocks.DEEPSLATE_IRON_ORE).setWeight(11))
                    )
            );

            output.accept(OFDeviceLootTables.DEVICE_0_NETHER.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Items.NETHER_BRICK).setWeight(960).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.NETHER_QUARTZ_ORE).setWeight(30))
                            .add(LootItem.lootTableItem(Blocks.NETHER_GOLD_ORE).setWeight(10))
                    )
            );

            // OF Device Mod 1
            output.accept(OFDeviceLootTables.DEVICE_1.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Blocks.STONE).setWeight(882).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.COAL_ORE).setWeight(50))
                            .add(LootItem.lootTableItem(Blocks.COPPER_ORE).setWeight(24))
                            .add(LootItem.lootTableItem(Blocks.LAPIS_ORE).setWeight(4))
                            .add(LootItem.lootTableItem(Blocks.IRON_ORE).setWeight(30))
                            .add(LootItem.lootTableItem(Blocks.GOLD_ORE).setWeight(4))
                            .add(LootItem.lootTableItem(Blocks.REDSTONE_ORE).setWeight(5))
                            .add(LootItem.lootTableItem(Blocks.DIAMOND_ORE).setWeight(1))
                    )
            );

            output.accept(OFDeviceLootTables.DEVICE_1_DEEP.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
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

            output.accept(OFDeviceLootTables.DEVICE_1_NETHER.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Items.NETHER_BRICK).setWeight(960).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.NETHER_QUARTZ_ORE).setWeight(30))
                            .add(LootItem.lootTableItem(Blocks.NETHER_GOLD_ORE).setWeight(10))
                    )
            );

            // OF Device Mod 2
            output.accept(OFDeviceLootTables.DEVICE_2.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
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

            output.accept(OFDeviceLootTables.DEVICE_2_DEEP.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
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

            output.accept(OFDeviceLootTables.DEVICE_2_NETHER.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Items.NETHER_BRICK).setWeight(959).setQuality(-240))
                            .add(LootItem.lootTableItem(Blocks.NETHER_QUARTZ_ORE).setWeight(30))
                            .add(LootItem.lootTableItem(Blocks.NETHER_GOLD_ORE).setWeight(10))
                            .add(LootItem.lootTableItem(Blocks.ANCIENT_DEBRIS).setWeight(1))
                    )
            );

            // OF Device Mod 2 with OF C Feeder T
            output.accept(OFDeviceLootTables.DEVICE_2_FEED_TNT.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Blocks.COBBLESTONE).setWeight(839).setQuality(-240)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Items.COAL).setWeight(50)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Blocks.RAW_COPPER_BLOCK).setWeight(47))
                            .add(LootItem.lootTableItem(Blocks.LAPIS_BLOCK).setWeight(4)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                            .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(30)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(20)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Blocks.REDSTONE_BLOCK).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(2)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                    )
            );

            output.accept(OFDeviceLootTables.DEVICE_2_FEED_TNT_DEEP.getResourceKey(),
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Blocks.COBBLED_DEEPSLATE).setWeight(931).setQuality(-240)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Items.COAL).setWeight(3)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Blocks.RAW_COPPER_BLOCK).setWeight(11))
                            .add(LootItem.lootTableItem(Blocks.LAPIS_BLOCK).setWeight(4)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                            .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(11)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(6)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Blocks.REDSTONE_BLOCK).setWeight(20)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(9)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                            .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(4)
                                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(3))))
                    )
            );
        }
    }
}
