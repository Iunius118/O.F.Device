package com.github.iunius118.orefarmingdevice.world.level.block.entity;

import com.github.iunius118.orefarmingdevice.inventory.CobblestoneDeviceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class CobblestoneDeviceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {
    public static final String KEY_INTERVAL_TIME = "IntervalTime";
    private static final int[] SLOTS_FOR_DOWN = new int[]{0};
    private static final int CONTAINER_SIZE = 1;
    public static final int SLOT_RESULT = 0;
    protected NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    public int intervalTime;
    public final CobblestoneDeviceType type;
    protected final ContainerData dataAccess = new SimpleContainerData(0);

    protected CobblestoneDeviceBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, CobblestoneDeviceType cobblestoneDeviceType) {
        super(blockEntityType, blockPos, blockState);
        type = cobblestoneDeviceType;
    }

    public CobblestoneDeviceBlockEntity(BlockPos blockPos, BlockState blockState, CobblestoneDeviceType type) {
        this(getBlockEntityType(type), blockPos, blockState, type);
    }

    public static BlockEntityType<CobblestoneDeviceBlockEntity> getBlockEntityType(CobblestoneDeviceType type) {
        if (type == CobblestoneDeviceType.BASIC) {
            return ModBlockEntityTypes.COBBLESTONE_DEVICE_0;
        }

        return null;
    }

    public int getIntervalTicks() {
        return type.getIntervalTicks();
    }

    @Override
    public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, items, provider);
        intervalTime = compoundTag.getIntOr(KEY_INTERVAL_TIME, 0);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putInt(KEY_INTERVAL_TIME, intervalTime);
        ContainerHelper.saveAllItems(compoundTag, items, provider);
    }

    @Override
    public void preRemoveSideEffects(BlockPos blockPos, BlockState blockState) {
        if (this.level != null) {
            Containers.dropContents(this.level, blockPos, this);
        }
    }

    /* ITickableTileEntity */

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, CobblestoneDeviceBlockEntity device) {
        if (++device.intervalTime >= device.getIntervalTicks()) {
            device.intervalTime = 0;

            if (level == null || level.isClientSide) {
                return;
            }

            // Server side
            ItemStack productSlotStack = device.items.get(SLOT_RESULT);
            ItemStack productStack = device.getProductStack();

            if (productSlotStack.isEmpty()) {
                // Insert product item stack to empty product slot
                device.items.set(SLOT_RESULT, productStack);
            } else if (ItemStack.isSameItem(productSlotStack, productStack)
                    && (productSlotStack.getCount() + productStack.getCount() <= Math.min(device.getMaxStackSize(), productSlotStack.getMaxStackSize()))) {
                // Add same product item to item stack in product slot
                productSlotStack.grow(productStack.getCount());
            } else {
                // When the result slot stack is full, do nothing.
            }
        }
    }

    private ItemStack getProductStack() {
        return this.worldPosition.getY() > 0 ? new ItemStack(Blocks.COBBLESTONE) : new ItemStack(Blocks.COBBLED_DEEPSLATE);
    }

    @Override
    protected MutableComponent getDefaultName() {
        String translationKey = type.getContainerTranslationKey();
        return Component.translatable(translationKey);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new CobblestoneDeviceMenu(containerId, playerInventory, this, dataAccess);
    }

    /* WorldlyContainer */

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return direction == Direction.DOWN ? SLOTS_FOR_DOWN : new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    /* IInventory */

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < items.size() ? items.get(index) : ItemStack.EMPTY;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        return ContainerHelper.removeItem(items, index, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(items, index);
    }

    @Override
    public void setItem(int index, ItemStack newStack) {
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nwStacks) {
    }

    public void setItemForGameTest(int index, ItemStack newStack) {
        items.set(index, newStack);

        if (!newStack.isEmpty() && newStack.getCount() > this.getMaxStackSize()) {
            newStack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    protected void setItemsForGameTest(NonNullList<ItemStack> nwStacks) {
        items = nwStacks;
    }

    @Override
    public boolean stillValid(Player player) {
        if (level.getBlockEntity(worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double) worldPosition.getX() + 0.5D, (double) worldPosition.getY() + 0.5D, (double) worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeItem(Container p_273520_, int p_272681_, ItemStack p_273702_) {
        return true;
    }

    /* Clearable */

    @Override
    public void clearContent() {
        items.clear();
    }

    /*  StackedContentsCompatible */

    public void fillStackedContents(StackedItemContents itemContents) {
        for (ItemStack itemstack : items) {
            itemContents.accountStack(itemstack);
        }
    }
}
