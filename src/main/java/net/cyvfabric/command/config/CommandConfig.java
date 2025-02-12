package net.cyvfabric.command.config;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.command.CommandWithoutArg;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiModConfig;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandConfig extends CommandWithoutArg {
    public CommandConfig() {
        super("config");
        this.helpString = "Opens the mod config GUI.";

        aliases.add("configuration");
        aliases.add("settings");
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context) {
        GuiHandler.setScreen(new GuiModConfig());
    }
}
