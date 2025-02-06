package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.cyvfabric.util.parkour.LandingAxis;
import net.cyvfabric.util.parkour.LandingBlock;
import net.cyvfabric.util.parkour.LandingMode;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class CommandSetlb extends CyvCommand {
    public CommandSetlb() {
        super("setlb");
        hasArgs = true;
        usage = "[arguments]";
        this.helpString = "Set landing block";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        run(args);
    }

    public static void run(String[] args) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;

        new Thread(() -> {
            LandingMode mode = LandingMode.landing;
            LandingAxis axis = LandingAxis.both;
            boolean box = false;
            boolean target = false;
            for (String s : args) {
                s = s.toLowerCase();
                switch (s) {
                    case "x" -> axis = LandingAxis.x;
                    case "z" -> axis = LandingAxis.z;
                    case "land", "landing" -> mode = LandingMode.landing;
                    case "hit" -> mode = LandingMode.hit;
                    case "zneo", "z-neo", "neo", "z_neo" -> mode = LandingMode.z_neo;
                    case "enter" -> mode = LandingMode.enter;
                    case "box" -> box = true;
                    case "target" -> target = true;
                }
            }

            if (target) {
                HitResult hit = player.raycast(100, 0, false);
                if (hit.getType().equals(HitResult.Type.BLOCK)) {
                    try {
                        BlockPos pos = ((BlockHitResult) hit).getBlockPos();

                        if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) {
                            CyvFabric.sendChatMessage("Please look at a valid block.");
                        } else {
                            ParkourTickListener.landingBlock = new LandingBlock(pos, mode, axis, box);
                            CyvFabric.sendChatMessage("Successfully set landing block.");
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

                    BlockPos pos = new BlockPos(player.getBlockX(),
                            player.getBlockY(), player.getBlockZ());

                    if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) pos = pos.down();

                    if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) {
                        CyvFabric.sendChatMessage("Please stand on a valid block.");
                    } else {
                        ParkourTickListener.landingBlock = new LandingBlock(pos, mode, axis, box);
                        CyvFabric.sendChatMessage("Successfully set landing block.");
                    }

                } else {
                    CyvFabric.sendChatMessage("Please stand on a valid block.");
                }
            }
        }, "Set landing block").start();
    }
}