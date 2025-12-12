package com.github.iunius118.orefarmingdevice.world.level.block.entity;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import net.minecraft.resources.Identifier;

import java.util.Arrays;

public enum OFDeviceType {
    MOD_0("device_0", 200, 1),
    MOD_1("device_1", 100, 2),
    MOD_2("device_2", 50, 4),
    ;

    private final String name;
    private final int totalProcessingTime;
    private final int fuelConsumption;

    OFDeviceType(String name, int totalProcessingTime, int fuelConsumption) {
        this.name = name;
        this.totalProcessingTime = totalProcessingTime;
        this.fuelConsumption = fuelConsumption;
    }

    public static OFDeviceType valueOf (Identifier id) {
        return Arrays.stream(OFDeviceType.values()).filter(t -> t.getId().equals(id)).findFirst().orElse(MOD_0);
    }

    public String getName() {
        return name;
    }

    public int getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public String getContainerTranslationKey() {
        return "container." + OreFarmingDevice.MOD_ID + "." + name;
    }

    public Identifier getId() {
        return OreFarmingDevice.makeId(name);
    }
}
