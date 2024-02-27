package net.cyvfabric;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CyvFabric implements ModInitializer {
	public static final String MOD_ID = "cyvfabric";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("CyvFabric initializing...");
	}
}