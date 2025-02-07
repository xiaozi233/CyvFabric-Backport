package net.cyvfabric.gui.config.panels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class ConfigPanelIntegerSlider implements ConfigPanel {
    public int sliderValue;
    public String configOption;
    public String displayString;
    public final int minValue;
    private final int maxValue;
    public final int index;
    public GuiModConfig screenIn;

    private final int xPosition;
    private final int yPosition;
    private final int sizeX;
    private final int sizeY;

    public ConfigPanelIntegerSlider(int index, String configOption, String displayString, int minValue, int maxValue, GuiModConfig screenIn) {
        this.index = index;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        Window sr = MinecraftClient.getInstance().getWindow();
        sizeX = screenIn.sizeX-20;
        sizeY = MinecraftClient.getInstance().textRenderer.fontHeight*3/2;
        this.xPosition = sr.getScaledWidth()/2-screenIn.sizeX/2+10;
        this.yPosition = sr.getScaledHeight()/2-screenIn.sizeY/2+10 + (index * MinecraftClient.getInstance().textRenderer.fontHeight * 2);
        this.sliderValue = CyvClientConfig.getInt(configOption, 0);
//        MathHelper.clamp(this.sliderValue, minValue, maxValue);

    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, int scroll) {
        //text label
        DrawableHelper.drawTextWithShadow(matrices, MinecraftClient.getInstance().textRenderer, Text.of(this.displayString), this.xPosition, this.yPosition+this.sizeY/2-MinecraftClient.getInstance().textRenderer.fontHeight/2+1-scroll, 0xFFFFFFFF);
        //bg
        GuiUtils.drawRoundedRect(matrices, this.xPosition+this.sizeX/2, this.yPosition-scroll, this.xPosition+this.sizeX, this.yPosition+this.sizeY-scroll, 3, this.mouseInBounds(mouseX, mouseY) ? CyvFabric.theme.shade1 : CyvFabric.theme.shade2);
        //slider
        GuiUtils.drawRoundedRect(matrices, this.xPosition+this.sizeX/2+ (sizeX/2 * (sliderValue-minValue)/(maxValue-minValue)) -3, this.yPosition-1-scroll,
                this.xPosition+this.sizeX/2+ (sizeX/2 * (sliderValue-minValue)/(maxValue-minValue)) +3, this.yPosition+this.sizeY+1-scroll, 1, CyvFabric.theme.mainBase());
        //amount
        DrawableHelper.drawCenteredTextWithShadow(matrices, MinecraftClient.getInstance().textRenderer, Text.of(" "+this.sliderValue).asOrderedText(), this.xPosition+this.sizeX*3/4, this.yPosition+this.sizeY/2-MinecraftClient.getInstance().textRenderer.fontHeight/2+1-scroll, 0xFFFFFFFF);

    }

    @Override
    public void mouseDragged(double mouseX, double mouseY) {
        this.sliderValue = (int)((mouseX+2-(this.xPosition+this.sizeX/2))/(float)(this.sizeX/2) * (this.maxValue - this.minValue)) + this.minValue;
        this.sliderValue = MathHelper.clamp(this.sliderValue, this.minValue, this.maxValue);
        CyvClientConfig.set(this.configOption, this.sliderValue);
        onValueChange();
    }

    @Override
    public boolean mouseInBounds(double mouseX, double mouseY) {
        return mouseX > this.xPosition + this.sizeX / 2 && mouseY > this.yPosition
                && mouseX < this.xPosition + this.sizeX && mouseY < this.yPosition + this.sizeY;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        // TODO Auto-generated method stub

    }


    @Override
    public void keyTyped(char typedChar, int keyCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        CyvClientConfig.set(this.configOption, this.sliderValue);
    }


}