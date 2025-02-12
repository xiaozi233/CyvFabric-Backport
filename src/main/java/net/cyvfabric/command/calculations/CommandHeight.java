package net.cyvfabric.command.calculations;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.text.DecimalFormat;

public class CommandHeight extends CyvCommand {
    public CommandHeight() {
        super("height");
        this.hasArgs = true;
        this.usage = "<airtime> [ceiling_height]";
        this.helpString = "Calculate elevation change given airtime and optional ceiling.";

    }

    public int run(CommandContext<FabricClientCommandSource> context, boolean celling) {

        double final_tick = IntegerArgumentType.getInteger(context,"ticks");
        double ceiling_y = celling ? DoubleArgumentType.getDouble(context, "celling") : 4;
        double vy = 0.42;
        double y = 0;
        double ticks = 0;

        while (ticks < final_tick) {
            y = y + vy;

            if (y > ceiling_y - 1.8) {
                y = ceiling_y - 1.8;
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
        return 1;
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .then(ClientCommandManager.argument("ticks", IntegerArgumentType.integer(1, 1000))
                        .executes(commandContext -> run(commandContext, false))
                        .then(ClientCommandManager.argument("celling", DoubleArgumentType.doubleArg(1.8))
                                .executes(commandContext -> run(commandContext, true))
                        )
                );
    }
}
