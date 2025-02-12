package net.cyvfabric.keybinding;

import net.cyvfabric.command.mpk.CommandSetlb;
import net.cyvfabric.util.CyvKeybinding;
import org.lwjgl.glfw.GLFW;

public class KeybindingSetlb extends CyvKeybinding {
    public KeybindingSetlb() {
        super("key.cyvfabric.setlb", GLFW.GLFW_KEY_UNKNOWN);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandSetlb.setlbTarget();
        }
    }
}
