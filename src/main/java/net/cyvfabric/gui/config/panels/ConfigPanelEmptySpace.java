package net.cyvfabric.gui.config.panels;

import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

public class ConfigPanelEmptySpace implements ConfigPanel {
    public final int index;
    public GuiModConfig screenIn;

    private final int xPosition;
    private final int yPosition;
    private final int sizeX;
    private final int sizeY;

    public ConfigPanelEmptySpace(int index, GuiModConfig screenIn) {
        this.index = index;
        this.screenIn = screenIn;

        Window sr = MinecraftClient.getInstance().getWindow();
        sizeX = screenIn.sizeX - 20;
        sizeY = MinecraftClient.getInstance().textRenderer.fontHeight * 3 / 2;
        this.xPosition = sr.getScaledWidth() / 2 - screenIn.sizeX / 2 + 10;
        this.yPosition = sr.getScaledHeight() / 2 - screenIn.sizeY / 2 + 10 + (index * MinecraftClient.getInstance().textRenderer.fontHeight * 2);

    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, int scroll){
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY) {}

    @Override
    public boolean mouseInBounds(double mouseX, double mouseY) {
        return mouseX > this.xPosition + this.sizeX / 2 && mouseY > this.yPosition
                && mouseX < this.xPosition + this.sizeX && mouseY < this.yPosition + this.sizeY;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {}


    @Override
    public void keyTyped(char typedChar, int keyCode) {}

    @Override
    public void save() {}
}