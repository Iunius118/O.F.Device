package com.github.iunius118.orefarmingdevice.loot;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.config.OreFarmingDeviceConfig;
import com.github.iunius118.orefarmingdevice.world.item.CobblestoneFeederItem;
import com.github.iunius118.orefarmingdevice.world.item.ModItems;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceBlockEntity;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public enum ModLootTables {
    DEVICE_0("device_0", Blocks.COBBLESTONE, OFDeviceLootCondition.is(OFDeviceType.MOD_0)),
    DEVICE_0_DEEP("device_0_d", Blocks.COBBLED_DEEPSLATE, OFDeviceLootCondition.is(OFDeviceType.MOD_0)),
    DEVICE_0_NETHER("device_0_n", Blocks.NETHERRACK, OFDeviceLootCondition.is(OFDeviceType.MOD_0)),
    DEVICE_0_FEED("device_0", ModItems.COBBLESTONE_FEEDER, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_0_IN_SHALLOW_LAYER)),
    DEVICE_0_FEED_DEEP("device_0_d", ModItems.COBBLESTONE_FEEDER, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_0_IN_DEEP_LAYER)),
    DEVICE_0_FEED_2("device_0", ModItems.COBBLESTONE_FEEDER_2, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_0_IN_SHALLOW_LAYER)),
    DEVICE_0_FEED_2_DEEP("device_0_d", ModItems.COBBLESTONE_FEEDER_2, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_0_IN_DEEP_LAYER)),

    DEVICE_1("device_1", Blocks.COBBLESTONE, OFDeviceLootCondition.is(OFDeviceType.MOD_1)),
    DEVICE_1_DEEP("device_1_d", Blocks.COBBLED_DEEPSLATE, OFDeviceLootCondition.is(OFDeviceType.MOD_1)),
    DEVICE_1_NETHER("device_1_n", Blocks.NETHERRACK, OFDeviceLootCondition.is(OFDeviceType.MOD_1)),
    DEVICE_1_FEED("device_1", ModItems.COBBLESTONE_FEEDER, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_1_IN_SHALLOW_LAYER)),
    DEVICE_1_FEED_DEEP("device_1_d", ModItems.COBBLESTONE_FEEDER, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_1_IN_DEEP_LAYER)),
    DEVICE_1_FEED_2("device_1", ModItems.COBBLESTONE_FEEDER_2, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_1_IN_SHALLOW_LAYER)),
    DEVICE_1_FEED_2_DEEP("device_1_d", ModItems.COBBLESTONE_FEEDER_2, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_1_IN_DEEP_LAYER)),

    DEVICE_2("device_2", Blocks.COBBLESTONE, OFDeviceLootCondition.is(OFDeviceType.MOD_2)),
    DEVICE_2_DEEP("device_2_d", Blocks.COBBLED_DEEPSLATE, OFDeviceLootCondition.is(OFDeviceType.MOD_2)),
    DEVICE_2_NETHER("device_2_n", Blocks.NETHERRACK, OFDeviceLootCondition.is(OFDeviceType.MOD_2)),
    DEVICE_2_FEED("device_2", ModItems.COBBLESTONE_FEEDER, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_2_IN_SHALLOW_LAYER)),
    DEVICE_2_FEED_DEEP("device_2_d", ModItems.COBBLESTONE_FEEDER, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_2_IN_DEEP_LAYER)),
    DEVICE_2_FEED_2("device_2", ModItems.COBBLESTONE_FEEDER_2, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_2_IN_SHALLOW_LAYER)),
    DEVICE_2_FEED_2_DEEP("device_2_d", ModItems.COBBLESTONE_FEEDER_2, OFDeviceLootCondition.is(OFDeviceLootCondition.MOD_2_IN_DEEP_LAYER)),
    ;

    private final Identifier id;
    private final ResourceKey<LootTable> resourceKey;
    private final ItemStack material;
    private final Predicate<OFDeviceLootCondition> canProcess;

    ModLootTables(String key, ItemLike item, Predicate<OFDeviceLootCondition> canProcess) {
        id = OreFarmingDevice.makeId(key);
        this.resourceKey = ResourceKey.create(Registries.LOOT_TABLE, id);
        this.material = new ItemStack(item);
        this.canProcess = canProcess;
    }

    public static Optional<ModLootTables> find(OFDeviceBlockEntity device, ItemStack stack) {
        return Arrays.stream(values()).filter(t -> t.canProcess(device, stack)).findFirst();
    }

    public static Optional<ModLootTables> find(OFDeviceLootCondition lootCondition, ItemStack stack) {
        return Arrays.stream(values()).filter(t -> t.canProcess(lootCondition, stack)).findFirst();
    }

    public Identifier getId() {
        return id;
    }

    public ResourceKey<LootTable> getResourceKey() {
        return resourceKey;
    }

    public ItemStack getMaterial() {
        return material;
    }

    public OFDeviceLootCondition getLootCondition() {
        return Arrays.stream(OFDeviceLootCondition.values())
                .filter(canProcess)
                .findFirst().orElse(OFDeviceLootCondition.NOT_APPLICABLE);
    }

    public boolean canProcess(OFDeviceBlockEntity device, ItemStack stack) {
        if (material.getItem() instanceof CobblestoneFeederItem
                && !OreFarmingDeviceConfig.SERVER.isCobblestoneFeederAvailable()) {
            // OF Cobblestone Feeders is not available for OF Devices by config
            return false;
        }

        OFDeviceLootCondition lootCondition = OFDeviceLootCondition.find(device);
        return canProcess.test(lootCondition) && ItemStack.isSameItem(material, stack);
    }

    public boolean canProcess(OFDeviceLootCondition lootCondition, ItemStack stack) {
        if (material.getItem() instanceof CobblestoneFeederItem
                && !OreFarmingDeviceConfig.SERVER.isCobblestoneFeederAvailable()) {
            // OF Cobblestone Feeders are not available for OF Devices by config
            return false;
        }

        return canProcess.test(lootCondition) && ItemStack.isSameItem(material, stack);
    }
}
