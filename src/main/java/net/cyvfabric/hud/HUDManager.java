package net.cyvfabric.hud;

import com.google.common.collect.Sets;
import net.cyvfabric.gui.GuiMPK;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.hud.labels.*;
import net.cyvfabric.hud.structure.DraggableTextLabel;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.option.GameOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HUDManager {
    public static List<DraggableTextLabel> registeredRenderers = new ArrayList<DraggableTextLabel>();
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void init() { //initialize and create eventlistener
        HudRenderCallback.EVENT.register(HUDManager::render);

        registeredRenderers.addAll(new LabelBundleCoordinates().labels);
        registeredRenderers.addAll(new LabelBundleHitCoords().labels);
        registeredRenderers.addAll(new LabelBundleJumpCoords().labels);
        registeredRenderers.addAll(new LabelBundleLandingCoords().labels);
        registeredRenderers.addAll(new LabelBundleLandingPB().labels);
        registeredRenderers.addAll(new LabelBundleLasts().labels);
        registeredRenderers.addAll(new LabelBundleMomentumOffsets().labels);
        registeredRenderers.addAll(new LabelBundleSpeedVector().labels);
        registeredRenderers.addAll(new LabelBundleSpeeds().labels);
        registeredRenderers.addAll(new LabelBundleTickTimings().labels);
        registeredRenderers.addAll(new LabelBundleTurningAngles().labels);

    }

    private static void render(DrawContext context, float partialTicks) {
        long color2 = 0xFFFFFFFF;
        float f = mc.player.getYaw();
        f = f % 360;
        if (f < 0) f += 360;

        for (float i=0; i<360; i += 22.5) { //compass
            float distance = (Math.abs(f - i) <= 180) ? i-f : (f > 180) ? (i - (f - 360)) : ((i - 360) - f);
            if (Math.abs(distance) > 95) continue; //distance ranges from -90 to 90
            int xOffset = (int) ( distance * 0.5 * 120 / 100 );
            int height = (i%90==0) ? mc.textRenderer.fontHeight*2/3 : (i%45==0) ? mc.textRenderer.fontHeight/2 : mc.textRenderer.fontHeight/3;
            context.drawVerticalLine(mc.getWindow().getScaledWidth()/2 + xOffset,
                    1, 1+height, 0xFFFFFFFF);

            if (i==0) {//south
                context.drawText(mc.textRenderer, "S", mc.getWindow().getScaledWidth()/2 + xOffset - mc.textRenderer.getWidth("S")/2,
                        2+height, (int) color2, false);
            } else if (i==90) {//west
                context.drawText(mc.textRenderer, "W", mc.getWindow().getScaledWidth()/2 + xOffset - mc.textRenderer.getWidth("W")/2,
                        2+height, (int) color2, false);
            } else if (i==180) {//north
                context.drawText(mc.textRenderer, "N", mc.getWindow().getScaledWidth()/2 + xOffset - mc.textRenderer.getWidth("N")/2,
                        2+height, (int) color2, false);
            } else if (i==270) {//east
                context.drawText(mc.textRenderer, "E", mc.getWindow().getScaledWidth()/2 + xOffset - mc.textRenderer.getWidth("E")/2,
                        2+height, (int) color2, false);
            }

        }

        context.drawVerticalLine(mc.getWindow().getScaledWidth()/2, 1,
                mc.textRenderer.fontHeight*3/2, 0xFFFF0000);


        if (mc.options.hudHidden) return;
        if (mc.currentScreen == null || mc.currentScreen instanceof GenericContainerScreen ||
                mc.currentScreen instanceof ChatScreen || mc.currentScreen instanceof GuiModConfig
                || mc.currentScreen instanceof GuiMPK) {
            for (DraggableTextLabel renderer : registeredRenderers) {
                if (mc.currentScreen instanceof GenericContainerScreen && !renderer.renderInGui()) continue;
                if (mc.currentScreen instanceof ChatScreen && !renderer.renderInChat()) continue;

                GameOptions gameSettings = mc.options;
                if (mc.inGameHud.getDebugHud().shouldShowDebugHud() && !renderer.renderInOverlay()) continue;

                callRenderer(renderer, context, partialTicks);
            }
        }
    }

    private static void callRenderer(DraggableTextLabel renderer, DrawContext context, float partialTicks) {
        if (!renderer.isEnabled) return;
        if (!renderer.isVisible) return;

        ScreenPosition pos = renderer.load();

        if (pos == null) {
            pos = renderer.getDefaultPosition();
        }

        renderer.render(context, pos);

    }

}
