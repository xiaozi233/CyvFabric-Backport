package net.cyvfabric.command.config;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandColor2 extends CyvCommand {
    public CommandColor2() {
        super("color2");
        this.hasArgs = true;
        this.usage = "[color]";
        this.helpString = "Sets color2 for display and chat.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        if (args.length < 1) {
            CyvFabric.sendChatMessage("Invalid color. For a list of colors use /cyv colors");
            return;
        }

        if (CyvClientColorHelper.setColor2(args[0].toLowerCase())) {
            CyvFabric.sendChatMessage("Successfully changed color 2.");
        } else {
            CyvFabric.sendChatMessage("Invalid color. For a list of colors use /cyv colors");
        }
    }
}
