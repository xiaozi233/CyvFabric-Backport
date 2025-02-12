package net.cyvfabric.command.config;

import com.mojang.brigadier.arguments.StringArgumentType;
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
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .then(ClientCommandManager.argument("color", StringArgumentType.string())
                        .suggests(CyvClientColorHelper.COLORS_SUGGESTIONS)
                        .executes(commandContext -> {
                            if (CyvClientColorHelper.setColor1(StringArgumentType.getString(commandContext, "color"))) {
                                CyvFabric.sendChatMessage("Successfully changed color 1.");
                            } else {
                                CyvFabric.sendChatMessage("Invalid color");
                            }
                            return 1;
                        })
                );
        }
}
