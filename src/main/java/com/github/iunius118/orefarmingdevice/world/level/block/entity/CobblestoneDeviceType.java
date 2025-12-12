package com.github.iunius118.orefarmingdevice.world.level.block.entity;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import net.minecraft.resources.Identifier;

import java.util.Arrays;

public enum CobblestoneDeviceType {
    BASIC("cobblestone_device_0", 8),
    ;

    private final String name;
    private final int intervalTicks;

    CobblestoneDeviceType(String name, int intervalTicks) {
        this.name = name;
        this.intervalTicks = intervalTicks;
    }

    public static CobblestoneDeviceType valueOf (Identifier id) {
        return Arrays.stream(CobblestoneDeviceType.values()).filter(t -> t.getId().equals(id)).findFirst().orElse(BASIC);
    }

    public String getName() {
        return name;
    }

    public int getIntervalTicks() {
        return intervalTicks;
    }

    public String getContainerTranslationKey() {
        return "container." + OreFarmingDevice.MOD_ID + "." + name;
    }

    public Identifier getId() {
        return OreFarmingDevice.makeId(name);
    }
}
