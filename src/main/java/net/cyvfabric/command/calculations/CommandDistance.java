package net.cyvfabric.command.calculations;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.text.DecimalFormat;

public class CommandDistance extends CyvCommand {
    public CommandDistance() {
        super("distance");
        this.hasArgs = true;
        this.usage = "<x> <y>";
        this.helpString = "Calculate raw distance and angle of a diagonal jump given x and z.";

        this.aliases.add("dist");
        this.aliases.add("rawdistance");
        this.aliases.add("rawdist");

    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .then(ClientCommandManager.argument("x", DoubleArgumentType.doubleArg(0))
                        .then(ClientCommandManager.argument("z", DoubleArgumentType.doubleArg(0))
                                .executes(commandContext -> {
                                            double x = DoubleArgumentType.getDouble(commandContext, "x");
                                            double z = DoubleArgumentType.getDouble(commandContext, "z");

                                            if (x < 0.6) {
                                                x = 0.6;
                                            }

                                            if (z < 0.6) {
                                                z = 0.6;
                                            }

                                            DecimalFormat df = CyvFabric.df;

                                            double rawDistance = Math.sqrt(Math.pow((x - 0.6),2) + Math.pow((z - 0.6),2));
                                            double angle = Math.atan((z-0.6)/(x-0.6))*180/Math.PI;

                                            CyvFabric.sendChatMessage("Distance for jump dimensions " + x + " x " + z + ":"
                                                    + "\nJump length: " + df.format(rawDistance + 0.6) + "b"
                                                    + "\nRaw distance: " + df.format(rawDistance) + "m"
                                                    + "\nAngle: " + df.format(angle) + "\u00B0");
                                            return 1;
                                        }
                                )
                        )
                );
    }
}
