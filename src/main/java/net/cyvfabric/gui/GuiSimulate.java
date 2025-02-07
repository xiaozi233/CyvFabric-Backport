package net.cyvfabric.gui;

import mcpk.Parser;
import mcpk.Player;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.util.ArrayList;

//gui for the in-game movement simulator
public class GuiSimulate extends CyvGui {
    public static ArrayList<String> chatHistory = new ArrayList<>();

    public boolean repeatEventsEnabled;
    TextFieldWidget input;
    ButtonWidget button;
    int chatHistoryIndex = 0;

    public GuiSimulate() {
        super("Movement Simulator");
    }

    @Override
    protected void init() {
        input = new TextFieldWidget(textRenderer, width/2-width*23/80, (int) (height*0.40-10), width*23/40, 20, Text.of(""));
        button = new ButtonWidget(
                width / 2 - 50,
                height * 3 / 5 - 10,
                100,
                20,
                Text.of("Calculate"),
                (widget) -> {},
                ButtonWidget.EMPTY
        );

        input.setMaxLength(65536);
        this.chatHistoryIndex = 0;
        this.input.setTextFieldFocused(true);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        Window sr = MinecraftClient.getInstance().getWindow();
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        int x1 = (int) (sr.getScaledWidth() * 0.20);
        int y1 = (int) (sr.getScaledHeight() * 0.30);
        int x2 = (int) (sr.getScaledWidth() * 0.80);
        int y2 = (int) (sr.getScaledHeight() * 0.50);

        super.renderBackground(matrices); //background tint
        GuiUtils.drawRoundedRect(matrices, x1-2, y1-2, x2+2, y2+2, 9, 0x88000000);
        GuiUtils.drawRoundedRect(matrices, x1, y1, x2, y2, 7, 0x11000000);

        input.render(matrices, mouseX, mouseY, tickDelta);
        button.render(matrices, mouseX, mouseY, tickDelta);

        DrawableHelper.drawCenteredTextWithShadow(matrices, textRenderer, Text.of("Movement Simulator").asOrderedText(), x1+46, y1-15, 0xFFFFFFFF);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        input.charTyped(chr, modifiers);
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            this.close(); //close the gui
            String text = input.getText(); //parser shit

            if (text.isEmpty() || text.equals(" ")) {}
            else {
                if (chatHistory.isEmpty()) {
                    chatHistory.add(text);
                } else {
                    if (!chatHistory.get(chatHistory.size()-1).equals(text)) {
                        chatHistory.add(text);
                    }
                }

                output(text);
                return true;
            }
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_UP) { //scroll up
            if (chatHistoryIndex < chatHistory.size()) {
                chatHistoryIndex++;
                input.setText(chatHistory.get(chatHistory.size()-chatHistoryIndex));
                return true;
            }

        } else if (keyCode == GLFW.GLFW_KEY_DOWN) { //scroll down
            if (chatHistoryIndex > 0) {
                chatHistoryIndex--;
                if (chatHistoryIndex == 0) {
                    input.setText("");
                } else {
                    input.setText(chatHistory.get(chatHistory.size()-chatHistoryIndex));
                }
                return true;
            }

        }

        input.keyPressed(keyCode, scanCode, modifiers);

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        input.mouseClicked(mouseX, mouseY, mouseButton);
        if (button.mouseClicked(mouseX, mouseY, mouseButton)) {
            this.close();
            String text = input.getText(); //parser shit

            if (text.isEmpty() || text.equals(" ")) {}
            else {
                if (chatHistory.isEmpty()) {
                    chatHistory.add(text);
                } else {
                    if (!chatHistory.get(chatHistory.size()-1).equals(text)) {
                        chatHistory.add(text);
                    }
                }
                output(text);

            }}
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void output(String text) {
        new Thread(() -> {
            Player player = new Player();
            DecimalFormat df = CyvFabric.df;
            Player.df = (byte) df.getMaximumFractionDigits();
            Parser parser = new Parser();

            try {
                parser.parse(player, text);
            } catch (Exception e) {
                CyvFabric.sendChatMessage("Parsing failed.");
                return;
            }

            double z = player.z;
            double vz = player.vz;
            double x = player.x;
            double vx = player.vx;

            double vector = Math.sqrt(vx*vx + vz*vz);
            double angle = Math.atan2(-vx,vz) * 180d/Math.PI;

            CyvFabric.sendChatMessage("Simulated parsed string: \247o" + text + "\n\247r"
                    + "z: " + df.format(z) + "\n"
                    + "vz: " + df.format(vz) + "\n"
                    + "x: " + df.format(x) + "\n"
                    + "vx: " + df.format(vx) + "\n"
                    + "Speed Vector: " + df.format(vector) + ", " + df.format(angle) + "\u00B0");
        }).start();

    }

}
