package net.cyvfabric.command.config;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandColor1 extends CyvCommand {
    public CommandColor1() {
        super("color1");
        this.hasArgs = true;
        this.usage = "[color]";
        this.helpString = "Sets color1 for display and chat.";
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register() {
        LiteralArgumentBuilder<FabricClientCommandSource> command = super.register();
        CyvClientColorHelper.COLORS.forEach(color -> command.then(ClientCommandManager.literal(color)
                .executes(commandContext -> {
                    CyvClientColorHelper.setColor1(color);
                    CyvFabric.sendChatMessage("Successfully changed color 1.");
                    return 1;
                })
        ));
        return command;
    }
}
