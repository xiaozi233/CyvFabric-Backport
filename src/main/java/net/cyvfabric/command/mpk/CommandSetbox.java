package net.cyvfabric.command.mpk;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.cyvfabric.util.parkour.LandingBlock;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Box;

public class CommandSetbox extends CyvCommand {
    public CommandSetbox() {
        super("setbox");
        this.helpString = "Creates landing zone with set dimensions.";
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register().then(ClientCommandManager.argument("x1", DoubleArgumentType.doubleArg())
                .then(ClientCommandManager.argument("y1", DoubleArgumentType.doubleArg())
                        .then(ClientCommandManager.argument("z1", DoubleArgumentType.doubleArg())
                                .then(ClientCommandManager.argument("x2", DoubleArgumentType.doubleArg())
                                        .then(ClientCommandManager.argument("y2", DoubleArgumentType.doubleArg())
                                                .then(ClientCommandManager.argument("z2", DoubleArgumentType.doubleArg())
                                                        .executes(commandContext -> {
                                                            Box player = MinecraftClient.getInstance().player.getBoundingBox();

                                                            double x1 = DoubleArgumentType.getDouble(commandContext, "x1") + player.getXLength()/2;
                                                            double x2 = DoubleArgumentType.getDouble(commandContext, "x2") - player.getXLength()/2;
                                                            double y1 = DoubleArgumentType.getDouble(commandContext, "y1");
                                                            double y2 = DoubleArgumentType.getDouble(commandContext, "y2");
                                                            double z1 = DoubleArgumentType.getDouble(commandContext, "z1") + player.getZLength()/2;
                                                            double z2 = DoubleArgumentType.getDouble(commandContext, "z2") - player.getZLength()/2;
                                                            Box box = new Box(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2),
                                                                    Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));

                                                            if (ParkourTickListener.landingBlock != null) {
                                                                ParkourTickListener.landingBlock.bb = new Box[]{box};
                                                                CyvFabric.sendChatMessage("Landing box changed.");

                                                            } else {
                                                                ParkourTickListener.landingBlock = new LandingBlock(box);
                                                                CyvFabric.sendChatMessage("Landing box set.");
                                                            }
                                                            return 1;
                                                        })
                                                )
                                        )
                                )
                        )
                )
        );
    }
}