package net.cyvfabric.hud.labels;

import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class LabelBundleTickTimings extends LabelBundle {

    public LabelBundleTickTimings() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastTiming";}
            public String getDisplayName() {return "Last Timing";}
            public int getWidth() {
                TextRenderer font = mc.textRenderer;
                return font.getWidth("Last Timing: [Timing & numbers here]");
            }
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 56);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String timing = ParkourTickListener.lastTiming;
                drawString(matrices, "Last Timing: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, timing, pos.getAbsoluteX() + 1 + font.getWidth("Last Timing: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                drawString(matrices, "Last Timing: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, "[Timing]", pos.getAbsoluteX() + 1 + font.getWidth("Last Timing: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelAirtime";}
            public String getDisplayName() {return "Airtime";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 65);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                int airtime = ParkourTickListener.lastAirtime;

                drawString(matrices, "Airtime: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, airtime, pos.getAbsoluteX() + 1 + font.getWidth("Airtime: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                drawString(matrices, "Airtime: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, 0, pos.getAbsoluteX() + 1 + font.getWidth("Airtime: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelTier";}
            public String getDisplayName() {return "Tier";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 137);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                int airtime = 12 - ParkourTickListener.lastAirtime;

                drawString(matrices, "Tier: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, airtime, pos.getAbsoluteX() + 1 + font.getWidth("Tier: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                drawString(matrices, "Tier: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, 0, pos.getAbsoluteX() + 1 + font.getWidth("Tier: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });
    }

}
