package net.cyvfabric;

import net.cyvfabric.event.ConfigLoader;
import net.cyvfabric.event.CommandInitializer;
import net.cyvfabric.event.GuiHandler;
import net.cyvfabric.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

//Client-sided portion of the mod
public class CyvFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() { //called when the mod is initialized on the client-side
        ConfigLoader.init(CyvFabric.config);

        KeyInputHandler.register(); //register mod keybinds
        CommandInitializer.register(); //register mod commands
        GuiHandler.register(); //register gui listener

        CyvFabric.LOGGER.info("CyvFabric client initialized!");
    }
}
