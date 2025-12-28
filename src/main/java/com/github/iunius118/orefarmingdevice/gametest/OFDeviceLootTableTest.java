package com.github.iunius118.orefarmingdevice.gametest;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.config.OreFarmingDeviceConfig;
import com.github.iunius118.orefarmingdevice.loot.ModLootTables;
import com.github.iunius118.orefarmingdevice.loot.OFDeviceLootCondition;
import com.github.iunius118.orefarmingdevice.world.item.ModItems;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import com.github.iunius118.orefarmingdevice.world.level.block.OFDeviceBlock;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceBlockEntity;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class OFDeviceLootTableTest {
    private static final List<ResourceKey<Consumer<GameTestHelper>>> TEST_FUNCTIONS = new ArrayList<>();
    public static final int LOOT_TABLE_LOOKUP_TEST_MAX_TICKS = 810;

    public static void register(IEventBus modEventBus) {
        // Register test functions
        var testFunctionRegister = DeferredRegister.create(Registries.TEST_FUNCTION, OreFarmingDevice.MOD_ID);
        TEST_FUNCTIONS.clear();

        IntStream.rangeClosed(0, 20).forEach(i -> {
            String name = "loot_table_%02d".formatted(i);
            TEST_FUNCTIONS.add(testFunctionRegister.register(name, () -> getLootTableLookupTest(i)).getKey());
        });

        testFunctionRegister.register(modEventBus);
    }

    private static Consumer<GameTestHelper> getLootTableLookupTest(int index) {
        return helper -> testLootTableLookup(helper, index);
    }

    /**
     * Test that {@link ModLootTables} returns the correct loot table for the specified device and coordinates.
     */
    private static void testLootTableLookup(GameTestHelper helper, int index) {
        ModLootTables lootTable = ModLootTables.values()[index];
        OFDeviceLootCondition lootCondition = lootTable.getLootCondition();
        helper.assertFalse(lootCondition == OFDeviceLootCondition.NOT_APPLICABLE,
                Component.literal("Device loot condition was not found."));

        // Get device block pos
        BlockPos absPos = helper.absolutePos(BlockPos.ZERO);
        int deviceAbsY = lootCondition.isDeviceInShallowLayer() ? 1 : 0;
        BlockPos devicePos = BlockPos.ZERO.offset(1, deviceAbsY - absPos.getY(), 1);

        // Place device
        helper.destroyBlock(devicePos);
        OFDeviceType type = lootCondition.getType();
        OFDeviceBlock deviceBlock = switch(type) {
            case MOD_0 -> ModBlocks.DEVICE_0;
            case MOD_1 -> ModBlocks.DEVICE_1;
            case MOD_2 -> ModBlocks.DEVICE_2;
        };
        helper.setBlock(devicePos, deviceBlock);
        helper.assertTrue(helper.getBlockState(devicePos).is(deviceBlock),
                Component.literal("Failed to place device block."));
        OFDeviceBlockEntity device = helper.getBlockEntity(devicePos, OFDeviceBlockEntity.class);

        // Set 8 material items to device
        device.setItem(0, new ItemStack(lootTable.getMaterial().getItem(), 8));
        // Set fuel for 4 material items to device
        device.setItem(1, new ItemStack(Items.OAK_SIGN, 4));

        // Test
        final int expectedProductCount = (int) ((lootTable.getMaterial().is(ModItems.COBBLESTONE_FEEDER) ? 2 : 4)
                / OreFarmingDeviceConfig.SERVER.getDeviceProcessingSpeed().getMultiplier());
        final int tick = device.getTotalProcessingTime() * expectedProductCount;
        helper.runAfterDelay(tick, () -> {
                    // Check products
                    helper.assertTrue(device.getLastProcessedLootTable() == lootTable,
                            Component.literal("Loot table did not match: exp = %s, act = %s."
                                    .formatted(lootTable, device.getLastProcessedLootTable())));
                    helper.assertTrue(device.getProductCount() == expectedProductCount,
                            Component.literal("Product count was incorrect: exp = %d, act = %d"
                                    .formatted(expectedProductCount, device.getProductCount())));

                    // Clean up if successful
                    device.setItem(0, ItemStack.EMPTY);
                    device.setItem(1, ItemStack.EMPTY);
                    device.setItem(2, ItemStack.EMPTY);
                    helper.destroyBlock(devicePos);
                    helper.succeed();
                }
        );
    }

    public static void registerTestInstance(RegisterGameTestsEvent event, Holder<TestEnvironmentDefinition> testEnvironment) {
        // Register loot table lookup test instances
        IntStream.range(0, TEST_FUNCTIONS.size()).forEach(index ->
                event.registerTest(OreFarmingDevice.makeId("loot_table_%s".formatted(ModLootTables.values()[index].name().toLowerCase())),
                        new FunctionGameTestInstance(TEST_FUNCTIONS.get(index),
                                new TestData<>(testEnvironment, ModGameTest.DEFAULT_STRUCTURE, LOOT_TABLE_LOOKUP_TEST_MAX_TICKS, 0, true))));
    }
}
