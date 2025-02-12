package net.cyvfabric.util;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.event.CommandInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.util.ArrayList;

/**Base subcommand of /cyv.*/
public abstract class CyvCommand {
    public final String name;
    public String usage = "none";
    public String helpString = "WIP";

    public ArrayList<CyvCommand> subCommands = new ArrayList<>();
    public CyvCommand parent; //will be null if this is not a subcommand
    public ArrayList<String> aliases = new ArrayList<>();
    public boolean hasArgs = false;

    public CyvCommand(String name) {
        this.name = name;
        CommandInitializer.TAGS.add(name);
    }

    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return ClientCommandManager.literal(this.name);
    }

    public String getDetailedHelp() {
        return helpString;
    }
}
