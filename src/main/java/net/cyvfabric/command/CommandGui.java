package net.cyvfabric.command;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiMPK;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandGui extends CommandWithoutArg {
    public CommandGui() {
        super("gui");
        this.helpString = "Open the MPK gui.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context) {
        GuiHandler.setScreen(new GuiMPK());
    }
}