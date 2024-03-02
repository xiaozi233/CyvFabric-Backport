package net.cyvfabric.command.calculations;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.GuiHandler;
import net.cyvfabric.gui.GuiSimulate;
import net.cyvfabric.util.CyvCommand;
import net.minecraft.server.command.ServerCommandSource;

public class CommandSimulate extends CyvCommand {
    public CommandSimulate() {
        super("simulate");
        this.usage = "none";
        this.helpString = "Simulates movement given a string of functions.";

        this.aliases.add("sim");
        this.aliases.add("s");
        this.aliases.add("%");

    }

    @Override
    public void run(CommandContext<ServerCommandSource> context, String[] args) {
        GuiHandler.setScreen(new GuiSimulate());
    }
}
