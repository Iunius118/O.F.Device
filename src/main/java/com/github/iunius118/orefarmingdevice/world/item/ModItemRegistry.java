package com.github.iunius118.orefarmingdevice.world.item;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlockRegistry;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.CobblestoneDeviceType;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModItemRegistry {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OreFarmingDevice.MOD_ID);

    public static final RegistryObject<Item> DEVICE_0 = registerBlockItem(OFDeviceType.MOD_0.getName(), ModBlockRegistry.DEVICE_0);
    public static final RegistryObject<Item> DEVICE_1 = registerBlockItem(OFDeviceType.MOD_1.getName(), ModBlockRegistry.DEVICE_1);
    public static final RegistryObject<Item> DEVICE_2 = registerBlockItem(OFDeviceType.MOD_2.getName(), ModBlockRegistry.DEVICE_2);
    public static final RegistryObject<Item> COBBLESTONE_DEVICE_0 = registerBlockItem(CobblestoneDeviceType.BASIC.getName(), ModBlockRegistry.COBBLESTONE_DEVICE_0);
    public static final RegistryObject<Item> COBBLESTONE_FEEDER = registerCobblestoneFeederItem(CobblestoneFeederType.BASIC.getName(), CobblestoneFeederType.BASIC);
    public static final RegistryObject<Item> COBBLESTONE_FEEDER_2 = registerCobblestoneFeederItem(CobblestoneFeederType.UPGRADED.getName(), CobblestoneFeederType.UPGRADED);

    private static ResourceKey<Item> modItemId(String name) {
        return ResourceKey.create(Registries.ITEM, OreFarmingDevice.makeId(name));
    }

    private static RegistryObject<Item> registerBlockItem(String name, RegistryObject<Block> block) {
        return registerItem(name, p -> new BlockItem(block.get(), p), new Item.Properties().useBlockDescriptionPrefix());
    }

    private static RegistryObject<Item> registerCobblestoneFeederItem(String name, CobblestoneFeederType type) {
        return registerItem(name, p -> new CobblestoneFeederItem(type, p), new Item.Properties());
    }

    private static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> func, Item.Properties properties) {
        return ITEMS.register(name, () -> func.apply(properties.setId(modItemId(name))));
    }

    public static void register(BusGroup modBusGroup) {
        OreFarmingDevice.LOGGER.debug("Register mod items");
        ITEMS.register(modBusGroup);
    }
}
