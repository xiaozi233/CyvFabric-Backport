package net.cyvfabric.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class GuiUtils extends Screen {
    protected GuiUtils() {
        super(Text.of("GuiUtils"));
    }

    //draw rounded rect
    public static void drawRoundedRect(MatrixStack matrices, int x, int y, int x2, int y2, int r, int color) {
        fill(matrices, x, y, x2, y2, color);
//        BufferRenderer.draw(Tessellator.getInstance().getBuffer());
    }

}
