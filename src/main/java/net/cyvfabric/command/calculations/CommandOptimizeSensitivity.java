package net.cyvfabric.command.calculations;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

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
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .then(ClientCommandManager.argument("angle", DoubleArgumentType.doubleArg())
                        .executes(commandContext -> {
                            double angle = Math.abs(DoubleArgumentType.getDouble(commandContext, "angle"));
                            double currentSens = MinecraftClient.getInstance().options.mouseSensitivity;

                            double currentPx = (angle / 1.2) / Math.pow((currentSens * 0.6) + 0.2, 3);
                            double newSens = (Math.cbrt((angle / (1.2 * Math.round(currentPx)))) - 0.2) / 0.6;

                            //check
                            if (currentSens > 1 || currentSens < 0) {
                                CyvFabric.sendChatMessage("Sensitivity optimising unavailable for sensitivities above 200% or below 0%.");
                                return 1;
                            }

                            //response
                            if (Math.round(currentPx) == 0) {
                                CyvFabric.sendChatMessage("Angle is too low to optimize for.");
                            } else {
                                MinecraftClient.getInstance().options.mouseSensitivity = newSens;
                                MinecraftClient.getInstance().options.write();

                                double percentage = 200 * newSens;
                                CyvFabric.sendChatMessage("Sensitivity optimized to " + CyvFabric.df.format(percentage) + "%"
                                        + "\nPixels of turning: " + Math.round(currentPx));

                            }
                            return 1;
                        })
                );
    }
}
