package com.github.iunius118.orefarmingdevice.common;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.inventory.ModMenuTypes;
import com.github.iunius118.orefarmingdevice.world.item.ModCreativeModeTabs;
import com.github.iunius118.orefarmingdevice.world.item.ModItemRegistry;
import com.github.iunius118.orefarmingdevice.world.item.crafting.ModRecipeSerializers;
import com.github.iunius118.orefarmingdevice.world.item.crafting.ModRecipeTypes;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlockRegistry;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.CobblestoneDeviceType;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.ModBlockEntityTypes;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterEventHandler {
    public static void registerGameObjects(BusGroup modBusGroup) {
        registerBlocks(modBusGroup);
        registerItems(modBusGroup);
        registerBlockEntityTypes(modBusGroup);
        registerRecipeTypes(modBusGroup);
        registerRecipeSerializers(modBusGroup);
        registerMenuTypes(modBusGroup);
        registerCreativeModeTabs(modBusGroup);
    }

    private static void registerBlocks(BusGroup modBusGroup) {
        ModBlockRegistry.register(modBusGroup);
    }

    private static void registerItems(BusGroup modBusGroup) {
        ModItemRegistry.register(modBusGroup);
    }

    private static void registerBlockEntityTypes(BusGroup modBusGroup) {
        var blockEntityTypeRegister = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, OreFarmingDevice.MOD_ID);

        blockEntityTypeRegister.register(OFDeviceType.MOD_0.getName(), () -> ModBlockEntityTypes.DEVICE_0);
        blockEntityTypeRegister.register(OFDeviceType.MOD_1.getName(), () -> ModBlockEntityTypes.DEVICE_1);
        blockEntityTypeRegister.register(OFDeviceType.MOD_2.getName(), () -> ModBlockEntityTypes.DEVICE_2);
        blockEntityTypeRegister.register(CobblestoneDeviceType.BASIC.getName(), () -> ModBlockEntityTypes.COBBLESTONE_DEVICE_0);

        blockEntityTypeRegister.register(modBusGroup);
    }

    private static void registerRecipeTypes(BusGroup modBusGroup) {
        var recipeTypeRegister = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, OreFarmingDevice.MOD_ID);

        recipeTypeRegister.register("device_processing", () -> ModRecipeTypes.DEVICE_PROCESSING);

        recipeTypeRegister.register(modBusGroup);
    }

    private static void registerRecipeSerializers(BusGroup modBusGroup) {
        var recipeSerializerRegister = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, OreFarmingDevice.MOD_ID);

        recipeSerializerRegister.register("device_processing", () -> ModRecipeSerializers.DEVICE_PROCESSING);

        recipeSerializerRegister.register(modBusGroup);
    }

    private static void registerMenuTypes(BusGroup modBusGroup) {
        var menuTypeRegister = DeferredRegister.create(ForgeRegistries.MENU_TYPES, OreFarmingDevice.MOD_ID);

        menuTypeRegister.register("device", () -> ModMenuTypes.DEVICE);
        menuTypeRegister.register("cobblestone_device", () -> ModMenuTypes.COBBLESTONE_DEVICE);

        menuTypeRegister.register(modBusGroup);
    }

    private static void registerCreativeModeTabs(BusGroup modBusGroup) {
        var creativeModeTabRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OreFarmingDevice.MOD_ID);

        creativeModeTabRegister.register("general", () -> ModCreativeModeTabs.MAIN);

        creativeModeTabRegister.register(modBusGroup);
    }
}
