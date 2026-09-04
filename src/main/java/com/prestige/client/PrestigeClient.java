package com.prestige.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.prestige.client.manager.ModuleManager;
import com.prestige.client.manager.ConfigManager;
import com.prestige.client.manager.EventManager;
import com.prestige.client.gui.ClickGUI;

@Environment(EnvType.CLIENT)
public class PrestigeClient implements ClientModInitializer {
	public static final String MOD_ID = "prestige-client";
	public static final String MOD_NAME = "Prestige Client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static final ModuleManager MODULE_MANAGER = new ModuleManager();
	public static final ConfigManager CONFIG_MANAGER = new ConfigManager();
	public static final EventManager EVENT_MANAGER = new EventManager();
	public static ClickGUI GUI;

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing Prestige Client for Crystal PvP");

		// Initialize configuration system
		CONFIG_MANAGER.loadConfig();

		// Register all modules
		MODULE_MANAGER.registerModules();

		// Initialize GUI
		GUI = new ClickGUI();

		LOGGER.info("Prestige Client initialized successfully!");
	}

	public static ModuleManager getModuleManager() {
		return MODULE_MANAGER;
	}

	public static ConfigManager getConfigManager() {
		return CONFIG_MANAGER;
	}

	public static EventManager getEventManager() {
		return EVENT_MANAGER;
	}
}
