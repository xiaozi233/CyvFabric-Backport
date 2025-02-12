package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.CommandWithoutArg;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.gui.GuiLb;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandMm extends CommandWithoutArg {
    public CommandMm() {
        super("mm");
        aliases.add("momentumblock");
        aliases.add("momentum");
        this.helpString = "Open GUi for modifying momentum block settings.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context) {
        if (ParkourTickListener.momentumBlock != null) GuiHandler.setScreen(new GuiLb(ParkourTickListener.momentumBlock));
        else {
            CyvFabric.sendChatMessage("You must set a momentum block to use this command.");
        }
    }
}