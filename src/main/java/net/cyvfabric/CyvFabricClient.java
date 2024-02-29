package net.cyvfabric;

import net.cyvfabric.event.CommandInitializer;
import net.cyvfabric.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

//Client-sided portion of the mod
public class CyvFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() { //called when the mod is initialized on the client-side
        KeyInputHandler.register(); //register mod keybinds
        CommandInitializer.initializeCommands(); //register mod commands

        CyvFabric.LOGGER.info("CyvFabric client initialized!");
    }
}
