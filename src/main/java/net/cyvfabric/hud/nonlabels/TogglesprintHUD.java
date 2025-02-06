package net.cyvfabric.hud.nonlabels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.text.DecimalFormat;

public class TogglesprintHUD extends DraggableHUDElement {
    @Override
    public String getName() {return "togglesprintHUD";}

    @Override
    public String getDisplayName() {return "Togglesprint HUD";}

    @Override
    public int getWidth() {
        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        return font.getWidth("[Sprint Toggled]");
    }

    @Override
    public int getHeight() {return 9;}

    @Override
    public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 232);}

    @Override
    public void render(MatrixStack matrices, ScreenPosition pos) {
        if (!this.isVisible) return;
        if (!KeybindingTogglesprint.sprintToggled) return;
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        TextRenderer font = mc.textRenderer;
        DecimalFormat df = CyvFabric.df;
        String p;
        drawString(matrices, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString(matrices, "Sprint Toggled", pos.getAbsoluteX() + 1 + font.getWidth("["), pos.getAbsoluteY() + 1, color2);
        drawString(matrices, "]", pos.getAbsoluteX() + 1+ font.getWidth("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);

    }

    @Override
    public void renderDummy(MatrixStack matrices, ScreenPosition pos) {
        int d = CyvClientConfig.getInt("df",5);
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        TextRenderer font = mc.textRenderer;
        drawString(matrices, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString(matrices, "Sprint Toggled", pos.getAbsoluteX() + 1 + font.getWidth("["), pos.getAbsoluteY() + 1, color2);
        drawString(matrices, "]", pos.getAbsoluteX() + 1+ font.getWidth("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);
    }

}
