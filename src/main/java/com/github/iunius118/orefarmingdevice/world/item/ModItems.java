package com.github.iunius118.orefarmingdevice.world.item;

import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {
    private static final Item.Properties ofDevicePropertiesSupplier = new Item.Properties().tab(ModItemGroups.MAIN);

    public static final Item DEVICE_0 = new BlockItem(ModBlocks.DEVICE_0, ofDevicePropertiesSupplier);
    public static final Item DEVICE_1 = new BlockItem(ModBlocks.DEVICE_1, ofDevicePropertiesSupplier);
    public static final Item DEVICE_2 = new BlockItem(ModBlocks.DEVICE_2, ofDevicePropertiesSupplier);
    public static final Item COBBLESTONE_FEEDER = new Item(new Item.Properties().tab(ModItemGroups.MAIN));
}
