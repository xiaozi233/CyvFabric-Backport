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

public class LabelBundleSpeedVector extends LabelBundle {

    public LabelBundleSpeedVector() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelIndividualSpeeds";}
            public String getDisplayName() {return "Speeds";}
            public int getWidth() {
                TextRenderer font = mc.textRenderer;
                StringBuilder num = new StringBuilder("000000.");
                num.append("0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5))));
                return font.getWidth("Speeds: " + num + "/" + num + "/" + num);
            }
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 214);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String x = df.format(ParkourTickListener.vx);
                String y = df.format(ParkourTickListener.vy);
                String z = df.format(ParkourTickListener.vz);
                drawString(matrices, "Speeds: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, x, pos.getAbsoluteX() + 1 + font.getWidth("Speeds: ")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(matrices, "/", pos.getAbsoluteX() + 1 + font.getWidth("Speeds: " + x)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(matrices, y, pos.getAbsoluteX() + 1 + font.getWidth("Speeds: " + x + "/")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(matrices, "/", pos.getAbsoluteX() + 1 + font.getWidth("Speeds: " + x + "/" + y)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(matrices, z, pos.getAbsoluteX() + 1 + font.getWidth("Speeds: " + x + "/" + y + "/")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                StringBuilder str = new StringBuilder("0.");
                str.append("0".repeat(Math.max(0, Integer.parseInt(CyvFabric.config.configFields.get("df").value.toString()))));
                drawString(matrices, "Speeds: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str.toString(), pos.getAbsoluteX() + 1 + font.getWidth("Speeds: ")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(matrices, "/", pos.getAbsoluteX() + 1 + font.getWidth("Speeds: " + str)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str.toString(), pos.getAbsoluteX() + 1 + font.getWidth("Speeds: " + str + "/")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(matrices, "/", pos.getAbsoluteX() + 1 + font.getWidth("Speeds: " + str + "/" + str)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str.toString(), pos.getAbsoluteX() + 1 + font.getWidth("Speeds: " + str + "/" + str + "/")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelSpeedVector";}
            public String getDisplayName() {return "Speed Vector";}
            public int getWidth() {
                TextRenderer font = mc.textRenderer;
                String str = getDisplayName() + ": 00000." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5))) +
                        "/000." +
                        "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5))) +
                        "\u00B0";
                return font.getWidth(str);
            }
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 223);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String speed = df.format(Math.hypot(ParkourTickListener.vx, ParkourTickListener.vz));
                String angle = df.format(Math.toDegrees(Math.atan2((ParkourTickListener.vx == 0) ? 0 : -ParkourTickListener.vx, ParkourTickListener.vz)));

                drawString(matrices, "Speed Vector: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, speed, pos.getAbsoluteX() + 1 + font.getWidth("Speed Vector: ")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(matrices, "/", pos.getAbsoluteX() + 1 + font.getWidth("Speed Vector: " + speed)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(matrices, angle+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Speed Vector: " + speed + "/")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                StringBuilder str = new StringBuilder("0.");
                str.append("0".repeat(Math.max(0, Integer.parseInt(CyvFabric.config.configFields.get("df").value.toString()))));
                drawString(matrices, "Speed Vector: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str.toString(), pos.getAbsoluteX() + 1 + font.getWidth("Speed Vector: ")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(matrices, "/", pos.getAbsoluteX() + 1 + font.getWidth("Speed Vector: " + str)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Speed Vector: " + str + "/")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

    }

}