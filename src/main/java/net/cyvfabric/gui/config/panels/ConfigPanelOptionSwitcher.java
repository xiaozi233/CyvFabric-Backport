package net.cyvfabric.gui.config.panels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfigPanelOptionSwitcher<T> implements ConfigPanel {
    public int sliderValue;
    public String configOption;
    public String displayString;
    public final int index;
    public final T[] sliderValues;
    public GuiModConfig screenIn;

    private final int xPosition;
    private final int yPosition;
    private final int sizeX;
    private final int sizeY;

    public ConfigPanelOptionSwitcher(int index, String configOption, String displayString, T[] options, GuiModConfig screenIn) {
        this.index = index;
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        Window sr = MinecraftClient.getInstance().getWindow();
        sizeX = screenIn.sizeX-20;
        sizeY = MinecraftClient.getInstance().textRenderer.fontHeight * 3 / 2;
        this.xPosition = sr.getScaledWidth() / 2 - screenIn.sizeX / 2 + 10;
        this.yPosition = sr.getScaledHeight() / 2 - screenIn.sizeY / 2 + 10 + (index * MinecraftClient.getInstance().textRenderer.fontHeight * 2);

        this.sliderValues = options;
        this.sliderValue = 0;
        for (int i=0; i<this.sliderValues.length; i++) {
            if (this.sliderValues[i].toString().equals(CyvClientConfig.getString(configOption, ""))) {
                this.sliderValue = i;
                break;
            }
        }

    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, int scroll) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        //text label
        DrawableHelper.drawTextWithShadow(matrices, textRenderer, Text.of(this.displayString), this.xPosition, this.yPosition + this.sizeY / 2 - textRenderer.fontHeight / 2 + 1 - scroll, 0xFFFFFFFF);

        //bg
        GuiUtils.drawRoundedRect(matrices, this.xPosition + this.sizeX / 2, this.yPosition - scroll, this.xPosition + this.sizeX, this.yPosition + this.sizeY - scroll, 3, this.mouseInBounds(mouseX, mouseY) ? CyvFabric.theme.accent1 : CyvFabric.theme.accent2);

        //amount
        DrawableHelper.drawCenteredTextWithShadow(matrices, textRenderer, Text.of(""+this.sliderValues[this.sliderValue]).asOrderedText(), this.xPosition + this.sizeX * 3 / 4, this.yPosition + this.sizeY / 2 - textRenderer.fontHeight / 2 + 1 - scroll, 0xFFFFFFFF);

    }

    @Override
    public void mouseDragged(double mouseX, double mouseY) {

    }

    @Override
    public boolean mouseInBounds(double mouseX, double mouseY) {
        return mouseX > this.xPosition + this.sizeX / 2 && mouseY > this.yPosition
                && mouseX < this.xPosition + this.sizeX && mouseY < this.yPosition + this.sizeY;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) this.sliderValue++;
        else if (mouseButton == 1) this.sliderValue--;
        if (this.sliderValue >= this.sliderValues.length) this.sliderValue = 0;
        if (this.sliderValue < 0) this.sliderValue = this.sliderValues.length-1;

        CyvClientConfig.set(this.configOption, this.sliderValues[this.sliderValue]);
        onValueChange();

    }


    @Override
    public void keyTyped(char typedChar, int keyCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        CyvClientConfig.set(this.configOption, this.sliderValues[this.sliderValue]);
    }
}