package com.github.iunius118.orefarmingdevice.client.gui;

import com.github.iunius118.orefarmingdevice.OreFarmingDevice;
import com.github.iunius118.orefarmingdevice.inventory.OFDeviceMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class OFDeviceScreen extends AbstractContainerScreen<OFDeviceMenu> {
    private static final Identifier LIT_PROGRESS_SPRITE = OreFarmingDevice.makeId("container/of_device/lit_progress");
    private static final Identifier BURN_PROGRESS_SPRITE = OreFarmingDevice.makeId("container/of_device/burn_progress");
    private static final Identifier TEXTURE = OreFarmingDevice.makeId("textures/gui/container/of_device.png");

    public OFDeviceScreen(OFDeviceMenu menu, Inventory playerInventory, Component textComponent) {
        super(menu, playerInventory, textComponent);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float renderTicks) {
        super.extractBackground(graphics, mouseX, mouseY, renderTicks);
        int left = this.leftPos;
        int top = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, left, top, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        if (this.menu.isLit()) {
            // Render remaining burn time bar
            int litProgressHeight = Mth.ceil(this.menu.getLitProgress() * 30.0F);
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITE, 8, 30, 0, 30 - litProgressHeight, left + 78, top + 28 + 30 - litProgressHeight, 8, litProgressHeight);
        }

        // Render smelting progress bar
        int burnProgressHeigh = Mth.ceil(this.menu.getBurnProgress() * 16.0F);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS_SPRITE, 14, 16, 0, 0, left + 108, top + 35, 16, burnProgressHeigh);
    }
}
