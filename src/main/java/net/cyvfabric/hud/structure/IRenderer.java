package net.cyvfabric.hud.structure;

import net.minecraft.client.util.math.MatrixStack;

public interface IRenderer {
    int getWidth();
    int getHeight();

    void save(ScreenPosition pos);

    ScreenPosition load();

    void render(MatrixStack matrices, ScreenPosition pos);

    default void renderDummy(MatrixStack matrices, ScreenPosition pos) {
        render(matrices, pos);
    }

    default boolean renderInChat() {
        return true;
    }

    default boolean renderInGui() {
        return false;
    }

    default boolean renderInOverlay() {
        return false;
    }

    

}
