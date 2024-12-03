package net.cyvfabric.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class GuiUtils extends Screen {
    protected GuiUtils() {
        super(Text.of("GuiUtils"));
    }

    //draw rounded rect
    public static void drawRoundedRect(DrawContext context, int x, int y, int x2, int y2, int r, int color) {
        context.fill(x, y, x2, y2, 0, color);
        context.draw();
    }

}
