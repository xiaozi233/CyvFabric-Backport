package net.cyvfabric.event;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.CommandGui;
import net.cyvfabric.command.CommandHelp;
import net.cyvfabric.command.CommandInertia;
import net.cyvfabric.command.calculations.*;
import net.cyvfabric.command.config.*;
import net.cyvfabric.command.mpk.*;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CommandInitializer {
    public static final ArrayList<CyvCommand> cyvCommands = new ArrayList<>(); //all commands
    public static final Collection<String> TAGS = new ArrayList<>();
    public static final SuggestionProvider<FabricClientCommandSource> SUGGEST_TAGS = (context, builder) -> {
        for (String tag : TAGS) {
            builder.suggest(tag);
        }
        return builder.buildFuture();
    };

    //add all commands in
    public static void addCommands() {
        cyvCommands.addAll(Arrays.asList(//config commands
                new CommandHelp(), new CommandColor1(), new CommandColor2(), new CommandDf(),
                new CommandConfig(), new CommandGui(), new CommandInertia()));

        cyvCommands.addAll(Arrays.asList(//config commands
                new CommandSetlb(), new CommandClearlb(), new CommandClearpb(), new CommandSetmm(), new CommandClearmm(),
                new CommandSetbox(), new CommandSetcond(), new CommandLb(), new CommandMm()));

        cyvCommands.addAll(Arrays.asList(//mm commands
                new CommandAirtime(), new CommandCalculate(), new CommandDistance(), new CommandHeight(),
                new CommandSimulate(), new CommandSetSensitivity(), new CommandOptimizeSensitivity(), new CommandSimulate()));

        cyvCommands.add(new CommandMacro());
    }

    public static void register() {
        addCommands();
        LiteralArgumentBuilder<FabricClientCommandSource> baseCommandBuilder = ClientCommandManager.literal("cyv")
                .requires(source -> source.hasPermissionLevel(0))
                .executes(context -> {
                    CyvFabric.sendChatMessage("For more info use /cyv help"); //no args
                    return 1;
                });
        cyvCommands.forEach(cyvCommand -> baseCommandBuilder.then(cyvCommand.register()));


        ClientCommandManager.DISPATCHER.register(baseCommandBuilder);

        LiteralCommandNode<FabricClientCommandSource> baseCommand = baseCommandBuilder.build();

        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("mpk").redirect(baseCommand).executes(context -> {
                    CyvFabric.sendChatMessage("For more info use /cyv help"); //no args
                    return 1;
                })
        ); //alias for /cyv)

        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("mm").redirect(baseCommand).executes(context -> {
                    CyvFabric.sendChatMessage("For more info use /cyv help"); //no args
                    return 1;
                })
        );
    }
}
