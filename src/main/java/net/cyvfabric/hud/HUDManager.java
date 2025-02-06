package net.cyvfabric.hud;

import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.gui.GuiMPK;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.hud.labels.*;
import net.cyvfabric.hud.nonlabels.DirectionHUD;
import net.cyvfabric.hud.nonlabels.KeystrokesHUD;
import net.cyvfabric.hud.nonlabels.TogglesprintHUD;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class HUDManager {
    public static final List<DraggableHUDElement> registeredRenderers = new ArrayList<>();
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void init() { //initialize and create eventlistener
        HudRenderCallback.EVENT.register(HUDManager::render);

        registeredRenderers.add(new DirectionHUD());
        registeredRenderers.add(new TogglesprintHUD());
        registeredRenderers.add(new KeystrokesHUD());

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
        registeredRenderers.addAll(new LabelBundleHitExtras().labels);

    }

    private static void render(MatrixStack matrices, float tickDelta) {
        if (mc.options.hudHidden) return;

        if (CommandMacro.macroRunning > 0) { //macrorunning
            Window sr = mc.getWindow();
            mc.textRenderer.draw(matrices, "MACRO",
                    (float) sr.getScaledWidth() /2 - (float) mc.textRenderer.getWidth("MACRO") / 2,
                    (float) sr.getScaledHeight() /5, 0xFFFF0000);
        }

        if (mc.currentScreen == null || mc.currentScreen instanceof GenericContainerScreen ||
                mc.currentScreen instanceof ChatScreen || mc.currentScreen instanceof GuiModConfig
                || mc.currentScreen instanceof GuiMPK) {
            for (DraggableHUDElement renderer : registeredRenderers) {
                if (mc.currentScreen instanceof GenericContainerScreen && !renderer.renderInGui()) continue;
                if (mc.currentScreen instanceof ChatScreen && !renderer.renderInChat()) continue;

                GameOptions gameOptions = mc.options;
//                if (mc.inGameHud.getDebugHud().shouldShowDebugHud() && !renderer.renderInOverlay()) continue;
                if (gameOptions.debugEnabled && !gameOptions.hudHidden &&!renderer.renderInOverlay()) continue;

                callRenderer(renderer, matrices, tickDelta);
            }
        }
    }

    private static void callRenderer(DraggableHUDElement renderer, MatrixStack matrices, float tickDelta) {
        if (!renderer.isEnabled) return;
        if (!renderer.isVisible) return;

        ScreenPosition pos = renderer.load();

        if (pos == null) {
            pos = renderer.getDefaultPosition();
        }

        renderer.render(matrices, pos);

    }

}
