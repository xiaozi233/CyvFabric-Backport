package net.cyvfabric.command.config;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandColor2 extends CyvCommand {
    public CommandColor2() {
        super("color2");
        this.hasArgs = true;
        this.usage = "[color]";
        this.helpString = "Sets color2 for display and chat.";
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        LiteralArgumentBuilder<FabricClientCommandSource> command = super.register();
        CyvClientColorHelper.COLORS.forEach(color -> command.then(ClientCommandManager.literal(color)
                .executes(commandContext -> {
                    CyvClientColorHelper.setColor2(color);
                    CyvFabric.sendChatMessage("Successfully changed color 2.");
                    return 1;
                })
        ));
        return command;
    }
}
