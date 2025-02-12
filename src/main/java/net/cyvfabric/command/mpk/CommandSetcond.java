package net.cyvfabric.command.mpk;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandSetcond extends CyvCommand {
    public CommandSetcond() {
        super("setcond");
        this.helpString = "Set landing block detection zone.";
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register().then(ClientCommandManager.argument("x1", DoubleArgumentType.doubleArg())
                .then(ClientCommandManager.argument("z1", DoubleArgumentType.doubleArg())
                        .then(ClientCommandManager.argument("x2", DoubleArgumentType.doubleArg())
                                .then(ClientCommandManager.argument("z2", DoubleArgumentType.doubleArg())
                                        .executes(commandContext -> {
                                            double x1 = DoubleArgumentType.getDouble(commandContext, "x1");
                                            double x2 = DoubleArgumentType.getDouble(commandContext, "x2");
                                            double z1 = DoubleArgumentType.getDouble(commandContext, "z1");
                                            double z2 = DoubleArgumentType.getDouble(commandContext, "z2");

                                            if (ParkourTickListener.landingBlock != null) {
                                                ParkourTickListener.landingBlock.adjustCond(x1, x2, z1, z2);
                                                CyvFabric.sendChatMessage("Successfully set landing conditions.");
                                            } else {
                                                CyvFabric.sendChatMessage("No target block exists.");
                                            }
                                            return 1;
                                        })
                                )
                        )
                )
        );
    }
}