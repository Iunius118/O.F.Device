package com.github.iunius118.orefarmingdevice.world.item;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlockRegistry;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.CobblestoneDeviceType;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItemRegistry {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OreFarmingDevice.MOD_ID);

    public static final DeferredItem<BlockItem> DEVICE_0 = ITEMS.registerSimpleBlockItem(OFDeviceType.MOD_0.getName(), ModBlockRegistry.DEVICE_0);
    public static final DeferredItem<BlockItem> DEVICE_1 = ITEMS.registerSimpleBlockItem(OFDeviceType.MOD_1.getName(), ModBlockRegistry.DEVICE_1);
    public static final DeferredItem<BlockItem> DEVICE_2 = ITEMS.registerSimpleBlockItem(OFDeviceType.MOD_2.getName(), ModBlockRegistry.DEVICE_2);
    public static final DeferredItem<BlockItem> COBBLESTONE_DEVICE_0 = ITEMS.registerSimpleBlockItem(CobblestoneDeviceType.BASIC.getName(), ModBlockRegistry.COBBLESTONE_DEVICE_0);
    public static final DeferredItem<Item> COBBLESTONE_FEEDER = registerCobblestoneFeederItem(CobblestoneFeederType.BASIC.getName(), CobblestoneFeederType.BASIC);
    public static final DeferredItem<Item> COBBLESTONE_FEEDER_2 = registerCobblestoneFeederItem(CobblestoneFeederType.UPGRADED.getName(), CobblestoneFeederType.UPGRADED);
    public static final DeferredItem<Item> COBBLESTONE_FEEDER_TNT = registerCobblestoneFeederItem(CobblestoneFeederType.TNT.getName(), CobblestoneFeederType.TNT);

    private static DeferredItem<Item> registerCobblestoneFeederItem(String name, CobblestoneFeederType type) {
        return ITEMS.registerItem(name, p -> new CobblestoneFeederItem(type, p));
    }

    public static void register(IEventBus modEventBus) {
        OreFarmingDevice.LOGGER.debug("Register mod items");
        ITEMS.register(modEventBus);
    }
}
