package net.cyvfabric.command.calculations;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.text.DecimalFormat;

public class CommandHeight extends CyvCommand {
    public CommandHeight() {
        super("height");
        this.hasArgs = true;
        this.usage = "<airtime> [ceiling_height]";
        this.helpString = "Calculate elevation change given airtime and optional ceiling.";

    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        try {
            double vy = 0.42;
            double y = 0;
            double ticks = 0;
            double final_tick = Integer.parseInt(args[0]);
            double ceiling;

            if (args.length < 2) ceiling = 4;
            else ceiling = Double.parseDouble(args[1]);

            if (final_tick > 1000) {
                CyvFabric.sendChatMessage("Please input an airtime duration below 1000.");
                return;
            }

            if (final_tick < 1) {
                CyvFabric.sendChatMessage("Please input an airtime duration above 1.");
                return;
            }

            if (ceiling < 1.8) {
                CyvFabric.sendChatMessage("Ceiling must be above 1.8 blocks in height.");
                return;
            }

            while (ticks < final_tick) {
                y = y + vy;

                if (y > ceiling - 1.8) {
                    y = ceiling - 1.8;
                    vy = 0;
                }

                vy = (vy - 0.08) * 0.98;
                if (Math.abs(vy) < 0.00549) {
                    vy = 0;
                }
                ticks++;
            }

            DecimalFormat df = CyvFabric.df;
            CyvFabric.sendChatMessage("Change in height: " + df.format(y));

        } catch (Exception e) {
            CyvFabric.sendChatMessage("Please input a valid jump.");

        }
    }
}
