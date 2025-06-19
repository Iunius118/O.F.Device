package com.github.iunius118.orefarmingdevice.world.level.block;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.CobblestoneDeviceType;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockRegistry {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OreFarmingDevice.MOD_ID);

    public static final RegistryObject<Block> DEVICE_0 = registerDeviceBlock(OFDeviceType.MOD_0.getName(), p -> new OFDeviceBlock(p, OFDeviceType.MOD_0));
    public static final RegistryObject<Block> DEVICE_1 = registerDeviceBlock(OFDeviceType.MOD_1.getName(), p -> new OFDeviceBlock(p, OFDeviceType.MOD_1));
    public static final RegistryObject<Block> DEVICE_2 = registerDeviceBlock(OFDeviceType.MOD_2.getName(), p -> new OFDeviceBlock(p, OFDeviceType.MOD_2));
    public static final RegistryObject<Block> COBBLESTONE_DEVICE_0 = registerDeviceBlock(CobblestoneDeviceType.BASIC.getName(), p -> new CobblestoneDeviceBlock(p, CobblestoneDeviceType.BASIC));

    private static ResourceKey<Block> modBlockId(String name) {
        return ResourceKey.create(Registries.BLOCK, OreFarmingDevice.makeId(name));
    }

    private static RegistryObject<Block> registerDeviceBlock(String name, Function<BlockBehaviour.Properties, Block> func) {
        var properties = BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F);
        return registerBlock(name, func, properties);
    }

    private static RegistryObject<Block> registerBlock(String name, Function<BlockBehaviour.Properties, Block> func, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, () -> func.apply(properties.setId(modBlockId(name))));
    }

    public static void register(BusGroup modBusGroup) {
        OreFarmingDevice.LOGGER.debug("Register mod blocks");
        BLOCKS.register(modBusGroup);
    }
}
