package com.github.iunius118.orefarmingdevice.world.item;

import com.github.iunius118.orefarmingdevice.data.ModLanguageProvider;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static CreativeModeTab MAIN = CreativeModeTab.builder()
            .title(Component.translatable(ModLanguageProvider.MOD_ITEM_GROUP_KEY))
            .icon(() -> ModItemRegistry.DEVICE_2.isBound() ? new ItemStack(ModBlocks.DEVICE_2) : ItemStack.EMPTY)
            .displayItems((params, output) -> {
                if (!ModItemRegistry.DEVICE_0.isBound()) {
                    return;
                }

                output.accept(ModItems.DEVICE_0);
                output.accept(ModItems.DEVICE_1);
                output.accept(ModItems.DEVICE_2);
                output.accept(ModItems.COBBLESTONE_DEVICE_0);
                output.accept(ModItems.COBBLESTONE_FEEDER);
                output.accept(ModItems.COBBLESTONE_FEEDER_2);
                output.accept(ModItems.COBBLESTONE_FEEDER_TNT);
            })
            .build();
}
