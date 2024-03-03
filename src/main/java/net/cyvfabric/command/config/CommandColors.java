package net.cyvfabric.command.config;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandColors extends CyvCommand {
    public CommandColors() {
        super("colors");
        this.helpString = "Get the list of colors usable for display and chat.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        String str = "List of colors usable:";
        for (CyvClientColorHelper.CyvClientColor c : CyvClientColorHelper.colors) {
            str += "\n" + c.chatColor + c.name;
        }

        CyvFabric.sendChatMessage(str);
    }
}
