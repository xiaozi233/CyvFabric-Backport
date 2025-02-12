package net.cyvfabric.command.calculations;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

public class CommandSetSensitivity extends CyvCommand {
    public CommandSetSensitivity() {
        super("setsensitivity");
        this.hasArgs = true;
        this.usage = "<value from 0.0 to 1.0>";
        this.helpString = "Sets your current sensitivity. (100% = 0.5, ranges from 0.0 to 1.0)";

        this.aliases.add("ss");
        this.aliases.add("setsens");

    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .executes(commandContext -> {
                    CyvFabric.sendChatMessage("Sensitivity: " +  CyvFabric.df.format(MinecraftClient.getInstance().options.mouseSensitivity * 200) + "%");
                    return 1;
                })
                .then(ClientCommandManager.argument("sensitivity", DoubleArgumentType.doubleArg(0, 1))
                        .executes(commandContext -> {
                            double sensitivity = DoubleArgumentType.getDouble(commandContext, "sensitivity");

                            MinecraftClient.getInstance().options.mouseSensitivity = sensitivity;
                            MinecraftClient.getInstance().options.write();

                            double percentage = 200 * sensitivity;
                            CyvFabric.sendChatMessage("Sensitivity set to " + CyvFabric.df.format(percentage) + "%");
                            return 1;
                        })
                );
    }
}
