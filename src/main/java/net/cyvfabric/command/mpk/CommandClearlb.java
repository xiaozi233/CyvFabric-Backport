package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.CommandWithoutArg;
import net.cyvfabric.event.events.ParkourTickListener;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandClearlb extends CommandWithoutArg {
    public CommandClearlb() {
        super("clearlb");
        this.helpString = "Clear the landing block.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context) {
        if (ParkourTickListener.landingBlock != null) ParkourTickListener.landingBlock = null;
        CyvFabric.sendChatMessage("Successfully cleared landing block.");
    }
}