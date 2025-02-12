package net.cyvfabric.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandInertia extends CyvCommand {
    public CommandInertia() {
        super("inertia");
        this.hasArgs = true;
        this.usage = "[subcategory]";
        this.helpString = "Configure inertia listener. Use /cyv help inertia for more info.";
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        LiteralArgumentBuilder<FabricClientCommandSource> modeCommand = ClientCommandManager.literal("mode")
                .then(ClientCommandManager.literal("x")
                        .executes(commandContext -> {
                            CyvClientConfig.set("inertiaAxis", 'x');
                            CyvFabric.sendChatMessage("Inertia axis set to x.");
                            return 1;
                        })
                ).then(ClientCommandManager.literal("z")
                        .executes(commandContext -> {
                            CyvClientConfig.set("inertiaAxis", 'z');
                            CyvFabric.sendChatMessage("Inertia axis set to z.");
                            return 1;
                        })
                );
        return super.register()
                .then(ClientCommandManager.literal("toggle")
                        .executes(commandContext -> {
                            if (CyvClientConfig.getBoolean("inertiaEnabled", false)) {
                                CyvClientConfig.set("inertiaEnabled", false);
                                CyvFabric.sendChatMessage("Inertia listener toggled off");
                            } else {
                                CyvClientConfig.set("inertiaEnabled", true);
                                CyvFabric.sendChatMessage("Inertia listener toggled on");
                            }
                            return 1;
                        })
                ).then(ClientCommandManager.literal("min")
                        .then(ClientCommandManager.argument("min", DoubleArgumentType.doubleArg())
                                .executes(commandContext -> {
                                    double min = DoubleArgumentType.getDouble(commandContext, "min");
                                    CyvClientConfig.set("inertiaMin", min);
                                    CyvFabric.sendChatMessage("Inertia minimum speed threshold set to " + min + ".");
                                    return 1;
                                })
                        )
                ).then(ClientCommandManager.literal("max")
                        .then(ClientCommandManager.argument("max", DoubleArgumentType.doubleArg())
                                .executes(commandContext -> {
                                    double max = DoubleArgumentType.getDouble(commandContext, "max");
                                    CyvClientConfig.set("inertiaMax", max);
                                    CyvFabric.sendChatMessage("Inertia maximum speed threshold set to " + max + ".");
                                    return 1;
                                })
                        )
                ).then(modeCommand)
                .then(ClientCommandManager.literal("axis").redirect(modeCommand.build())
                ).then(ClientCommandManager.literal("tick")
                        .then(ClientCommandManager.argument("ticks", IntegerArgumentType.integer(1, 12))
                                .executes(commandContext -> {
                                    int ticks = IntegerArgumentType.getInteger(commandContext, "ticks");
                                    CyvClientConfig.set("inertiaTick", ticks);
                                    CyvFabric.sendChatMessage("Inertia tick set to " + ticks + ".");
                                    return 1;
                                })
                        )
                ).then(ClientCommandManager.literal("ground")
                        .then(ClientCommandManager.literal("normal")
                                .executes(commandContext -> {
                                    CyvClientConfig.set("inertiaGroundType", "normal");
                                    CyvFabric.sendChatMessage("Ground type set to normal.");
                                    return 1;
                                })
                        )
                        .then(ClientCommandManager.literal("ice")
                                .executes(commandContext -> {
                                    CyvClientConfig.set("inertiaGroundType", "ice");
                                    CyvFabric.sendChatMessage("Ground type set to ice.");
                                    return 1;
                                })
                        )
                        .then(ClientCommandManager.literal("slime")
                                .executes(commandContext -> {
                                    CyvClientConfig.set("inertiaGroundType", "slime");
                                    CyvFabric.sendChatMessage("Ground type set to slime.");
                                    return 1;
                                })
                        )
                );
    }
    @Override
    public String getDetailedHelp() {
        return """
                Toggle on/off or modify inertia listener settings. Subcategories:\
                
                [toggle]: Toggle listener on or off.\
                
                [min/max]: Set minimum and maximum speed bounds for a chat message.\
                
                [mode]: Set listener to x or z axis.\
                
                [tick]: Set airtick to check for inertia.\
                
                [ground]: Change ground type between ice, slime, or normal.""";
    }

}
