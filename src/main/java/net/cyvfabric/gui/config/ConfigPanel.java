package net.cyvfabric.gui.config;

import net.minecraft.client.gui.DrawContext;

public interface ConfigPanel {
    public boolean mouseInBounds(double mouseX, double mouseY);
    public void mouseClicked(double mouseX, double mouseY, int mouseButton);
    public void keyTyped(char typedChar, int keyCode);
    public default void keyPressed(int keyCode, int scanCode, int modifiers) {}
    public void draw(DrawContext context, int mouseX, int mouseY, int scroll);
    public default void update() {}
    public void mouseDragged(double mouseX, double mouseY);
    public void save();
    public default void select() {}
    public default void unselect() {}

    public default void onValueChange() {}

}