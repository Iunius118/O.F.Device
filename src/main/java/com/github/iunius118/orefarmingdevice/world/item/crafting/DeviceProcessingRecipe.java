package com.github.iunius118.orefarmingdevice.world.item.crafting;

import com.github.iunius118.orefarmingdevice.loot.ModLootTables;
import com.github.iunius118.orefarmingdevice.loot.OFDeviceLootCondition;
import com.github.iunius118.orefarmingdevice.world.item.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class DeviceProcessingRecipe extends AbstractCookingRecipe {
    public DeviceProcessingRecipe() {
        super("", CookingBookCategory.MISC, Ingredient.of(Items.COBBLESTONE), ItemStack.EMPTY, 0, 200);
    }

    @Override
    public boolean matches(SingleRecipeInput recipeInput, Level level) {
        ItemStack stack = recipeInput.item();
        return ModLootTables.find(OFDeviceLootCondition.MOD_0_IN_SHALLOW_LAYER, stack).isPresent();
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
        return ModRecipeSerializers.DEVICE_PROCESSING;
    }

    public static class Serializer implements RecipeSerializer<DeviceProcessingRecipe> {
        private static final MapCodec<DeviceProcessingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                (instance) -> instance.group(
                        Codec.STRING.optionalFieldOf("dummy").forGetter(recipe -> Optional.empty())
                ).apply(instance, (dummy) -> new DeviceProcessingRecipe())
        );
        private static final StreamCodec<RegistryFriendlyByteBuf, DeviceProcessingRecipe> STREAM_CODEC = StreamCodec.of(
                DeviceProcessingRecipe.Serializer::toNetwork, DeviceProcessingRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<DeviceProcessingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DeviceProcessingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static DeviceProcessingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            return new DeviceProcessingRecipe();
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, DeviceProcessingRecipe recipe) {
        }
    }
}
