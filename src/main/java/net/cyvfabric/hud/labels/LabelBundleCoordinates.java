package net.cyvfabric.hud.labels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.text.DecimalFormat;

public class LabelBundleCoordinates extends LabelBundle {

    public LabelBundleCoordinates() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {
                return "labelFPS";
            }

            public String getDisplayName() {
                return "FPS";
            }

            public int getWidth() {return
                    getLabelWidth(this.getDisplayName());
            }

            public int getHeight() {
                return getLabelHeight();
            }
            public ScreenPosition getDefaultPosition() {
                return new ScreenPosition(0, 1);
            }

            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                drawString(matrices, "FPS: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, MinecraftClient.currentFps, pos.getAbsoluteX() + 1 + font.getWidth("FPS: ")
                        , pos.getAbsoluteY() + 1, color2);
            }

            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                drawString(matrices, "FPS: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, "360", pos.getAbsoluteX() + 1 + font.getWidth("FPS: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {

            public String getName() {
                return "labelX";
            }

            public String getDisplayName() {
                return "X Coord";
            }

            public int getWidth() {
                return getLabelWidth(this.getDisplayName());
            }

            public int getHeight() {
                return getLabelHeight();
            }

            public ScreenPosition getDefaultPosition() {
                return new ScreenPosition(0, 10);
            }

            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String x = df.format(ParkourTickListener.x);
                drawString(matrices, "X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, x, pos.getAbsoluteX() + 1 + font.getWidth("X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }

            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));
                drawString(matrices, "X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {
                return "labelY";
            }

            public String getDisplayName() {
                return "Y Coord";
            }

            public int getWidth() {return getLabelWidth(
                    this.getDisplayName());
            }

            public int getHeight() {
                return getLabelHeight();
            }

            public ScreenPosition getDefaultPosition() {
                return new ScreenPosition(0, 19);
            }

            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String y = df.format(ParkourTickListener.y);
                drawString(matrices, "Y: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, y, pos.getAbsoluteX() + 1 + font.getWidth("Y: ")
                        , pos.getAbsoluteY() + 1, color2);
            }

            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));
                drawString(matrices, "Y: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Y: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelZ";}
            public String getDisplayName() {return "Z Coord";}
            public int getWidth() {return getLabelWidth(this.getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 28);}
            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.z);
                drawString(matrices, "Z: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, z, pos.getAbsoluteX() + 1 + font.getWidth("Z: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));
                drawString(matrices, "Z: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str, pos.getAbsoluteX() + 1 + font.getWidth("Z: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelYaw";}
            public String getDisplayName() {return "Yaw";}
            public int getWidth() {return getLabelWidth(this.getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 37);}

            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String f;
                if (/*frameBasedFacing*/false) {
                    f = df.format(ParkourTickListener.formatYaw(mc.player.getYaw()));
                } else {
                    f = (df.format((ParkourTickListener.lastTick == null) ? 0 : ParkourTickListener.formatYaw(ParkourTickListener.lastTick.f)))+"\u00B0";
                }

                drawString(matrices, "F: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, f, pos.getAbsoluteX() + 1 + font.getWidth("F: ")
                        , pos.getAbsoluteY() + 1, color2);

                if (/*ModCoordinates.this.showAxis*/ false) {
                    float absFacing = Math.abs(ParkourTickListener.formatYaw(ParkourTickListener.lastTick.f));
                    drawString(matrices, ((absFacing > 45 && absFacing < 135) ? " X" : " Z")
                            ,pos.getAbsoluteX() + 1 + font.getWidth("F: " + f), pos.getAbsoluteY() + 1, color1);
                }

            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                StringBuilder str = new StringBuilder("0.");
                str.append("0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5))));
                drawString(matrices, "F: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("F: ")
                        , pos.getAbsoluteY() + 1, color2);

                if (/*ModCoordinates.this.showAxis*/ false) {
                    drawString(matrices, " Z",pos.getAbsoluteX() + 1 + font.getWidth("F: " + str+"\u00B0"), pos.getAbsoluteY() + 1, color1);
                }
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelPitch";}
            public String getDisplayName() {return "Pitch";}
            public int getWidth() {return getLabelWidth(this.getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 46);}

            public void render(MatrixStack matrices, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String p;
                if (/*frameBasedFacing*/ false) {
                    p = df.format(ParkourTickListener.formatYaw(mc.player.getPitch()));
                } else {
                    p = df.format((ParkourTickListener.lastTick == null) ? 0 : ParkourTickListener.lastTick.p)+"\u00B0";
                }
                drawString(matrices, "Pitch: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, p, pos.getAbsoluteX() + 1 + font.getWidth("Pitch: ")
                        , pos.getAbsoluteY() + 1, color2);

            }
            public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0." + "0".repeat(Math.max(0, CyvClientConfig.getInt("df", 5)));
                drawString(matrices, "Pitch: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(matrices, str +"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Pitch: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });
    }

}
