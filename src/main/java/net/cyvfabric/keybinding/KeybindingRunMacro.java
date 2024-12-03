package net.cyvfabric.keybinding;

import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiMPK;
import net.cyvfabric.util.CyvKeybinding;
import org.lwjgl.glfw.GLFW;

public class KeybindingRunMacro extends CyvKeybinding {
    public KeybindingRunMacro() {
        super("key.cyvfabric.runmacro", GLFW.GLFW_KEY_V);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandMacro.runMacro(null);
        }
    }
}
