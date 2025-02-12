package net.cyvfabric.command.calculations;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.command.CommandWithoutArg;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiSimulate;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandSimulate extends CommandWithoutArg {
    public CommandSimulate() {
        super("simulate");
        this.usage = "none";
        this.helpString = "Simulates movement given a string of functions.";

        this.aliases.add("sim");
        this.aliases.add("s");
        this.aliases.add("%");

    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context) {
        GuiHandler.setScreen(new GuiSimulate());
    }
}
