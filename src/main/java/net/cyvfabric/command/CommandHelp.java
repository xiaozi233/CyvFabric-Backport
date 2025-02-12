package net.cyvfabric.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.CommandInitializer;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**Custom client-sided commands*/
public class CommandHelp extends CyvCommand {
     public CommandHelp() {
         super("help");
         this.usage = "[subcommand]";
         this.helpString = "Get the subcommand help menu for the " + this.parent + " command.";
     }

     public int run(CommandContext<FabricClientCommandSource> context, boolean hasArgs){
         String commandPath;
         String commandName;
         ArrayList<CyvCommand> subCommands;

         if (this.parent == null) { //executed from the very base command
             commandPath = "/cyv help";
             commandName = "cyv";
             subCommands = CommandInitializer.cyvCommands;
         } else { //executed from within a cyvclient subcommand
             commandPath = "/cyv " + this.parent.name + " help";
             commandName = this.parent.name;
             subCommands = this.parent.subCommands;
         }

         if (!hasArgs && !subCommands.isEmpty()) { //no arguments and haven't reached top-level command
             List<String> helpText = new ArrayList<>();
             helpText.add(CyvClientColorHelper.color1.chatColor + commandName +  " help menu:\247r");

             String chatColor2 = CyvClientConfig.getBoolean("whiteChat", false) ? CyvClientColorHelper.colors.get(12).chatColor
                     : CyvClientColorHelper.color2.chatColor;

             for (CyvCommand c : subCommands) {
                 helpText.add(CyvClientColorHelper.color1.chatColor + c.name + ": "
                         + chatColor2 + c.helpString);
             }

             helpText.add(CyvClientColorHelper.color1.chatColor + "\247oNote: Use " + commandPath + " [command] for details");

             CyvFabric.sendChatMessage(String.join("\n", helpText));

             return 1;
         }
         //arguments, or reached top-level command in command tree
         CyvCommand targetCommand = null; //target command

         List<String> argsList = new ArrayList<>(Arrays.stream(context.getInput().split(" ")).toList());
         argsList.remove(0);
         argsList.remove(0);
         String[] args = argsList.toArray(String[]::new);
         if (hasArgs) { //details for a specific command
             Outer:
             for (CyvCommand cmd : subCommands) { //loop through subcommands
                 if (!cmd.name.toLowerCase().equals(args[0])) {
                     for (String s : cmd.aliases) {
                         if (s.toLowerCase().equals(args[0])) {
                             targetCommand = cmd;
                             break Outer;
                         }
                     }
                 } else {
                     targetCommand = cmd;
                     break;
                 }
             }

         } else { //show info about self
             targetCommand = parent;
         }

         if (targetCommand == null) {
             CyvFabric.sendChatMessage("Command not found. Use " + commandPath + " for a list of commands.");

         } else {
             List<String> commandNames = new ArrayList<>();
             commandNames.add(targetCommand.name);
             if (targetCommand.aliases != null) commandNames.addAll(targetCommand.aliases);

             CyvFabric.sendChatMessage("Command: /" + args[0] + "\n"
                     + "Aliases: " + String.join(", ", commandNames) + "\n"
                     + "Usage: " + targetCommand.usage + "\n"
                     + targetCommand.getDetailedHelp()
                     + "\n" + CyvClientColorHelper.color1.chatColor +
                     "\247oNote: Use " + commandPath + " to list subcommands."
             );

         }

         return 1;
     }


    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .executes(commandContext -> this.run(commandContext, false))
                .then(ClientCommandManager.argument("command", StringArgumentType.string())
                        .suggests(CommandInitializer.SUGGEST_TAGS)
                        .executes(commandContext -> this.run(commandContext, true))
                );
    }


}
