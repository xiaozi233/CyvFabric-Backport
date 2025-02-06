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

public class LabelBundleLandingPB extends LabelBundle {

    public LabelBundleLandingPB() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelPB";}
            public String getDisplayName() {return "PB";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 0);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                
                DecimalFormat df = CyvFabric.df;
                String pb;
                if (ParkourTickListener.landingBlock == null) {
                    pb = "NaN";
                } else {
                    pb = (ParkourTickListener.landingBlock.pb == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.pb);
                }

                drawString(matrices, "PB: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, pb, pos.getAbsoluteX() + 1 + font.getWidth("PB: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "PB: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("PB: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelXOffset";}
            public String getDisplayName() {return "X Offset";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 9);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String x;
                if (ParkourTickListener.landingBlock == null) {
                    x = "NaN";
                } else {
                    x = (ParkourTickListener.landingBlock.lastOffsetX == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.lastOffsetX.doubleValue());
                }

                drawString(matrices, "X Offset: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, x, pos.getAbsoluteX() + 1 + font.getWidth("X Offset: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "X Offset: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("X Offset: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelZOffset";}
            public String getDisplayName() {return "Z Offset";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 18);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String z;
                if (ParkourTickListener.landingBlock == null) {
                    z = "NaN";
                } else {
                    z = (ParkourTickListener.landingBlock.lastOffsetZ == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.lastOffsetZ.doubleValue());
                }

                drawString(matrices, "Z Offset: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, z, pos.getAbsoluteX() + 1 + font.getWidth("Z Offset: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "Z Offset: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Z Offset: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelTotalOffset";}
            public String getDisplayName() {return "Total Offset";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 45);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String pb;
                if (ParkourTickListener.landingBlock == null) {
                    pb = "NaN";
                } else {
                    pb = (ParkourTickListener.landingBlock.lastPb == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.lastPb);
                }

                drawString(matrices, "Total Offset: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, pb, pos.getAbsoluteX() + 1 + font.getWidth("Total Offset: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "Total Offset: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Total Offset: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelXPB";}
            public String getDisplayName() {return "X PB";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 27);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String x;
                if (ParkourTickListener.landingBlock == null) {
                    x = "NaN";
                } else {
                    x = (ParkourTickListener.landingBlock.pbX == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.pbX.doubleValue());
                }

                drawString(matrices, "X PB: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, x, pos.getAbsoluteX() + 1 + font.getWidth("X PB: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "X PB: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("X PB: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelZPB";}
            public String getDisplayName() {return "Z PB";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 36);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String z;
                if (ParkourTickListener.landingBlock == null) {
                    z = "NaN";
                } else {
                    z = (ParkourTickListener.landingBlock.pbZ == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.pbZ.doubleValue());
                }

                drawString(matrices, "Z PB: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, z, pos.getAbsoluteX() + 1 + font.getWidth("Z PB: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));

                drawString(matrices, "Z PB: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Z PB: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });
    }

}
