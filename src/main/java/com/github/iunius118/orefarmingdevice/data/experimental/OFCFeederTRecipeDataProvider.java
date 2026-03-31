package com.github.iunius118.orefarmingdevice.data.experimental;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.concurrent.CompletableFuture;

public class OFCFeederTRecipeDataProvider {
    private final static String PACK_PATH = "feeder_t_recipes";
    private final static Identifier PACK_ID = OreFarmingDevice.makeId(PACK_PATH);
    private final static String PACK_NAME = "O.F.Device Feeder T Recipes";

    private OFCFeederTRecipeDataProvider() {}

    public static void addProviders(final GatherDataEvent.Client event) {
        var dataGenerator = event.getGenerator();
        var packOutput = new PackOutput(dataGenerator.getPackOutput().getOutputFolder().resolve(PACK_PATH));
        var lookupProvider = event.getLookupProvider();
        var packGenerator = dataGenerator.getBuiltinDatapack(true, PACK_PATH);

        packGenerator.addProvider((o) -> PackMetadataGenerator.forFeaturePack(packOutput, Component.literal("O.F.Device - OF C Feeder T recipes")));
        packGenerator.addProvider((o) -> new OFCFeederTRecipeDataProvider.FeederTRecipeProvider.Runner(packOutput, lookupProvider));
    }

    public static void addPackFinders(final AddPackFindersEvent event) {
        event.addPackFinders(PACK_ID, PackType.SERVER_DATA, Component.literal(PACK_PATH), PackSource.FEATURE, false, Pack.Position.TOP);
    }

    private static class FeederTRecipeProvider extends VanillaRecipeProvider {
        public FeederTRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            super(provider, output);
        }

        @Override
        protected void buildRecipes() {
            final HolderLookup.RegistryLookup<Item> holderGetter = registries.lookupOrThrow(Registries.ITEM);

            // Cobblestone Feeder TNT
            ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.COBBLESTONE_FEEDER_TNT)
                    .pattern("PTP")
                    .pattern("OHO")
                    .pattern("RfR")
                    .define('P', Blocks.PISTON)
                    .define('T', Blocks.TNT)
                    .define('O', Blocks.OBSERVER)
                    .define('H', Blocks.HOPPER)
                    .define('R', Blocks.REPEATER)
                    .define('f', ModItems.COBBLESTONE_FEEDER_2)
                    .unlockedBy("has_feeder_2", has(ModItems.COBBLESTONE_FEEDER_2))
                    .save(output);

            // Cobblestone Feeder TNT -> Cobblestone Feeder II
            ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.MISC, ModItems.COBBLESTONE_FEEDER_2)
                    .group(OreFarmingDevice.MOD_ID + ":feeders_to_feeder_2")
                    .requires(ModItems.COBBLESTONE_FEEDER_TNT)
                    .unlockedBy("has_feeder_tnt", has(ModItems.COBBLESTONE_FEEDER_TNT))
                    .save(output, OreFarmingDevice.MOD_ID + ":feeder_tnt_to_feeder_2");
        }

        public static class Runner extends RecipeProvider.Runner {
            protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
                super(packOutput, registries);
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
                return new FeederTRecipeProvider(registries, output);
            }

            @Override
            public String getName() {
                return "Recipes";
            }
        }
    }
}
