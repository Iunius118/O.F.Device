package com.github.iunius118.orefarmingdevice.world.level.block.entity;

public enum OFDeviceProcessingSpeed {
    HALF(2.0f),
    NORMAL(1.0f),
    DOUBLE(0.5f)
    ;

    /** Multiplier to be applied to the base processing time of OF Devices.
     *  The smaller this value is, the faster the processing speed will be. */
    private final float multiplier;

    OFDeviceProcessingSpeed(float multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Gets the processing speed multiplier for OF Devices.
     * @return The processing speed multiplier. The smaller this value is, the faster the processing speed will be.
     */
    public float getMultiplier() {
        return multiplier;
    }
}
