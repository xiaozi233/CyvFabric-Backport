package net.cyvfabric.command.mpk;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.cyvfabric.util.parkour.LandingAxis;
import net.cyvfabric.util.parkour.LandingBlock;
import net.cyvfabric.util.parkour.LandingMode;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public class CommandSetmm extends CyvCommand {private static final SuggestionProvider<FabricClientCommandSource> AXIS_SUGGESTIONS = (context, builder) -> {
    for (String tag : Arrays.asList("x", "z", "both")) {
        builder.suggest(tag);
    }
    return builder.buildFuture();
};
    private static final SuggestionProvider<FabricClientCommandSource> MODE_SUGGESTIONS = (context, builder) -> {
        for (String tag : Arrays.asList("landing", "hit", "zneo", "box")) {
            builder.suggest(tag);
        }
        return builder.buildFuture();
    };
    private static final SuggestionProvider<FabricClientCommandSource> TARGET_SUGGESTIONS = (context, builder) -> builder.suggest("target").buildFuture();

    public CommandSetmm() {
        super("setmm");
        hasArgs = true;
        usage = "[arguments]";
        this.helpString = "Set momentum block";
    }

    public int run(CommandContext<FabricClientCommandSource> context) {
        return this.run(context, false, false, false);
    }

    public int run(CommandContext<FabricClientCommandSource> context, boolean axis, boolean mode, boolean target) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;

        new Thread(() -> {
            LandingAxis landingAxis = LandingAxis.both;
            LandingMode landingMode = LandingMode.landing;
            boolean box = false;
            boolean isTarget = false;
            if (axis){
                switch (StringArgumentType.getString(context, "axis")){
                    case "x" -> landingAxis = LandingAxis.x;
                    case "z" -> landingAxis = LandingAxis.z;
                    case "both" -> landingAxis = LandingAxis.both;
                    case "land", "landing" -> landingMode = LandingMode.landing;
                    case "hit" -> landingMode = LandingMode.hit;
                    case "zneo", "z-neo", "neo", "z_neo" -> landingMode = LandingMode.z_neo;
                    case "enter" -> landingMode = LandingMode.enter;
                    case "box" -> box = true;
                    case "target" -> isTarget = true;
                }
            }
            if (mode){
                switch (StringArgumentType.getString(context, "mode")){
                    case "x" -> landingAxis = LandingAxis.x;
                    case "z" -> landingAxis = LandingAxis.z;
                    case "both" -> landingAxis = LandingAxis.both;
                    case "land", "landing" -> landingMode = LandingMode.landing;
                    case "hit" -> landingMode = LandingMode.hit;
                    case "zneo", "z-neo", "neo", "z_neo" -> landingMode = LandingMode.z_neo;
                    case "enter" -> landingMode = LandingMode.enter;
                    case "box" -> box = true;
                    case "target" -> isTarget = true;
                }
            }
            if (target || isTarget) {
                HitResult hit = player.raycast(100, 0, false);
                if (hit.getType().equals(HitResult.Type.BLOCK)) {
                    try {
                        BlockPos pos = ((BlockHitResult) hit).getBlockPos();

                        if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) {
                            CyvFabric.sendChatMessage("Please look at a valid block.");
                        } else {
                            ParkourTickListener.momentumBlock = new LandingBlock(pos, landingMode, landingAxis, box);
                            CyvFabric.sendChatMessage("Successfully set momentum block.");
                        }
                    } catch (Exception e) {
                        CyvFabric.sendChatMessage("Please look at a valid block.");
                    }
                } else {
                    CyvFabric.sendChatMessage("Please look at a valid block.");
                }
            }
            else {
                if (player.isOnGround()) {
                    int yLevel = player.getBlockY();

                    BlockPos pos = new BlockPos(player.getBlockX(),
                            (int) (Math.floor(yLevel - 0.001)), player.getBlockZ());

                    if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) pos = pos.down();

                    if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) {
                        CyvFabric.sendChatMessage("Please stand on a valid block.");
                    } else {
                        ParkourTickListener.momentumBlock = new LandingBlock(pos, landingMode, landingAxis, box);
                        CyvFabric.sendChatMessage("Successfully set momentum block.");
                    }

                } else {
                    CyvFabric.sendChatMessage("Please stand on a valid block.");
                }
            }
        }, "Set landing block").start();
        return 1;
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .executes(this::run)
                .then(ClientCommandManager.argument("axis", StringArgumentType.string())
                .suggests(AXIS_SUGGESTIONS).suggests(MODE_SUGGESTIONS).suggests(TARGET_SUGGESTIONS)
                .executes(commandContext -> this.run(commandContext, true, false,false))
                .then(ClientCommandManager.argument("mode", StringArgumentType.string())
                        .suggests(MODE_SUGGESTIONS)
                        .executes(commandContext -> this.run(commandContext, true, true,false))
                        .then(ClientCommandManager.argument("target", StringArgumentType.string())
                                .suggests(TARGET_SUGGESTIONS)
                                .executes(commandContext -> this.run(commandContext, true, true,true))
                        )
                )
        );
    }
}