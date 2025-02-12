package net.cyvfabric.command.calculations;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandAirtime extends CyvCommand {
    public CommandAirtime() {
        super("airtime");
        this.hasArgs = true;
        this.usage = "<y> [ceiling_height]";
        this.helpString = "Calculate airtime in ticks given elevation change and optional ceiling.";

        this.aliases.add("duration");

    }

    public int run(CommandContext<FabricClientCommandSource> context, boolean celling) {
        double end_y = DoubleArgumentType.getDouble(context, "end_y");
        double celling_y = celling ? DoubleArgumentType.getDouble(context, "celling") : 4;
        double vy = 0.42;
        double y = 0;
        double ticks = 0;

        while ((y > (end_y) || vy > 0) && end_y <= 1.252) {
            y = y + vy;

            if (y > celling_y - 1.8) {
                y = celling_y - 1.8;
                vy = 0;
            }

            vy = (vy - 0.08) * 0.98;
            if (Math.abs(vy) < (0.003)) vy = 0;
            ticks++;
        }

        CyvFabric.sendChatMessage("Airtime: " + ticks + " ticks");
        return 1;
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .then(ClientCommandManager.argument("end_y", DoubleArgumentType.doubleArg(-1000))
                        .executes(commandContext -> run(commandContext, false))
                        .then(ClientCommandManager.argument("celling", DoubleArgumentType.doubleArg(-1.8))
                                .executes(commandContext -> run(commandContext, true))
                        )
                );
    }
}
