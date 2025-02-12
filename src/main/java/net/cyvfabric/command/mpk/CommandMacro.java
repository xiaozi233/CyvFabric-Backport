package net.cyvfabric.command.mpk;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.MacroFileInit;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiMacro;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class CommandMacro extends CyvCommand {
    public CommandMacro() {
        super("macro");
        this.helpString = "Open the parkour macro GUI.";
    }

    public static int macroRunning = 0;
    public static ArrayList<ArrayList<String>> macro;
    //[w][a][s][d][space][sprint][sneak][yaw][pitch]

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register(){
        return super.register()
                .executes(commandContext -> {
                    GuiHandler.setScreen(new GuiMacro());
                    return 1;
                })
                .then(ClientCommandManager.literal("run")
                        .executes(commandContext -> {
                            runMacro();
                            return 1;
                        })
                ).then(ClientCommandManager.literal("stop")
                        .executes(commandContext -> macroRunning = 1)
                );
    }

    public static void runMacro() {
        MacroFileInit.swapFile(CyvClientConfig.getString("currentMacro", "macro"));
        if (!MinecraftClient.getInstance().isInSingleplayer()) {
            CyvFabric.sendChatMessage("No permission to run macro.");
            return;
        }

        try {
            if (macroRunning == 0) {
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new FileReader(MacroFileInit.macroFile));
                macro = gson.fromJson(reader, ArrayList.class);
                macroRunning = macro.size() + 1; //MovementListener starts macro

            }

        } catch (FileNotFoundException e) {
            CyvFabric.LOGGER.error(String.valueOf(e));
            CyvFabric.sendChatMessage("Macro file doesn't exist.");
        }
    }

    void addToArray(boolean w, boolean a, boolean s, boolean d, boolean space, boolean sprint, boolean sneak, float yaw, float pitch) {
        ArrayList<String> params = new ArrayList<>();

        params.add(w+"");
        params.add(a+"");
        params.add(s+"");
        params.add(d+"");
        params.add(space+"");
        params.add(sprint+"");
        params.add(sneak+"");
        params.add(yaw+"");
        params.add(pitch+"");


        macro.add(params);
    }

}
