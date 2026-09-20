package com.github.iunius118.orefarmingdevice.world.item.crafting;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<DeviceProcessingRecipe> DEVICE_PROCESSING = RecipeType.simple(OreFarmingDevice.makeId("device_processing"));
}
