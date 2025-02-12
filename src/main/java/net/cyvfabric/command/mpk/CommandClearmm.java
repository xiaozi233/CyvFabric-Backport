package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.CommandWithoutArg;
import net.cyvfabric.event.events.ParkourTickListener;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandClearmm extends CommandWithoutArg {
    public CommandClearmm() {
        super("clearmm");
        this.helpString = "Clear the momentum block.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context) {
        if (ParkourTickListener.momentumBlock != null) ParkourTickListener.momentumBlock = null;
        CyvFabric.sendChatMessage("Successfully cleared momentum block.");
    }
}