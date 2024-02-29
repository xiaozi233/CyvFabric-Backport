package net.cyvfabric;

import net.cyvfabric.util.ChatFormattingString;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

//Main class of the mod
public class CyvFabric implements ModInitializer {
	public static final String MOD_ID = "cyvfabric";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static DecimalFormat df = new DecimalFormat("#");

	@Override
	public void onInitialize() {
		LOGGER.info("CyvFabric initializing...");
	}

	/**Send a client-sided message to the player*/
	public static void sendMessage(Object text) {
		try {
			MinecraftClient.getInstance().player.sendMessage(Text.of(
				ChatFormattingString.AQUA + "<Cyv> " + ChatFormattingString.WHITE + text.toString()));
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}

}