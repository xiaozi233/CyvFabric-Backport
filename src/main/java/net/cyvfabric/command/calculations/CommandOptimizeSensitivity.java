package net.cyvfabric.command.calculations;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.GuiHandler;
import net.cyvfabric.gui.GuiSimulate;
import net.cyvfabric.util.CyvCommand;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;

import java.text.DecimalFormat;

public class CommandOptimizeSensitivity extends CyvCommand {
    public CommandOptimizeSensitivity() {
        super("optimizesensitivity");
        this.usage = "<angle>";
        this.helpString = "Optimize current sensitivity to be able to turn given degree in a whole number of pixels.";

        this.aliases.add("os");
        this.aliases.add("optimizesens");
        this.aliases.add("optimisesens");
        this.aliases.add("optimisesensitivity");
    }

    @Override
    public void run(CommandContext<ServerCommandSource> context, String[] args) {
        try {
            //calculations
            double angle = Math.abs(Float.parseFloat(args[0]));
            double currentSens = MinecraftClient.getInstance().options.getMouseSensitivity().getValue();

            double currentPx = (angle / 1.2) / Math.pow((currentSens * 0.6) + 0.2, 3);
            double newSens = (Math.cbrt((angle / (1.2 * Math.round(currentPx)))) - 0.2) / 0.6;

            //check
            if (currentSens > 1 || currentSens < 0) {
                CyvFabric.sendMessage("Sensitivity optimising unavailable for sensitivities above 200% or below 0%.");
                return;

            }

            //response
            if (Math.round(currentPx) == 0) {
                CyvFabric.sendMessage("Angle is too low to optimize for.");
            } else {
                MinecraftClient.getInstance().options.getMouseSensitivity().setValue(newSens);
                MinecraftClient.getInstance().options.write();

                double percentage = 200 * newSens;
                DecimalFormat df = CyvFabric.df;
                CyvFabric.sendMessage("Sensitivity optimized to " + df.format(percentage) + "%"
                        + "\nPixels of turning: " + Math.round(currentPx));

            }

        } catch (Exception e) {
            CyvFabric.sendMessage("Please input a valid turning angle to optimize for.");

        }
    }
}
