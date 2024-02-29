package net.cyvfabric.gui;

import net.cyvfabric.CyvFabric;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class CyvGui extends Screen {
    private Screen parent; //parent screen

    public CyvGui(String name) {
        super(Text.of(name));
    }

    @Override //called upon GUI initialization or resizing
    protected void init() {}

    @Override //called each frame, put the drawScreen things here.
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        MinecraftClient mc = MinecraftClient.getInstance();

        this.renderInGameBackground(context); //draw bg

        context.drawCenteredTextWithShadow(textRenderer, Text.of("Gui Screen"), width/2, 10, 0xFFFFFFFF); //draw text
        int textWidth = textRenderer.getWidth("Gui Screen"); //calculate the width of the Gui Screen text

        //then draw the underline underneath the text
        context.drawHorizontalLine((width - textWidth)/2, (width + textWidth)/2, 10 + textRenderer.fontHeight, 0xFFFFFFFF);
    }

    @Override //called every tick
    public void tick() {

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override //called when the mouse is scrolled
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override //called upon GUI closing
    public void close() {
        MinecraftClient.getInstance().setScreen(parent); //sets screen to parent, or closes
    }


}
