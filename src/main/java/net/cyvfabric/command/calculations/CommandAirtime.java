package net.cyvfabric.command.calculations;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandAirtime extends CyvCommand {
    public CommandAirtime() {
        super("airtime");
        this.hasArgs = true;
        this.usage = "<y> [ceiling_height]";
        this.helpString = "Calculate airtime in ticks given elevation change and optional ceiling.";

        this.aliases.add("duration");

    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        try {
            double vy = 0.42;
            double y = 0;
            double ticks = 0;
            double end_y = Double.parseDouble(args[0]);
            double ceiling;

            if (args.length < 2) ceiling = 4;
            else ceiling = Double.parseDouble(args[1]);

            if (end_y < -1000) {
                CyvFabric.sendChatMessage("Please input a jump height above -1000.");
                return;
            }

            if (ceiling < 1.8) {
                CyvFabric.sendChatMessage("Ceiling must be above 1.8 blocks in height.");
                return;
            }

            while ((y > (end_y) || vy > 0) && end_y <= 1.252) {
                y = y + vy;

                if (y > ceiling - 1.8) {
                    y = ceiling - 1.8;
                    vy = 0;
                }

                vy = (vy - 0.08) * 0.98;
                if (Math.abs(vy) < (0.003)) vy = 0;
                ticks++;
            }

            CyvFabric.sendChatMessage("Airtime: " + Double.toString(ticks) + " ticks");

        } catch (Exception e) {
            CyvFabric.sendChatMessage("Please input a valid jump.");
        }
    }
}
