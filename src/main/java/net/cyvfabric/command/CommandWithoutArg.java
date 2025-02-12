package net.cyvfabric.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public abstract class CommandWithoutArg extends CyvCommand {
    public CommandWithoutArg(String name) {
        super(name);
    }
    public abstract void run(CommandContext<FabricClientCommandSource> context);

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register().executes(commandContext -> {
            this.run(commandContext);
            return 1;
        });
    }
}
