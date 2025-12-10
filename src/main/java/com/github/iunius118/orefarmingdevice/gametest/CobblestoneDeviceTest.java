package com.github.iunius118.orefarmingdevice.gametest;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.level.block.CobblestoneDeviceBlock;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.CobblestoneDeviceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class CobblestoneDeviceTest {
    private static final List<ResourceKey<Consumer<GameTestHelper>>> TEST_FUNCTIONS = new ArrayList<>();
    private static final int COBBLESTONE_DEVICE_TEST_MAX_TICKS = 224;

    public static void register(IEventBus modEventBus) {
        // Register test functions
        var testFunctionRegister = DeferredRegister.create(Registries.TEST_FUNCTION, OreFarmingDevice.MOD_ID);
        TEST_FUNCTIONS.clear();

        TEST_FUNCTIONS.add(testFunctionRegister.register("c_device_shallow", () -> getCobblestoneDeviceTest(true)).getKey());
        TEST_FUNCTIONS.add(testFunctionRegister.register("c_device_deep", () -> getCobblestoneDeviceTest(false)).getKey());

        testFunctionRegister.register(modEventBus);
    }

    private static Consumer<GameTestHelper> getCobblestoneDeviceTest(boolean isInShallowLayer) {
        return helper -> testCobblestoneDevice(helper, isInShallowLayer);
    }

    /**
     * Test that OF C Device generates the correct products.
     */
    private static void testCobblestoneDevice(GameTestHelper helper, boolean isInShallowLayer) {
        final CobblestoneDeviceBlock deviceBlock = ModBlocks.COBBLESTONE_DEVICE_0;
        final Item cobblestone = isInShallowLayer ? Items.COBBLESTONE : Items.COBBLED_DEEPSLATE;

        // Get device block pos
        BlockPos absPos = helper.absolutePos(BlockPos.ZERO);
        int deviceAbsY = isInShallowLayer ? 1 : 0;
        BlockPos devicePos = BlockPos.ZERO.offset(1, deviceAbsY - absPos.getY(), 1);

        // Place device
        helper.destroyBlock(devicePos);
        helper.setBlock(devicePos, deviceBlock);
        helper.assertTrue(helper.getBlockState(devicePos).is(deviceBlock),
                Component.literal("Failed to place device block."));
        CobblestoneDeviceBlockEntity device = helper.getBlockEntity(devicePos, CobblestoneDeviceBlockEntity.class);

        // A hopper below the device
        BlockPos hopperPos = devicePos.below();

        // Test 1: Generate cobblestone
        final int expectedProductCountTest1 = 3;
        final int tickTest1 = deviceBlock.type.getIntervalTicks() * expectedProductCountTest1;
        helper.runAfterDelay(tickTest1, () -> {
                    // Check products in the device
                    final ItemStack product = device.getItem(0);
                    helper.assertTrue(product.is(cobblestone),
                            Component.literal("[Test 1] Product did not match: exp = %s, act = %s."
                                    .formatted(cobblestone, product.getItem())));
                    helper.assertTrue(product.getCount() == expectedProductCountTest1,
                            Component.literal("[Test 1] Product count was incorrect: exp = %d, act = %d"
                                    .formatted(expectedProductCountTest1, product.getCount())));

                    // Prepare for Test 2
                    device.setItemForGameTest(0, new ItemStack(cobblestone, cobblestone.getDefaultMaxStackSize() - expectedProductCountTest1));
                }
        );

        // Test 2: Stop generating cobblestone
        final int expectedProductCountTest2 = cobblestone.getDefaultMaxStackSize();
        final int tickTest2 = tickTest1 + deviceBlock.type.getIntervalTicks() * (expectedProductCountTest1 + 1);
        helper.runAfterDelay(tickTest2, () -> {
                    // Check products in the device
                    final ItemStack product = device.getItem(0);
                    helper.assertTrue(product.getCount() == expectedProductCountTest2,
                            Component.literal("[Test 2] Product count was incorrect: exp = %d, act = %d"
                                    .formatted(expectedProductCountTest2, product.getCount())));

                    // Prepare for Test 3
                    device.setItemForGameTest(0, new ItemStack(cobblestone, cobblestone.getDefaultMaxStackSize() - expectedProductCountTest1));
                    helper.setBlock(hopperPos, Blocks.HOPPER);
                    helper.assertTrue(helper.getBlockState(hopperPos).is(Blocks.HOPPER),
                            Component.literal("[Pre Test 3] Failed to place hopper block."));

                    HopperBlockEntity hopper = helper.getBlockEntity(hopperPos, HopperBlockEntity.class);
                    hopper.setItem(0, new ItemStack(cobblestone, cobblestone.getDefaultMaxStackSize() - expectedProductCountTest1));
                    IntStream.range(1, 5).forEach(i -> hopper.setItem(i, new ItemStack(Items.STICK)));
                }
        );

        // Test 3: Hopper
        final int expectedProductCountTest3 = cobblestone.getDefaultMaxStackSize();
        final int tickTest3 = tickTest2 + deviceBlock.type.getIntervalTicks() * expectedProductCountTest1;
        helper.runAfterDelay(tickTest3, () -> {
                    // Check products in the hopper
                    HopperBlockEntity hopper = helper.getBlockEntity(hopperPos, HopperBlockEntity.class);
                    final ItemStack product = hopper.getItem(0);
                    helper.assertTrue(product.getCount() == expectedProductCountTest3,
                            Component.literal("[Test 3] Product count was incorrect: exp = %d, act = %d"
                                    .formatted(expectedProductCountTest3, product.getCount())));

                }
        );

        // Test 4: Stop generating cobblestone with hopper
        final int expectedProductCountTest4 = cobblestone.getDefaultMaxStackSize();
        final int tickTest4 = tickTest3 + deviceBlock.type.getIntervalTicks() * (expectedProductCountTest1 + 1);
        helper.runAfterDelay(tickTest4, () -> {
                    // Check products in the device
                    final ItemStack product = device.getItem(0);
                    helper.assertTrue(product.getCount() == expectedProductCountTest4,
                            Component.literal("[Test 4] Product count was incorrect: exp = %d, act = %d"
                                    .formatted(expectedProductCountTest4, product.getCount())));

                    // Clean up if successful
                    device.setItemForGameTest(0, ItemStack.EMPTY);
                    helper.destroyBlock(devicePos);

                    HopperBlockEntity hopper = helper.getBlockEntity(hopperPos, HopperBlockEntity.class);
                    IntStream.range(0, 5).forEach(i -> hopper.setItem(i, ItemStack.EMPTY));

                    helper.destroyBlock(hopperPos);
                    helper.succeed();
                }
        );
    }

    public static void registerTestInstance(RegisterGameTestsEvent event, Holder<TestEnvironmentDefinition> testEnvironment) {
        // Register cobblestone device test instances
        TEST_FUNCTIONS.forEach(key ->
                event.registerTest(key.identifier(),
                        new FunctionGameTestInstance(key,
                                new TestData<>(testEnvironment, ModGameTest.DEFAULT_STRUCTURE, COBBLESTONE_DEVICE_TEST_MAX_TICKS, 0, true))));
    }
}
