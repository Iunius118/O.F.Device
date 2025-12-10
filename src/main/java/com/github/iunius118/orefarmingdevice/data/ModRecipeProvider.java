package com.github.iunius118.orefarmingdevice.data;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.item.ModItems;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends VanillaRecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        final HolderLookup.RegistryLookup<Item> holderGetter = registries.lookupOrThrow(Registries.ITEM);

        // Devise 0
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.DECORATIONS, ModBlocks.DEVICE_0)
                .pattern("#F#")
                .pattern("#L#")
                .pattern("ixi")
                .define('#', Blocks.STONE)
                .define('F', Blocks.FURNACE)
                .define('L', Blocks.LEVER)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('x', Items.STONE_PICKAXE)
                .unlockedBy("has_furnace", has(Blocks.FURNACE))
                .save(output);

        // Devise 1
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.DECORATIONS, ModBlocks.DEVICE_1)
                .requires(ModBlocks.DEVICE_0)
                .requires(Items.IRON_PICKAXE)
                .unlockedBy("has_device_0", has(ModBlocks.DEVICE_0))
                .save(output);

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.PAPER), Ingredient.of(ModBlocks.DEVICE_0), Ingredient.of(Items.IRON_PICKAXE), RecipeCategory.DECORATIONS, Item.BY_BLOCK.get(ModBlocks.DEVICE_1))
                .unlocks("has_device_0", has(ModBlocks.DEVICE_0))
                .save(output, getItemId(ModBlocks.DEVICE_1.asItem()) + "_smithing");

        // Devise 2
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.DECORATIONS, ModBlocks.DEVICE_2)
                .requires(ModBlocks.DEVICE_1)
                .requires(Items.DIAMOND_PICKAXE)
                .unlockedBy("has_device_1", has(ModBlocks.DEVICE_1))
                .save(output);

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.PAPER), Ingredient.of(ModBlocks.DEVICE_1), Ingredient.of(Items.DIAMOND_PICKAXE), RecipeCategory.DECORATIONS, Item.BY_BLOCK.get(ModBlocks.DEVICE_2))
                .unlocks("has_device_1", has(ModBlocks.DEVICE_1))
                .save(output, getItemId(ModBlocks.DEVICE_2.asItem()) + "_smithing");

        // Cobblestone Feeder
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.COBBLESTONE_FEEDER)
                .pattern("  L")
                .pattern("RPx")
                .pattern("  W")
                .define('L', Items.LAVA_BUCKET)
                .define('R', Blocks.REPEATER)
                .define('P', Blocks.PISTON)
                .define('x', Items.STONE_PICKAXE)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("has_device_0", has(ModBlocks.DEVICE_0))
                .save(output);

        // Cobblestone Feeder II
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.MISC, ModItems.COBBLESTONE_FEEDER_2)
                .requires(ModItems.COBBLESTONE_FEEDER)
                .requires(Items.DIAMOND_PICKAXE)
                .unlockedBy("has_feeder", has(ModItems.COBBLESTONE_FEEDER))
                .save(output);

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.PAPER), Ingredient.of(ModItems.COBBLESTONE_FEEDER), Ingredient.of(Items.DIAMOND_PICKAXE), RecipeCategory.MISC, ModItems.COBBLESTONE_FEEDER_2)
                .unlocks("has_feeder", has(ModItems.COBBLESTONE_FEEDER))
                .save(output, getItemId(ModItems.COBBLESTONE_FEEDER_2) + "_smithing");

        // Cobblestone Feeder -> Lava Bucket
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.MISC, Items.LAVA_BUCKET)
                .group(OreFarmingDevice.MOD_ID + ":feeders_to_lava_bucket")
                .requires(ModItems.COBBLESTONE_FEEDER)
                .requires(Items.BUCKET)
                .unlockedBy("has_feeder", has(ModItems.COBBLESTONE_FEEDER))
                .save(output, OreFarmingDevice.MOD_ID + ":feeder_to_lava_bucket");

        // Cobblestone Feeder II -> Lava Bucket
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.MISC, Items.LAVA_BUCKET)
                .group(OreFarmingDevice.MOD_ID + ":feeders_to_lava_bucket")
                .requires(ModItems.COBBLESTONE_FEEDER_2)
                .requires(Items.BUCKET)
                .unlockedBy("has_feeder_2", has(ModItems.COBBLESTONE_FEEDER_2))
                .save(output, OreFarmingDevice.MOD_ID + ":feeder_2_to_lava_bucket");

        // Cobblestone Feeder II -> Diamond Pickaxe
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE)
                .requires(ModItems.COBBLESTONE_FEEDER_2)
                .requires(Tags.Items.RODS_WOODEN)
                .unlockedBy("has_feeder_2", has(ModItems.COBBLESTONE_FEEDER_2))
                .save(output, OreFarmingDevice.MOD_ID + ":feeder_2_to_diamond_pickaxe");

        // Cobblestone Devise
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.DECORATIONS, ModItems.COBBLESTONE_DEVICE_0)
                .pattern("###")
                .pattern("#f#")
                .pattern("#R#")
                .define('#', Blocks.STONE)
                .define('f', ModItems.COBBLESTONE_FEEDER_2)
                .define('R', Blocks.REDSTONE_TORCH)
                .unlockedBy("has_feeder_2", has(ModItems.COBBLESTONE_FEEDER_2))
                .save(output);

        // Cobblestone Devise -> Cobblestone Feeder II
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.MISC, ModItems.COBBLESTONE_FEEDER_2)
                .requires(ModItems.COBBLESTONE_DEVICE_0)
                .unlockedBy("has_c_device", has(ModItems.COBBLESTONE_DEVICE_0))
                .save(output, OreFarmingDevice.MOD_ID + ":c_device_to_feeder_2");
    }

    private Identifier getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }
        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
            return new ModRecipeProvider(registryLookup, output);
        }
        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
