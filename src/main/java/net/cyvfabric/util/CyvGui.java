package net.cyvfabric.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class CyvGui extends Screen {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    protected static Window sr = mc.getWindow();
    private Screen parent; //parent screen

    public CyvGui(String name) {
        super(Text.of(name));
    }

    @Override //called upon GUI initialization or resizing
    protected void init() {}


    @Override //called each frame, put the drawScreen things here.
    public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
    }

    @Override //called every tick
    public void tick() {

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override //called when the mouse is scrolled
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override //called upon GUI closing
    public void close() {
        MinecraftClient.getInstance().setScreen(parent); //sets screen to parent, or closes
    }

    public static void drawBorder(MatrixStack matrices, int x, int y, int width, int height, int color) {
        fill(matrices, x, y, x + width, y + 1, color);
        fill(matrices, x, y + height - 1, x + width, y + height, color);
        fill(matrices, x, y + 1, x + 1, y + height - 1, color);
        fill(matrices, x + width - 1, y + 1, x + width, y + height - 1, color);
    }

    public static void enableScissor(int x1, int y1, int x2, int y2) {
        double d = sr.getScaleFactor();
        RenderSystem.enableScissor((int) (x1 * d), (int) (y1 * d), (int) ((x2-x1) * d), (int) ((y2-y1) * d));
    }
}
