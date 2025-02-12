package net.cyvfabric.command.config;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.text.DecimalFormatSymbols;

public class CommandDf extends CyvCommand {
    public CommandDf() {
        super("df");
        this.hasArgs = true;
        this.usage = "[precision]";
        this.helpString = "Set the decimal precision of the mod from 0-16.";

        aliases.add("decimalprecision");
        aliases.add("precision");
        aliases.add("decimals");
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .then(ClientCommandManager.argument("df", IntegerArgumentType.integer(0, 16))
                        .executes(commandContext -> {
                            int df = IntegerArgumentType.getInteger(commandContext, "df");
                            CyvClientConfig.set("df", df);
                            CyvFabric.df.setMaximumFractionDigits(CyvClientConfig.getInt("df",df));

                            if (CyvClientConfig.getBoolean("trimZeroes", true)) CyvFabric.df.setMinimumFractionDigits(0);
                            else CyvFabric.df.setMinimumFractionDigits(CyvClientConfig.getInt("df",5));

                            DecimalFormatSymbols s = new DecimalFormatSymbols();
                            s.setDecimalSeparator('.');
                            CyvFabric.df.setDecimalFormatSymbols(s);
                            CyvFabric.sendChatMessage("Decimal precision set to " + df + ".");
                            return 1;
                        })
                );
    }
}
