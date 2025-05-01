package com.github.iunius118.orefarmingdevice.data;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.world.item.ModItems;
import com.github.iunius118.orefarmingdevice.world.level.block.CobblestoneDeviceBlock;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import com.github.iunius118.orefarmingdevice.world.level.block.OFDeviceBlock;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, OreFarmingDevice.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateBlockModels(blockModels);
        generateItemModels(itemModels);
    }

    private void generateBlockModels(BlockModelGenerators blockModels) {
        var blockStateOutput = blockModels.blockStateOutput;

        blockStateOutput.accept(createOFDevice(blockModels, ModBlocks.DEVICE_0));
        blockStateOutput.accept(createOFDevice(blockModels, ModBlocks.DEVICE_1));
        blockStateOutput.accept(createOFDevice(blockModels, ModBlocks.DEVICE_2));
        blockStateOutput.accept(createCobblestoneDevice(blockModels, ModBlocks.COBBLESTONE_DEVICE_0));
    }

    private void generateItemModels(ItemModelGenerators itemModels) {
        ItemModelOutput itemModelOutput = itemModels.itemModelOutput;

        addBlockItemModel(itemModelOutput, (BlockItem) ModItems.DEVICE_0, "_off_0");
        addBlockItemModel(itemModelOutput, (BlockItem) ModItems.DEVICE_1, "_off_0");
        addBlockItemModel(itemModelOutput, (BlockItem) ModItems.DEVICE_2, "_off_0");
        addBlockItemModel(itemModelOutput, (BlockItem) ModItems.COBBLESTONE_DEVICE_0, "_0");
        itemModels.generateFlatItem(ModItems.COBBLESTONE_FEEDER, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COBBLESTONE_FEEDER_2, ModelTemplates.FLAT_ITEM);
    }

    private MultiVariantGenerator createOFDevice(BlockModelGenerators blockModels, OFDeviceBlock block) {
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(OFDeviceBlock.LIT, OFDeviceBlock.CASING).generate((lit, casing) -> {
                    String litSuffix = lit ? "on" : "off";
                    String suffix = "_%s_%d".formatted(litSuffix, casing.ordinal());
                    return BlockModelGenerators.plainVariant(
                            blockModels.createSuffixedVariant(block, suffix, ModelTemplates.CUBE_ALL, TextureMapping::cube));
                })
        );
    }

    private MultiVariantGenerator createCobblestoneDevice(BlockModelGenerators blockModels, CobblestoneDeviceBlock block) {
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(OFDeviceBlock.CASING).generate(casing -> {
                    String suffix = "_%d".formatted(casing.ordinal());
                    return BlockModelGenerators.plainVariant(
                            blockModels.createSuffixedVariant(block, suffix, ModelTemplates.CUBE_ALL, TextureMapping::cube));
                })
        );
    }

    private void addBlockItemModel(ItemModelOutput itemModelOutput, BlockItem item, String suffix) {
        var model = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item.getBlock(), suffix));
        itemModelOutput.accept(item, model);
    }
}
