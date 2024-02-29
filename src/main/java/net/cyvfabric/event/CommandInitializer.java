package net.cyvfabric.event;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.CommandHelp;
import net.cyvfabric.command.CommandTest;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandInitializer  {
    public static ArrayList<CyvCommand> cyvCommands = new ArrayList<CyvCommand>(); //all commands

    //add all commands in
    public static void addCommands() {
        cyvCommands.add(new CommandHelp());
        cyvCommands.add(new CommandTest());
    }

    @SuppressWarnings({"unchecked"})
    public static void initializeCommands() {
        addCommands();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            //register the base command
            dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("cyv")
                    .requires(source -> source.hasPermissionLevel(2)))
                    .executes(context -> {
                        CyvFabric.sendMessage("For more info use /mpk help"); //no args
                        return 1;
                     })
                    .then(CommandManager.argument("message", MessageArgumentType.message()).executes(context -> {
                MessageArgumentType.getSignedMessage(context, "message", message -> {
                    String[] args = message.getSignedContent().split(" "); //all arguments following /mpk
                    CyvCommand targetCommand = null;

                    Outer: for (CyvCommand cmd : cyvCommands) { //loop through subcommands
                        if (!cmd.name.toLowerCase().equals(args[0])) {
                            for (String s : cmd.aliases) {
                                if (s.toLowerCase().equals(args[0])) {
                                    targetCommand = cmd;
                                    break Outer;
                                }
                            }
                        } else {
                            targetCommand = cmd;
                            break Outer;
                        }
                    }

                    if (targetCommand != null) {
                        String[] newArgs = new String[]{};
                        if (args.length > 1) newArgs = Arrays.copyOfRange(args, 1, args.length);
                        targetCommand.run(context, newArgs);
                        return;
                    }

                    //finished looping through with no matches?
                    MinecraftClient.getInstance().player.sendMessage(Text.of("Unknown command. For more info use /mpk help"));

                });
                return 1;
            })));

        });

            /*
            LiteralArgumentBuilder<FabricClientCommandSource> cmd = ClientCommandManager.literal("mpk")
                    .requires(source -> source.hasPermissionLevel(0))
                    .then(ClientCommandManager.argument("args", MessageArgumentType.message()))
                    .executes(context -> { //hints at a help menu if no args are passed
                        MinecraftClient.getInstance().player.sendMessage(Text.of("For more info use /mpk help"));
                        String[] args = context.getArgument("args", MessageArgumentType.MessageFormat.class).getContents().split(" ");
                        MinecraftClient.getInstance().player.sendMessage(Text.of(Arrays.toString(args)));
                        return 1;
                    });


            for (CyvCommand cyvCmd : cyvCommands) { //create a subcommand for each cyv command
                //Note: this method is located inside the CyvCommand class for recursion
                LiteralArgumentBuilder<FabricClientCommandSource> subCmd = cyvCmd.build();
                cmd.then(subCmd);

                for (String alias : cyvCmd.aliases) { //add all command aliases as redirects
                    cmd.then(ClientCommandManager.literal(alias).redirect(subCmd.getRedirect())); //redirect to the subCmd
                }

            }

            dispatcher.register(cmd); //register the full command after everything's done


             */

    }

}
