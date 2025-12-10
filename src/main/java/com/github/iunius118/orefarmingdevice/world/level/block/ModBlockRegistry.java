package com.github.iunius118.orefarmingdevice.world.level.block;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.CobblestoneDeviceType;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceType;
import com.google.common.base.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlockRegistry {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OreFarmingDevice.MOD_ID);

    public static final DeferredBlock<Block> DEVICE_0 = registerDeviceBlock(OFDeviceType.MOD_0.getName(), p -> new OFDeviceBlock(p, OFDeviceType.MOD_0));
    public static final DeferredBlock<Block> DEVICE_1 = registerDeviceBlock(OFDeviceType.MOD_1.getName(), p -> new OFDeviceBlock(p, OFDeviceType.MOD_1));
    public static final DeferredBlock<Block> DEVICE_2 = registerDeviceBlock(OFDeviceType.MOD_2.getName(), p -> new OFDeviceBlock(p, OFDeviceType.MOD_2));
    public static final DeferredBlock<Block> COBBLESTONE_DEVICE_0 = registerDeviceBlock(CobblestoneDeviceType.BASIC.getName(), p -> new CobblestoneDeviceBlock(p, CobblestoneDeviceType.BASIC));

    private static DeferredBlock<Block> registerDeviceBlock(String name, Function<BlockBehaviour.Properties, Block> func) {
        Supplier<BlockBehaviour.Properties> properties = () -> BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops().strength(3.5F);
        return BLOCKS.registerBlock(name, func, properties);
    }

    public static void register(IEventBus modEventBus) {
        OreFarmingDevice.LOGGER.debug("Register mod blocks");
        BLOCKS.register(modEventBus);
    }
}
