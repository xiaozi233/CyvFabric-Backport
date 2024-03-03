package net.cyvfabric.command;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.Arrays;

public class CommandTest extends CyvCommand {
    public CommandTest() {
        super("test");
        this.hasArgs = true;
        this.usage = "[arguments]";
        this.helpString = "This is simply a test command.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        CyvFabric.sendChatMessage("You said: " + Arrays.toString(args));
    }
}