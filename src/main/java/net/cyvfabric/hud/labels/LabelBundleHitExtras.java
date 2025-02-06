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

public class LabelBundleHitExtras extends LabelBundle {

    public LabelBundleHitExtras() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitAngle";}
            public String getDisplayName() {return "Hit Angle";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 146);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.formatYaw(ParkourTickListener.hf))+"\u00B0";

                drawString(matrices, "Hit Angle: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, z, pos.getAbsoluteX() + 1 + font.getWidth("Hit Angle: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "Hit Angle: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str +"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Hit Angle: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitVector";}
            public String getDisplayName() {return "Hit Vector";}
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
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 155);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String speed = df.format(Math.hypot(ParkourTickListener.hvx, ParkourTickListener.hvz));
                String angle = df.format(Math.toDegrees(Math.atan2((ParkourTickListener.hvx == 0) ? 0 : -ParkourTickListener.hvx, ParkourTickListener.hvz)));

                drawString(matrices, "Hit Vector: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, speed, pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: ")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(matrices, "/", pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: " + speed)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(matrices, angle+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: " + speed + "/")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                StringBuilder str = new StringBuilder("0.");
                str.append("0".repeat(Math.max(0, Integer.parseInt(CyvFabric.config.configFields.get("df").value.toString()))));
                drawString(matrices, "Hit Vector: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str.toString(), pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: ")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(matrices, "/", pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: " + str)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: " + str + "/")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });
    }

}
