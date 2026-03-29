package com.github.iunius118.orefarmingdevice.loot;

import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceBlockEntity;
import com.github.iunius118.orefarmingdevice.world.level.block.entity.OFDeviceType;
import net.minecraft.util.Mth;

import java.util.Arrays;
import java.util.function.Predicate;

public enum OFDeviceLootCondition {
    MOD_0_IN_SHALLOW_LAYER(OFDeviceType.MOD_0, false),
    MOD_1_IN_SHALLOW_LAYER(OFDeviceType.MOD_1, false),
    MOD_2_IN_SHALLOW_LAYER(OFDeviceType.MOD_2, false),
    MOD_0_IN_DEEP_LAYER(OFDeviceType.MOD_0, true),
    MOD_1_IN_DEEP_LAYER(OFDeviceType.MOD_1, true),
    MOD_2_IN_DEEP_LAYER(OFDeviceType.MOD_2, true),
    NOT_APPLICABLE(null, false),
    ;

    private final OFDeviceType type;
    private final boolean isInDeepLayer;

    OFDeviceLootCondition(OFDeviceType deviceType, boolean isDeviceInDeepLayer) {
        type = deviceType;
        isInDeepLayer = isDeviceInDeepLayer;
    }

    public boolean test(OFDeviceBlockEntity device) {
        return (device.type == type) && ((device.getBlockPos().getY() <= 0) == isInDeepLayer);
    }

    public static OFDeviceLootCondition find(OFDeviceBlockEntity device) {
        return Arrays.stream(values()).filter(c -> c.test(device)).findFirst().orElse(NOT_APPLICABLE);
    }

    public static OFDeviceLootCondition fromInt(int i) {
        OFDeviceLootCondition[] values = values();
        return values[Mth.clamp(i, 0, values.length - 1)];
    }

    public int toInt() {
        return ordinal();
    }

    public static Predicate<OFDeviceLootCondition> is(OFDeviceType deviceType) {
        return c -> c.contains(deviceType);
    }

    public static Predicate<OFDeviceLootCondition> is(OFDeviceLootCondition condition) {
        return c -> c.equals(condition);
    }

    public boolean contains(OFDeviceType deviceType) {
        return type == deviceType;
    }

    public boolean equals(OFDeviceLootCondition condition) {
        return this == condition;
    }

    public OFDeviceType getType() {
        return type;
    }

    public boolean isDeviceInShallowLayer() {
        return !isInDeepLayer;
    }

    public boolean isDeviceInDeepLayer() {
        return isInDeepLayer;
    }
}
