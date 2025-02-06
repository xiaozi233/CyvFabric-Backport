package net.cyvfabric.command.config;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.util.CyvCommand;
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
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        try {
            int df = Integer.parseInt(args[0]);

            if (df >= 0 && df <= 16) {
                CyvClientConfig.set("df",df);
                CyvFabric.df.setMaximumFractionDigits(CyvClientConfig.getInt("df",df));

                if (CyvClientConfig.getBoolean("trimZeroes", true)) CyvFabric.df.setMinimumFractionDigits(0);
                else CyvFabric.df.setMinimumFractionDigits(CyvClientConfig.getInt("df",5));

                DecimalFormatSymbols s = new DecimalFormatSymbols();
                s.setDecimalSeparator('.');
                CyvFabric.df.setDecimalFormatSymbols(s);
                CyvFabric.sendChatMessage("Decimal precision set to " + df + ".");
            } else {
                CyvFabric.sendChatMessage("Please enter a valid number from 0-16.");
            }

        } catch (Exception e) {
            CyvFabric.sendChatMessage("Please enter a valid number from 0-16.");
        }
    }
}
