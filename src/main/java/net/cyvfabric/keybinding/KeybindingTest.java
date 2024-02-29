package net.cyvfabric.keybinding;

import net.cyvfabric.util.CyvKeybinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeybindingTest extends CyvKeybinding {
    public KeybindingTest() {
        super("key.cyvfabric.test", GLFW.GLFW_KEY_J);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            client.player.sendMessage(Text.of("Ban Librations"));
        }
    }
}
