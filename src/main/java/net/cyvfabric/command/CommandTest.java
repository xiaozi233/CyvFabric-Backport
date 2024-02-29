package net.cyvfabric.command;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.GuiHandler;
import net.cyvfabric.gui.CyvGui;
import net.cyvfabric.gui.GuiSimulate;
import net.cyvfabric.util.CyvCommand;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Arrays;

public class CommandTest extends CyvCommand {
    public CommandTest() {
        super("test");
        this.hasArgs = true;
        this.usage = "[arguments]";
        this.helpString = "This is simply a test command.";
    }

    @Override
    public void run(CommandContext<ServerCommandSource> context, String[] args) {
        CyvFabric.sendMessage("Opening Gui");
        GuiHandler.setScreen(new GuiSimulate());
    }
}