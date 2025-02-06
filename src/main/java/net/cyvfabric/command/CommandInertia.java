package net.cyvfabric.command;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandInertia extends CyvCommand {
    public CommandInertia() {
        super("inertia");
        this.hasArgs = true;
        this.usage = "[subcategory]";
        this.helpString = "Configure inertia listener. Use /cyv help inertia for more info.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        boolean enabled = CyvClientConfig.getBoolean("inertiaEnabled", false);
        String subcategory = "toggle";

        //add a checker for no args to not toggle on/off inertia

        if (args.length != 0) {
            subcategory = args[0].toLowerCase();
        }

        switch (subcategory) {
            case "toggle" -> {
                if (enabled) {
                    CyvClientConfig.set("inertiaEnabled", false);
                    CyvFabric.sendChatMessage("Inertia listener toggled off");
                } else {
                    CyvClientConfig.set("inertiaEnabled", true);
                    CyvFabric.sendChatMessage("Inertia listener toggled on");
                }
            }
            case "min", "minimum" -> {
                try {
                    CyvClientConfig.set("inertiaMin", Double.parseDouble(args[1]));
                    CyvFabric.sendChatMessage("Inertia minimum speed threshold set to " + args[1] + ".");
                } catch (Exception e) {
                    CyvFabric.sendChatMessage("Invalid minimum speed.");
                }
            }
            case "max", "maximum" -> {
                try {
                    CyvClientConfig.set("inertiaMax", Double.parseDouble(args[1]));
                    CyvFabric.sendChatMessage("Inertia maximum speed threshold set to " + args[1] + ".");
                } catch (Exception e) {
                    CyvFabric.sendChatMessage("Invalid maximum speed.");
                }
            }
            case "mode", "axis" -> {
                if (args[1].equalsIgnoreCase("x")) {
                    CyvClientConfig.set("inertiaAxis", 'x');
                    CyvFabric.sendChatMessage("Inertia axis set to x.");
                } else if (args[1].equalsIgnoreCase("z")) {
                    CyvClientConfig.set("inertiaAxis", 'z');
                    CyvFabric.sendChatMessage("Inertia axis set to z.");
                } else {
                    CyvFabric.sendChatMessage("Invalid axis. Only x and z are allowed.");
                }
            }
            case "tick", "t" -> {
                try {
                    if (Integer.parseInt(args[1]) > 12 || Integer.parseInt(args[1]) < 1) {
                        CyvFabric.sendChatMessage("Inertia tick must be from 1 to 12.");
                    } else {
                        CyvClientConfig.set("inertiaTick", Integer.parseInt(args[1]));
                        CyvFabric.sendChatMessage("Inertia tick set to " + args[1] + ".");
                    }
                } catch (Exception e) {
                    CyvFabric.sendChatMessage("Invalid tick.");
                }
            }
            case "ground", "groundmode", "groundtype" -> {
                if (args[1].equalsIgnoreCase("normal")) {
                    CyvClientConfig.set("inertiaGroundType", "normal");
                    CyvFabric.sendChatMessage("Ground type set to normal.");
                } else if (args[1].equalsIgnoreCase("ice")) {
                    CyvClientConfig.set("inertiaGroundType", "ice");
                    CyvFabric.sendChatMessage("Ground type set to ice.");
                } else if (args[1].equalsIgnoreCase("slime")) {
                    CyvClientConfig.set("inertiaGroundType", "slime");
                    CyvFabric.sendChatMessage("Ground type set to slime.");
                } else {
                    CyvFabric.sendChatMessage("Invalid ground type. Only normal, ice, and slime are allowed.");
                }
            }
            default -> CyvFabric.sendChatMessage("Unrecognized argument.");
        }
    }

    @Override
    public String getDetailedHelp() {
        return """
                Toggle on/off or modify inertia listener settings. Subcategories:\
                
                [toggle]: Toggle listener on or off.\
                
                [min/max]: Set minimum and maximum speed bounds for a chat message.\
                
                [mode]: Set listener to x or z axis.\
                
                [tick]: Set airtick to check for inertia.\
                
                [ground]: Change ground type between ice, slime, or normal.""";
    }

}
