package com.github.iunius118.orefarmingdevice.world.level.block.entity;

import com.github.iunius118.orefarmingdevice.config.OreFarmingDeviceConfig;
import com.github.iunius118.orefarmingdevice.inventory.OFDeviceMenu;
import com.github.iunius118.orefarmingdevice.loot.ModLootTables;
import com.github.iunius118.orefarmingdevice.loot.OFDeviceLootCondition;
import com.github.iunius118.orefarmingdevice.world.item.CobblestoneFeederItem;
import com.github.iunius118.orefarmingdevice.world.item.CobblestoneFeederType;
import com.github.iunius118.orefarmingdevice.world.item.crafting.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.AABB;

import java.util.Collections;
import java.util.List;

public class OFDeviceBlockEntity extends AbstractFurnaceBlockEntity {
    public static final String KEY_EFFICIENCY = "Efficiency";
    public static final float MAX_EFFICIENCY = 3F;
    public final OFDeviceType type;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int slot) {
            return switch (slot) {
                case 0 -> OFDeviceBlockEntity.this.litTimeRemaining;
                case 1 -> OFDeviceBlockEntity.this.litTotalTime;
                case 2 -> OFDeviceBlockEntity.this.cookingTimer;
                case 3 -> OFDeviceBlockEntity.this.cookingTotalTime;
                // Device status number for determining material items on client side
                case 4 -> OFDeviceLootCondition.find(OFDeviceBlockEntity.this).toInt();
                default -> 0;
            };
        }

        public void set(int slot, int value) {
            switch (slot) {
                case 0 -> OFDeviceBlockEntity.this.litTimeRemaining = value;
                case 1 -> OFDeviceBlockEntity.this.litTotalTime = value;
                case 2 -> OFDeviceBlockEntity.this.cookingTimer = value;
                case 3 -> OFDeviceBlockEntity.this.cookingTotalTime = value;
                case 4 -> { /* Do nothing */ }
            }

        }

        public int getCount() {
            return 5;
        }
    };

    private float farmingEfficiency = 0F;
    private int productCount = 0;
    private ModLootTables lastProcessedLootTable = null;

    public OFDeviceBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, OFDeviceType ofDeviceType) {
        super(blockEntityType, blockPos, blockState, ModRecipeTypes.DEVICE_PROCESSING);
        type = ofDeviceType;
    }

    public OFDeviceBlockEntity(BlockPos blockPos, BlockState blockState, OFDeviceType type) {
        this(getBlockEntityType(type), blockPos, blockState, type);
    }

    public static BlockEntityType<OFDeviceBlockEntity> getBlockEntityType(OFDeviceType type) {
        return switch (type) {
            case MOD_0 -> ModBlockEntityTypes.DEVICE_0;
            case MOD_1 -> ModBlockEntityTypes.DEVICE_1;
            case MOD_2 -> ModBlockEntityTypes.DEVICE_2;
        };
    }

    public int getTotalProcessingTime() {
        return OreFarmingDeviceConfig.SERVER.canAccelerateProcessingSpeedByMod()
                ? type.getTotalProcessingTime() : OFDeviceType.MOD_0.getTotalProcessingTime();
    }

    public int getFuelConsumption(boolean isFuelConsumptionDoubled) {
        int fuel = OreFarmingDeviceConfig.SERVER.canIncreaseFuelConsumptionByMod()
                ? type.getFuelConsumption() : OFDeviceType.MOD_0.getFuelConsumption();
        return isFuelConsumptionDoubled ? fuel * 2 : fuel;
    }

    public float getFarmingEfficiency() {
        return OreFarmingDeviceConfig.SERVER.isFarmingEfficiencyEnabled()
                ? farmingEfficiency : 0F;
    }

    public int getProductCount() {
        return productCount;
    }

    public ModLootTables getLastProcessedLootTable() {
        return lastProcessedLootTable;
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        farmingEfficiency = Mth.clamp(input.getFloatOr(KEY_EFFICIENCY, 0F), 0F, MAX_EFFICIENCY);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putFloat(KEY_EFFICIENCY, Mth.clamp(farmingEfficiency, 0F, MAX_EFFICIENCY));
    }

    public static void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState, OFDeviceBlockEntity device) {
        boolean isLitOld = device.isLit();
        boolean hasChanged = false;
        ItemStack fuelStack = device.items.get(SLOT_FUEL);
        ItemStack materialStack = device.items.get(SLOT_INPUT);

        if (isLitOld) {
            device.litTimeRemaining -= device.getFuelConsumption(isFuelConsumptionDoubled(materialStack));
        }

        if ((device.isLit() || !fuelStack.isEmpty()) && !materialStack.isEmpty()) {
            ModLootTables productLootTable = device.findLootTable(materialStack);
            boolean canProcess = device.canProcess(productLootTable);

            if (!device.isLit() && canProcess) {
                // Burn new fuel stack
                device.litTimeRemaining = device.getBurnDuration(level.fuelValues(), fuelStack);
                device.litTotalTime = device.litTimeRemaining;

                if (device.isLit()) {
                    // Handle fuel
                    hasChanged = true;
                    ItemStack remainderStack = fuelStack.getCraftingRemainder();

                    if (!remainderStack.isEmpty()) {
                        device.items.set(SLOT_FUEL, remainderStack);
                    } else if (!fuelStack.isEmpty()) {
                        fuelStack.shrink(1);

                        if (fuelStack.isEmpty()) {
                            device.items.set(SLOT_FUEL, remainderStack);
                        }
                    }
                }
            }

            if (device.isLit() && device.litTimeRemaining == device.litTotalTime) {
                // When device is refueled
                device.updateFarmingEfficiency(level, blockPos);
                device.cookingTotalTime = device.getTotalProcessingTime();
            }

            if (device.isLit() && canProcess) {
                // Handle material stack
                ++device.cookingTimer;

                if (device.cookingTimer == device.cookingTotalTime) {
                    // Process completion
                    device.cookingTimer = 0;
                    device.cookingTotalTime = device.getTotalProcessingTime();
                    device.process(productLootTable);
                    hasChanged = true;
                }
            } else {
                device.cookingTimer = 0;
            }
        } else if (!device.isLit() && device.cookingTimer > 0) {
            // Fire went out before the process was complete
            device.cookingTimer = Mth.clamp(device.cookingTimer - 2, 0, device.cookingTotalTime);
        }

        if (isLitOld != device.isLit()) {
            // Switch on/off LIT of Device block
            level.setBlock(device.worldPosition, level.getBlockState(device.worldPosition).setValue(AbstractFurnaceBlock.LIT, device.isLit()), 3);
            hasChanged = true;
        }

        if (hasChanged) {
            device.setChanged();
        }
    }

    private boolean isLit() {
        return this.litTimeRemaining > 0;
    }

    private static boolean isFuelConsumptionDoubled(ItemStack stack) {
        if (stack.getItem() instanceof CobblestoneFeederItem feederItem) {
            return feederItem.type == CobblestoneFeederType.BASIC;
        }

        return false;
    }

    public void updateFarmingEfficiency(Level level, BlockPos blockPos) {
        AABB aabb = new AABB(blockPos).inflate(2.0, 1.0, 2.0);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, aabb);
        int size = entities.size();
        farmingEfficiency = Mth.clamp(size, 0F, MAX_EFFICIENCY);
    }

    public ModLootTables findLootTable(ItemStack stack) {
        return ModLootTables.find(this, stack).orElse(null);
    }

    private boolean canProcess(ModLootTables lootTableID) {
        return !items.get(SLOT_INPUT).isEmpty() && lootTableID != null;
    }

    private void process(ModLootTables productLootTable) {
        if (canProcess(productLootTable)) {
            // For tests
            productCount++;
            lastProcessedLootTable = productLootTable;

            ItemStack materialStack = items.get(SLOT_INPUT);
            List<ItemStack> productStacks = getRandomItemsFromLootTable(productLootTable);
            insertToProductSlot(productStacks);

            if (!(materialStack.getItem() instanceof CobblestoneFeederItem)) {
                materialStack.shrink(1);
            }
        }
    }

    private List<ItemStack> getRandomItemsFromLootTable(ModLootTables productLootTable) {
        if (level == null)
            return Collections.emptyList();

        var server = level.getServer();
        if (server == null)
            return Collections.emptyList();

        var lootTable = server.reloadableRegistries().getLootTable(productLootTable.getResourceKey());
        float luck = getFarmingEfficiency();

        /* DEBUG: log selected loot table
        com.github.iunius118.orefarmingdevice.OreFarmingDevice.LOGGER
                .debug("Device ({}), loot table: {}, efficiency: {}", this.getBlockPos(), lootTable.getLootTableId(), farmingEfficiency);
        // */

        return lootTable.getRandomItems(new LootParams.Builder((ServerLevel) level).withLuck(luck).create(LootContextParamSets.EMPTY));
    }

    private void insertToProductSlot(List<ItemStack> productStacks) {
        for (ItemStack productStack : productStacks) {
            ItemStack productSlotStack = items.get(SLOT_RESULT);

            if (productSlotStack.isEmpty()) {
                // Insert product item stack to empty product slot
                items.set(SLOT_RESULT, productStack.copy());
            } else if (ItemStack.isSameItem(productSlotStack, productStack)
                    && (productSlotStack.getCount() + productStack.getCount() <= Math.min(getMaxStackSize(), productSlotStack.getMaxStackSize()))) {
                // Add same product item to item stack in product slot
                productSlotStack.grow(productStack.getCount());
            } else {
                // Replace item stack in product slot with another item stack
                replaceProductStack(productStack.copy());
            }
        }
    }

    private void replaceProductStack(ItemStack newStack) {
        if (level == null) return;

        BlockPos pos = getBlockPos();
        ItemStack productSlotStack = items.get(SLOT_RESULT);
        // Eject old item stack
        level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, productSlotStack));
        // Set new item stack in product slot
        items.set(SLOT_RESULT, newStack);
    }

    @Override
    public void setItem(int slot, ItemStack newStack) {
        ItemStack oldStack = items.get(slot);
        boolean flag = !newStack.isEmpty() && ItemStack.isSameItemSameComponents(newStack, oldStack);
        items.set(slot, newStack);

        if (newStack.getCount() > getMaxStackSize()) {
            newStack.setCount(getMaxStackSize());
        }

        if (slot == SLOT_INPUT && !flag) {
            cookingTotalTime = getTotalProcessingTime();
            cookingTimer = 0;
            setChanged();
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos blockPos, BlockState blockState) {
        if (this.level != null) {
            Containers.dropContents(this.level, blockPos, this);
        }
    }

    @Override
    protected MutableComponent getDefaultName() {
        String translationKey = type.getContainerTranslationKey();
        return Component.translatable(translationKey);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new OFDeviceMenu(containerId, playerInventory, this, dataAccess);
    }
}
