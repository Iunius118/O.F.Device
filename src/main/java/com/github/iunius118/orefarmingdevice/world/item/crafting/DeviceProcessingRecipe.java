package com.github.iunius118.orefarmingdevice.world.item.crafting;

import com.github.iunius118.orefarmingdevice.loot.OFDeviceLootCondition;
import com.github.iunius118.orefarmingdevice.loot.OFDeviceLootTables;
import com.github.iunius118.orefarmingdevice.world.item.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * Dummy recipe class for devices.
 * This is used when the device's processing recipe is requested from outside.
 * Since it is a dummy recipe, the contents have no meaning.
 */
public class DeviceProcessingRecipe extends AbstractCookingRecipe {
    public static final MapCodec<DeviceProcessingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(
                    Codec.STRING.optionalFieldOf("dummy").forGetter(recipe -> Optional.empty())
            ).apply(instance, (dummy) -> new DeviceProcessingRecipe())
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, DeviceProcessingRecipe> STREAM_CODEC = StreamCodec.of(
            (b, r) -> {}, (b) -> new DeviceProcessingRecipe()
    );
    public static final RecipeSerializer<DeviceProcessingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public DeviceProcessingRecipe() {
        super(new CommonInfo(false), new CookingBookInfo(CookingBookCategory.MISC, ""), Ingredient.of(Items.COBBLESTONE), new ItemStackTemplate(Items.STONE), 0, 200);
    }

    @Override
    public boolean matches(SingleRecipeInput recipeInput, Level level) {
        ItemStack stack = recipeInput.item();
        return OFDeviceLootTables.find(OFDeviceLootCondition.MOD_0_IN_SHALLOW_LAYER, stack).isPresent();
    }

    @Override
    public RecipeType<? extends AbstractCookingRecipe> getType() {
        return ModRecipeTypes.DEVICE_PROCESSING;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.DEVICE;
    }

    @Override
    protected Item furnaceIcon() {
        return ModItems.DEVICE_0;
    }

    @Override
    public RecipeSerializer<DeviceProcessingRecipe> getSerializer() {
        return SERIALIZER;
    }
}
