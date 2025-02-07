package net.cyvfabric.hud.labels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.text.DecimalFormat;

public class LabelBundleHitCoords extends LabelBundle {

    public LabelBundleHitCoords() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitX";}
            public String getDisplayName() {return "Hit X";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 131);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String x = df.format(ParkourTickListener.hx);

                drawString(matrices, "Hit X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, x, pos.getAbsoluteX() + 1 + font.getWidth("Hit X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "Hit X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Hit X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitY";}
            public String getDisplayName() {return "Hit Y";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 140);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String y = df.format(ParkourTickListener.hy);

                drawString(matrices, "Hit Y: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, y, pos.getAbsoluteX() + 1 + font.getWidth("Hit Y: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "Hit Y: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Hit Y: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitZ";}
            public String getDisplayName() {return "Hit Z";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 149);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.hz);

                drawString(matrices, "Hit Z: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, z, pos.getAbsoluteX() + 1 + font.getWidth("Hit Z: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "Hit Z: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Hit Z: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });


    }

}
