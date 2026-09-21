package com.github.iunius118.orefarmingdevice.data;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.tags.ModTags;
import com.github.iunius118.orefarmingdevice.world.level.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends VanillaBlockTagsProvider {
    public ModBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper exFileHelper) {
        super(packOutput, lookupProvider, OreFarmingDevice.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addBlocks(tag(ModTags.Blocks.DEVICES),
                ModBlocks.DEVICE_0, ModBlocks.DEVICE_1, ModBlocks.DEVICE_2, ModBlocks.COBBLESTONE_DEVICE_0);

        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ModTags.Blocks.DEVICES);
        tag(BlockTags.BLOCKS_MOTION_NO_LEAVES).addTag(ModTags.Blocks.DEVICES);
    }

    private TagAppender<Block> addBlocks(TagAppender<Block> appender, Block... blocks) {
        Arrays.stream(blocks)
                .map(b -> BuiltInRegistries.BLOCK.wrapAsHolder(b).unwrapKey())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(appender::add);
        return appender;
    }
}
