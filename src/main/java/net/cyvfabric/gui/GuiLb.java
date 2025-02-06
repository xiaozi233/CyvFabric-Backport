package net.cyvfabric.gui;

import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.parkour.LandingAxis;
import net.cyvfabric.util.parkour.LandingBlock;
import net.cyvfabric.util.parkour.LandingMode;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class GuiLb extends CyvGui {
    LandingBlock lb;
    ButtonWidget landingModeButton;
    ButtonWidget axisButton;
    ButtonWidget calculateWalls;
    ButtonWidget resetWalls;

    ButtonWidget bbToggle;
    ButtonWidget condToggle;

    public GuiLb(LandingBlock b) {
        super("Landing/Momentum Gui");
        this.lb = b;
    }

    @Override
    public void init() {
        if (lb == null) this.close();

        this.landingModeButton = new ButtonWidget(
                this.width - 155,
                5,
                150,
                20,
                Text.of("Landing Mode: " + lb.mode.toString()),
                (button) -> landingModeButtonPressed(),
                ButtonWidget.EMPTY
        );

        this.axisButton = new ButtonWidget(
                this.width - 155,
                30,
                150,
                20,
                Text.of("Axis: " + lb.axis.toString()),
                (button) -> axisButtonPressed(),
                ButtonWidget.EMPTY
        );

// Calculate Walls Button
        this.calculateWalls = new ButtonWidget(
                this.width - 155,
                105,
                150,
                20,
                Text.of("Calculate Walls"),
                (button) -> lb.calculateWalls(),
                ButtonWidget.EMPTY
        );

        this.resetWalls = new ButtonWidget(
                this.width - 155,
                130,
                150,
                20,
                Text.of("Reset Walls"),
                (button) -> {
                    lb.xMinWall = null;
                    lb.xMaxWall = null;
                    lb.zMinWall = null;
                    lb.zMaxWall = null;
                },
                ButtonWidget.EMPTY
        );

        this.bbToggle = new ButtonWidget(
                this.width - 155,
                55,
                150,
                20,
                Text.of("BB Visible: " + CyvClientConfig.getBoolean("highlightLanding", false)), // 按钮文本
                (button) -> {
                    CyvClientConfig.set("highlightLanding", !CyvClientConfig.getBoolean("highlightLanding", false));
                    bbToggle.setMessage(Text.of("BB Visible: " + CyvClientConfig.getBoolean("highlightLanding", false)));
                },
                ButtonWidget.EMPTY
        );

        this.condToggle = new ButtonWidget(
                this.width - 155,
                80,
                150,
                20,
                Text.of("Cond Visible: " + CyvClientConfig.getBoolean("highlightLandingCond", false)),
                (button) -> {
                    CyvClientConfig.set("highlightLandingCond", !CyvClientConfig.getBoolean("highlightLandingCond", false));
                    button.setMessage(Text.of("Cond Visible: " + CyvClientConfig.getBoolean("highlightLandingCond", false)));
                },
                ButtonWidget.EMPTY
        );

        if (lb.axis.equals(LandingAxis.both)) {
            lb.axis = LandingAxis.both;
            this.axisButton.setMessage(Text.of("Axis: Both"));
        } else if (lb.axis.equals(LandingAxis.z)) {
            lb.axis = LandingAxis.z;
            this.axisButton.setMessage(Text.of("Axis: Z"));
        } else {
            lb.axis = LandingAxis.x;
            this.axisButton.setMessage(Text.of("Axis: X"));
        }

        if (lb.mode.equals(LandingMode.landing)) {
            this.landingModeButton.setMessage(Text.of("Landing Mode: Landing"));
        } else if (lb.mode.equals(LandingMode.hit)) {
            this.landingModeButton.setMessage(Text.of("Landing Mode: Hit"));
        } else if (lb.mode.equals(LandingMode.z_neo)) {
            this.landingModeButton.setMessage(Text.of("Landing Mode: Z Neo"));
        } else {
            this.landingModeButton.setMessage(Text.of("Landing Mode: Enter"));
        }

    }

    void landingModeButtonPressed() {
        LandingMode mode = lb.mode;
        if (mode.equals(LandingMode.landing)) {
            lb.mode = LandingMode.hit;
            this.landingModeButton.setMessage(Text.of("Landing Mode: Hit"));
        } else if (mode.equals(LandingMode.hit)) {
            lb.mode = LandingMode.z_neo;
            this.landingModeButton.setMessage(Text.of("Landing Mode: Z Neo"));
        } else if (mode.equals(LandingMode.z_neo)) {
            lb.mode = LandingMode.enter;
            this.landingModeButton.setMessage(Text.of("Landing Mode: Enter"));
        } else {
            lb.mode = LandingMode.landing;
            this.landingModeButton.setMessage(Text.of("Landing Mode: Landing"));
        }
    }

    void axisButtonPressed() {
        LandingAxis mode = lb.axis;
        if (mode.equals(LandingAxis.both)) {
            lb.axis = LandingAxis.z;
            this.axisButton.setMessage(Text.of("Axis: Z"));
        } else if (mode.equals(LandingAxis.z)) {
            lb.axis = LandingAxis.x;
            this.axisButton.setMessage(Text.of("Axis: X"));
        } else {
            lb.axis = LandingAxis.both;
            this.axisButton.setMessage(Text.of("Axis: Both"));
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        this.renderBackground(matrices);

        landingModeButton.render(matrices, mouseX, mouseY, tickDelta);
        axisButton.render(matrices, mouseX, mouseY, tickDelta);
        calculateWalls.render(matrices, mouseX, mouseY, tickDelta);
        resetWalls.render(matrices, mouseX, mouseY, tickDelta);

        bbToggle.render(matrices, mouseX, mouseY, tickDelta);
        condToggle.render(matrices, mouseX, mouseY, tickDelta);

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        landingModeButton.mouseClicked(mouseX, mouseY, button);
        axisButton.mouseClicked(mouseX, mouseY, button);
        calculateWalls.mouseClicked(mouseX, mouseY, button);
        resetWalls.mouseClicked(mouseX, mouseY, button);
        bbToggle.mouseClicked(mouseX, mouseY, button);
        condToggle.mouseClicked(mouseX, mouseY, button);

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void tick() {
        if (lb == null) this.close();
    }

}
