package com.github.iunius118.orefarmingdevice.world.item;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import net.minecraft.resources.Identifier;

public enum CobblestoneFeederType {
    BASIC("cobblestone_feeder"),
    UPGRADED("cobblestone_feeder_2"),
    ;

    private final String name;

    CobblestoneFeederType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Identifier getId() {
        return OreFarmingDevice.makeId(name);
    }
}
