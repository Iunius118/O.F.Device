package com.github.iunius118.orefarmingdevice.tags;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> DEVICES = modTag("devices");

        private static TagKey<Block> modTag(String id) {
            return TagKey.create(Registries.BLOCK, OreFarmingDevice.makeId(id));
        }
    }
}
