package com.github.iunius118.orefarmingdevice.gametest;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.level.block.CobblestoneDeviceBlock;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.CobblestoneDeviceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.DeferredRegisterData;

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
        helper.assertTrue(helper.getBlockState(devicePos).is(deviceBlock), "Failed to place device block.");
        CobblestoneDeviceBlockEntity device = helper.getBlockEntity(devicePos, CobblestoneDeviceBlockEntity.class);

        // A hopper below the device
        BlockPos hopperPos = devicePos.below();

        // Test 1: Generate cobblestone
        final int expectedProductCountTest1 = 3;
        final int tickTest1 = deviceBlock.type.getIntervalTicks() * expectedProductCountTest1;
        helper.runAfterDelay(tickTest1, () -> {
                    // Check products in the device
                    final ItemStack product = device.getItem(0);
                    helper.assertTrue(product.is(cobblestone), "[Test 1] Product did not match: exp = %s, act = %s.".formatted(cobblestone, product.getItem()));
                    helper.assertTrue(product.getCount() == expectedProductCountTest1, "[Test 1] Product count was incorrect: exp = %d, act = %d".formatted(expectedProductCountTest1, product.getCount()));

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
                    helper.assertTrue(product.getCount() == expectedProductCountTest2, "[Test 2] Product count was incorrect: exp = %d, act = %d".formatted(expectedProductCountTest2, product.getCount()));

                    // Prepare for Test 3
                    device.setItemForGameTest(0, new ItemStack(cobblestone, cobblestone.getDefaultMaxStackSize() - expectedProductCountTest1));
                    helper.setBlock(hopperPos, Blocks.HOPPER);
                    helper.assertTrue(helper.getBlockState(hopperPos).is(Blocks.HOPPER), "[Pre Test 3] Failed to place hopper block.");

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
                    helper.assertTrue(product.getCount() == expectedProductCountTest3, "[Test 3] Product count was incorrect: exp = %d, act = %d".formatted(expectedProductCountTest3, product.getCount()));

                }
        );

        // Test 4: Stop generating cobblestone with hopper
        final int expectedProductCountTest4 = cobblestone.getDefaultMaxStackSize();
        final int tickTest4 = tickTest3 + deviceBlock.type.getIntervalTicks() * (expectedProductCountTest1 + 1);
        helper.runAfterDelay(tickTest4, () -> {
                    // Check products in the device
                    final ItemStack product = device.getItem(0);
                    helper.assertTrue(product.getCount() == expectedProductCountTest4, "[Test 4] Product count was incorrect: exp = %d, act = %d".formatted(expectedProductCountTest4, product.getCount()));

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

    public static void registerTestInstance(DeferredRegisterData<GameTestInstance> register) {
        // Register cobblestone device test instances
        TEST_FUNCTIONS.forEach(key ->
                register.register(key.location().getPath(), ctx ->
                        getCobblestoneDeviceTestInstance(key, ctx.lookup(Registries.TEST_ENVIRONMENT)))
        );
    }

    private static FunctionGameTestInstance getCobblestoneDeviceTestInstance(ResourceKey<Consumer<GameTestHelper>> testFunction,
                                                                        HolderGetter<TestEnvironmentDefinition> testEnvGetter) {
        return new FunctionGameTestInstance(testFunction,
                new TestData<>(testEnvGetter.getOrThrow(GameTestEnvironments.DEFAULT_KEY),
                        ModGameTest.DEFAULT_STRUCTURE, COBBLESTONE_DEVICE_TEST_MAX_TICKS, 0, true));
    }
}
