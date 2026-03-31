package com.github.iunius118.orefarmingdevice.inventory;

import com.github.iunius118.orefarmingdevice.loot.OFDeviceLootCondition;
import com.github.iunius118.orefarmingdevice.loot.OFDeviceLootTables;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class OFDeviceMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;
    protected final Level level;

    public OFDeviceMenu(int containerCounter, Inventory playerInventory) {
        this(containerCounter, playerInventory, new SimpleContainer(3), new SimpleContainerData(5));
    }

    public OFDeviceMenu(int containerCounter, Inventory playerInventory, Container inventory, ContainerData dataAccess) {
        super(ModMenuTypes.DEVICE, containerCounter);
        this.container = inventory;
        this.data = dataAccess;
        this.level = playerInventory.player.level();
        this.addSlot(new Slot(inventory, 0, 108, 17));
        this.addSlot(new FuelSlot(this, inventory, 1, 56, 35));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, inventory, 2, 108, 53));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlots(dataAccess);
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack returnStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot.hasItem()) {
            ItemStack srcStack = slot.getItem();
            returnStack = srcStack.copy();

            if (slotIndex == 2) {
                if (!this.moveItemStackTo(srcStack, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(srcStack, returnStack);
            } else if (slotIndex != 1 && slotIndex != 0) {
                if (this.canSmelt(srcStack)) {
                    if (!this.moveItemStackTo(srcStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(srcStack)) {
                    if (!this.moveItemStackTo(srcStack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex < 30) {
                    if (!this.moveItemStackTo(srcStack, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex < 39 && !this.moveItemStackTo(srcStack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(srcStack, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (srcStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (srcStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, srcStack);
        }

        return returnStack;
    }

    protected boolean canSmelt(ItemStack stack) {
        int i = this.data.get(4);
        OFDeviceLootCondition condition = OFDeviceLootCondition.fromInt(i);
        return OFDeviceLootTables.find(condition, stack).isPresent();
    }

    protected boolean isFuel(ItemStack stack) {
        return this.level.fuelValues().isFuel(stack);
    }

    public float getBurnProgress() {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? Mth.clamp((float)i / (float)j, 0.0F, 1.0F) : 0.0F;
    }

    public float getLitProgress() {
        int i = this.data.get(1);

        if (i == 0) {
            i = 200;
        }

        return Mth.clamp((float) this.data.get(0) / (float) i, 0.0F, 1.0F);
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    private static class FuelSlot extends Slot {
        private final OFDeviceMenu menu;

        public FuelSlot(OFDeviceMenu ofDeviceMenu, Container inventory, int slotIndex, int x, int y) {
            super(inventory, slotIndex, x, y);
            this.menu = ofDeviceMenu;
        }

        public boolean mayPlace(ItemStack stack) {
            return this.menu.isFuel(stack) || isBucket(stack);
        }

        public int getMaxStackSize(ItemStack stack) {
            return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
        }

        public boolean isBucket(ItemStack stack) {
            return stack.getItem() == Items.BUCKET;
        }
    }
}
