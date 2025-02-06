package net.cyvfabric.gui.config;

import net.minecraft.client.util.math.MatrixStack;

public interface ConfigPanel {
    boolean mouseInBounds(double mouseX, double mouseY);
    void mouseClicked(double mouseX, double mouseY, int mouseButton);
    void keyTyped(char typedChar, int keyCode);
    default void keyPressed(int keyCode, int scanCode, int modifiers) {}
    void draw(MatrixStack matrices, int mouseX, int mouseY, int scroll);
    default void update() {}
    void mouseDragged(double mouseX, double mouseY);
    void save();
    default void select() {}
    default void unselect() {}

    default void onValueChange() {}

}