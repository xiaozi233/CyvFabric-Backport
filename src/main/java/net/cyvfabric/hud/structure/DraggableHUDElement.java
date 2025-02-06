package net.cyvfabric.hud.structure;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.LinkedHashMap;

public abstract class DraggableHUDElement implements IRenderer {
    protected final MinecraftClient mc;

    public ScreenPosition position;
    public boolean isVisible = true;
    public boolean isDraggable = true;
    public boolean isEnabled = true;

    public DraggableHUDElement() {
        this.mc = MinecraftClient.getInstance();

        setEnabled(isEnabled);
        this.position = this.getDefaultPosition();
    }

    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    private int getLineOffset(int lineNum) {
        return (MinecraftClient.getInstance().textRenderer.fontHeight + 3) * lineNum;
    }

    public abstract ScreenPosition getDefaultPosition();

    public LinkedHashMap<String, CyvClientConfig.ConfigValue<?>> getConfigFields() {
        LinkedHashMap<String, CyvClientConfig.ConfigValue<?>> fields = new LinkedHashMap<>();

        fields.put("enabled", new CyvClientConfig.ConfigValue<>(this.enabledByDefault()));
        fields.put("posx", new CyvClientConfig.ConfigValue<>(this.getDefaultPosition().getAbsoluteX()));
        fields.put("posy", new CyvClientConfig.ConfigValue<>(this.getDefaultPosition().getAbsoluteY()));
        fields.put("visible", new CyvClientConfig.ConfigValue<>(true));

        return fields;
    }

    public void readConfigFields() {
        try {
            this.isEnabled = CyvClientConfig.getBoolean(this.getName()+"_enabled", this.isEnabled);
            int x = CyvClientConfig.getInt(this.getName()+"_posx", this.getDefaultPosition().getAbsoluteX());
            int y = CyvClientConfig.getInt(this.getName()+"_posy", this.getDefaultPosition().getAbsoluteY());
            this.isVisible = CyvClientConfig.getBoolean(this.getName()+"_visible", this.isVisible);

            this.position = new ScreenPosition(x, y);

        } catch (Exception e) {
            CyvFabric.LOGGER.error(String.valueOf(e));
            this.position = this.getDefaultPosition();
        }
    }

    public void saveConfigFields() {
        CyvClientConfig.set(this.getName()+"_enabled", this.isEnabled);
        CyvClientConfig.set(this.getName()+"_visible", this.isVisible);
        CyvClientConfig.set(this.getName()+"_posx", this.position.getAbsoluteX());
        CyvClientConfig.set(this.getName()+"_posy", this.position.getAbsoluteY());
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void save(ScreenPosition pos) {
        this.position = pos;
    }

    public ScreenPosition load() {
        return position;
    }

    public abstract String getName();

    public abstract String getDisplayName();

    public boolean enabledByDefault() {
        return true;
    }

    protected void drawString(MatrixStack matrices, Object string, int x, int y, long color) {
        this.drawString(matrices, string, x, y, color, true);
    }

    protected void drawString(MatrixStack matrices, Object string, int x, int y, long color, boolean shadow) {
        long drawColor = (this.isVisible) ? color : 0xFFAAAAAA;
        if (shadow) {
            DrawableHelper.drawTextWithShadow(matrices, mc.textRenderer, Text.of(string.toString()), x, y, ((Long) drawColor).intValue());
        } else {
            mc.textRenderer.draw(matrices, string.toString(), x, y, ((Long) drawColor).intValue());
        }
    }


    protected void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {
        if (y2 < y1) {
            int i = y1;
            y1 = y2;
            y2 = i;
        }

        DrawableHelper.fill(matrices, x, y1 + 1, x + 1, y2, color);
    }
}
