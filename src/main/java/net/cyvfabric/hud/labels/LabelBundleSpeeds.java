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

public class LabelBundleSpeeds extends LabelBundle {

    public LabelBundleSpeeds() {
        labels.add(new DraggableHUDElement() {
            public String getName() {return "labelXSpeed";}
            public String getDisplayName() {return "X Speed";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 187);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String x = df.format(ParkourTickListener.vx);
                drawString(matrices, "X Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, x, pos.getAbsoluteX() + 1 + font.getWidth("X Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0." + "0".repeat(Math.max(0, Integer.parseInt(CyvFabric.config.configFields.get("df").value.toString())));
                drawString(matrices, "X Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("X Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        labels.add(new DraggableHUDElement() {
            public String getName() {return "labelYSpeed";}
            public String getDisplayName() {return "Y Speed";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 196);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String y = df.format(ParkourTickListener.vy);
                drawString(matrices, "Y Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, y, pos.getAbsoluteX() + 1 + font.getWidth("Y Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0." + "0".repeat(Math.max(0, Integer.parseInt(CyvFabric.config.configFields.get("df").value.toString())));
                drawString(matrices, "Y Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Y Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        labels.add(new DraggableHUDElement() {
            public String getName() {return "labelZSpeed";}
            public String getDisplayName() {return "Z Speed";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 205);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.vz);
                drawString(matrices, "Z Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, z, pos.getAbsoluteX() + 1 + font.getWidth("Z Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0." + "0".repeat(Math.max(0, Integer.parseInt(CyvFabric.config.configFields.get("df").value.toString())));
                drawString(matrices, "Z Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Z Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

    }
}
