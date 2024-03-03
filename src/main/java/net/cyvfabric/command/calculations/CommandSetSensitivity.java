package net.cyvfabric.command.calculations;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;

import java.text.DecimalFormat;

public class CommandSetSensitivity extends CyvCommand {
    public CommandSetSensitivity() {
        super("setsensitivity");
        this.hasArgs = true;
        this.usage = "<value from 0.0 to 1.0>";
        this.helpString = "Sets your current sensitivity. (100% = 0.5, ranges from 0.0 to 1.0)";

        this.aliases.add("ss");
        this.aliases.add("setsens");

    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        try {
            double sensitivity = Double.parseDouble(args[0]);

            //check
            if (sensitivity > 1 || sensitivity < 0) {
                CyvFabric.sendMessage("Sensitivity cannot be greater than 1 (200%) or less than 0.");
                return;

            }

            MinecraftClient.getInstance().options.getMouseSensitivity().setValue(sensitivity);
            MinecraftClient.getInstance().options.write();

            double percentage = 200 * sensitivity;
            DecimalFormat df = CyvFabric.df;

            CyvFabric.sendMessage("Sensitivity set to " + df.format(percentage) + "%");


        } catch (Exception e) {
            CyvFabric.sendMessage("Please input a valid sensitivity constant (100% = 0.5)");
            return;

        }
    }
}
