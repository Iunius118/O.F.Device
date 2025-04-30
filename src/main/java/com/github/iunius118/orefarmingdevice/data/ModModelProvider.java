package com.github.iunius118.orefarmingdevice.data;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.item.ModItems;
import com.github.iunius118.orefarmingdevice.world.level.block.CobblestoneDeviceBlock;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import com.github.iunius118.orefarmingdevice.world.level.block.OFDeviceBlock;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected Stream<Block> getKnownBlocks() {
        // Return stream of all mod blocks
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().registry().getNamespace().equals(OreFarmingDevice.MOD_ID))
                .map(Map.Entry::getValue);
    }

    @Override
    protected Stream<Item> getKnownItems() {
        // Return stream of all mod items
        return ForgeRegistries.ITEMS.getEntries().stream()
                .filter(e -> e.getKey().registry().getNamespace().equals(OreFarmingDevice.MOD_ID))
                .map(Map.Entry::getValue);
    }

    @Override
    protected BlockModelGenerators getBlockModelGenerators(BlockStateGeneratorCollector blocks, ItemInfoCollector items, SimpleModelCollector models) {
        return new ModBlockModelGenerators(blocks, items, models);
    }

    @Override
    protected ItemModelGenerators getItemModelGenerators(ItemInfoCollector items, SimpleModelCollector models) {
        return new ModItemModelGenerators(items, models);
    }

    private static class ModBlockModelGenerators extends BlockModelGenerators {
        public ModBlockModelGenerators(BlockStateGeneratorCollector blockStateOutput, ItemInfoCollector itemModelOutput, SimpleModelCollector modelOutput) {
            super(blockStateOutput, itemModelOutput, modelOutput);
        }

        @Override
        public void run() {
            createOFDevice(ModBlocks.DEVICE_0);
            createOFDevice(ModBlocks.DEVICE_1);
            createOFDevice(ModBlocks.DEVICE_2);
            createCobblestoneDevice(ModBlocks.COBBLESTONE_DEVICE_0);
        }

        public void createOFDevice(OFDeviceBlock block) {
            var multiVariantGenerator = MultiVariantGenerator.dispatch(block).with(
                    PropertyDispatch.initial(OFDeviceBlock.LIT, OFDeviceBlock.CASING).generate((lit, casing) -> {
                        String litSuffix = lit ? "on" : "off";
                        String suffix = "_%s_%d".formatted(litSuffix, casing.ordinal());
                        return plainVariant(this.createSuffixedVariant(block, suffix, ModelTemplates.CUBE_ALL, TextureMapping::cube));
                    })
            );
            this.blockStateOutput.accept(multiVariantGenerator);
        }

        public void createCobblestoneDevice(CobblestoneDeviceBlock block) {
            var multiVariantGenerator = MultiVariantGenerator.dispatch(block).with(
                    PropertyDispatch.initial(OFDeviceBlock.CASING).generate(casing -> {
                        String suffix = "_%d".formatted(casing.ordinal());
                        return plainVariant(this.createSuffixedVariant(block, suffix, ModelTemplates.CUBE_ALL, TextureMapping::cube));
                    })
            );
            this.blockStateOutput.accept(multiVariantGenerator);
        }
    }

    private static class ModItemModelGenerators extends ItemModelGenerators {
        public ModItemModelGenerators(ItemInfoCollector itemModelOutput, SimpleModelCollector modelOutput) {
            super(itemModelOutput, modelOutput);
        }

        @Override
        public void run() {
            addBlockItemModel((BlockItem) ModItems.DEVICE_0, "_off_0");
            addBlockItemModel((BlockItem) ModItems.DEVICE_1, "_off_0");
            addBlockItemModel((BlockItem) ModItems.DEVICE_2, "_off_0");
            addBlockItemModel((BlockItem) ModItems.COBBLESTONE_DEVICE_0, "_0");

            this.generateFlatItem(ModItems.COBBLESTONE_FEEDER, ModelTemplates.FLAT_ITEM);
            this.generateFlatItem(ModItems.COBBLESTONE_FEEDER_2, ModelTemplates.FLAT_ITEM);
        }

        private void addBlockItemModel(BlockItem item, String suffix) {
            ItemModel.Unbaked model = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item.getBlock(), suffix));
            itemModelOutput.accept(item, model);
        }
    }
}
