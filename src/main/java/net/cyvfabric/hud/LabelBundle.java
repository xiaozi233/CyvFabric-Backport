package net.cyvfabric.hud;

import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;

import java.util.ArrayList;
import java.util.List;

public class LabelBundle {
    TextRenderer font = MinecraftClient.getInstance().textRenderer;
    public List<DraggableHUDElement> labels = new ArrayList<DraggableHUDElement>();

    public int getLabelWidth(String s) {
        return getLabelWidth(s, false);
    }

    public int getLabelWidth(String s, boolean angle) {
        font = MinecraftClient.getInstance().textRenderer;

        StringBuilder str;
        if (angle) str = new StringBuilder(s + ": 000.");
        else str = new StringBuilder(s + ": 000000.");
        for (int i = 0; i< CyvClientConfig.getInt("df",5); i++) str.append("0");
        if (angle) str.append("\u00B0");
        return font.getWidth(str.toString());
    }

    public int getLabelHeight() {
        return 9;
    }

}
