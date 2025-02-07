package net.cyvfabric.gui.config.panels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class ConfigPanelDecimalEntry implements ConfigPanel {
    public TextFieldWidget field;
    public String configOption;
    public String displayString;
    public final int index;
    public GuiModConfig screenIn;

    private final int xPosition;
    private final int yPosition;
    private final int sizeX;
    private final int sizeY;

    private double minBound = -Double.MAX_VALUE;
    private double maxBound = Double.MAX_VALUE;

    public ConfigPanelDecimalEntry(int index, String configOption, String displayString, double min, double max, GuiModConfig screenIn) {
        this(index, configOption, displayString, screenIn);
        this.minBound = min;
        this.maxBound = max;
    }

    public ConfigPanelDecimalEntry(int index, String configOption, String displayString, GuiModConfig screenIn) {
        this.index = index;
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        Window sr = MinecraftClient.getInstance().getWindow();
        sizeX = screenIn.sizeX-20;
        sizeY = MinecraftClient.getInstance().textRenderer.fontHeight*3/2;
        this.xPosition = sr.getScaledWidth()/2-screenIn.sizeX/2+10;
        this.yPosition = sr.getScaledHeight()/2-screenIn.sizeY/2+10 + (index * MinecraftClient.getInstance().textRenderer.fontHeight * 2);

        this.field = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, this.xPosition+this.sizeX/2+2, this.yPosition+this.sizeY/2-MinecraftClient.getInstance().textRenderer.fontHeight/2+1, this.sizeX/2-4, MinecraftClient.getInstance().textRenderer.fontHeight/2, Text.of(""));
        this.field.setText(CyvClientConfig.getDouble(configOption, 0)+"");
        this.field.setDrawsBackground(false);
        this.field.setVisible(true);

    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, int scroll) {
        DrawableHelper.drawTextWithShadow(matrices, MinecraftClient.getInstance().textRenderer, Text.of(this.displayString), this.xPosition, this.yPosition+this.sizeY/2-MinecraftClient.getInstance().textRenderer.fontHeight/2+1-scroll, 0xFFFFFFFF);
        //bg
        GuiUtils.drawRoundedRect(matrices, this.xPosition+this.sizeX/2, this.yPosition-scroll, this.xPosition+this.sizeX, this.yPosition+this.sizeY-scroll, 3, this.mouseInBounds(mouseX, mouseY) ? CyvFabric.theme.shade1 : CyvFabric.theme.shade2);


        this.field.y = this.yPosition+this.sizeY/2-MinecraftClient.getInstance().textRenderer.fontHeight/2+1-scroll;
        this.field.render(matrices, mouseX, mouseY, MinecraftClient.getInstance().getTickDelta());
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
        this.field.mouseClicked(mouseX, mouseY, mouseButton);

        if (!(mouseX >= field.x && mouseX <= field.x + field.getWidth() && mouseY >= field.y && mouseY <= field.y + field.getHeight())) {
            this.unselect();
        } else {
            this.select();
        }
    }


    @Override
    public void keyTyped(char typedChar, int keyCode) {
        this.field.charTyped(typedChar, keyCode);

    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        this.field.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void save() {
        double val;
        try {
            val = MathHelper.clamp(Double.parseDouble(this.field.getText()), this.minBound, this.maxBound);
            this.field.setText(val+"");
            CyvClientConfig.set(this.configOption, val);
        } catch (Exception ignored) {}
    }

    @Override
    public void select() {
        this.field.setTextFieldFocused(true);
    }

    @Override
    public void unselect() {
        this.field.setTextFieldFocused(false);
        try {
            double val = MathHelper.clamp(Double.parseDouble(this.field.getText()), this.minBound, this.maxBound);
            this.field.setText(val+"");
            CyvClientConfig.set(this.configOption, val);
        } catch (Exception ignored) {}
        onValueChange();
    }


}