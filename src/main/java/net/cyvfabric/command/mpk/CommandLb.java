package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.CommandWithoutArg;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.gui.GuiLb;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandLb extends CommandWithoutArg {
    public CommandLb() {
        super("lb");
        aliases.add("landingblock");
        aliases.add("landing");
        this.helpString = "Open GUi for modifying landing block settings.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context) {
        if (ParkourTickListener.landingBlock != null) GuiHandler.setScreen(new GuiLb(ParkourTickListener.landingBlock));
        else {
            CyvFabric.sendChatMessage("You must set a landing block to use this command.");
        }
    }
}