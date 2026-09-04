package com.prestige.client.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prestige.client.PrestigeClient;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages configuration loading and saving
 */
public class ConfigManager {
	private static final String CONFIG_DIR = "config/prestige-client";
	private static final String SETTINGS_FILE = "settings.json";
	private final Gson gson;
	private final Map<String, Object> config = new HashMap<>();

	public ConfigManager() {
		this.gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		createConfigDirectory();
	}

	/**
	 * Create config directory if it doesn't exist
	 */
	private void createConfigDirectory() {
		try {
			Files.createDirectories(Paths.get(CONFIG_DIR));
		} catch (IOException e) {
			PrestigeClient.LOGGER.error("Failed to create config directory", e);
		}
	}

	/**
	 * Load configuration from file
	 */
	public void loadConfig() {
		File settingsFile = new File(CONFIG_DIR, SETTINGS_FILE);
		if (settingsFile.exists()) {
			try (FileReader reader = new FileReader(settingsFile)) {
				Map<String, Object> loadedConfig = gson.fromJson(reader, Map.class);
				if (loadedConfig != null) {
					config.putAll(loadedConfig);
				}
				PrestigeClient.LOGGER.info("Loaded configuration from " + SETTINGS_FILE);
			} catch (IOException e) {
				PrestigeClient.LOGGER.error("Failed to load configuration", e);
			}
		} else {
			saveConfig();
		}
	}

	/**
	 * Save configuration to file
	 */
	public void saveConfig() {
		File settingsFile = new File(CONFIG_DIR, SETTINGS_FILE);
		try (FileWriter writer = new FileWriter(settingsFile)) {
			gson.toJson(config, writer);
			PrestigeClient.LOGGER.info("Saved configuration to " + SETTINGS_FILE);
		} catch (IOException e) {
			PrestigeClient.LOGGER.error("Failed to save configuration", e);
		}
	}

	/**
	 * Get a configuration value
	 */
	public <T> T get(String key, T defaultValue) {
		Object value = config.get(key);
		if (value == null) {
			return defaultValue;
		}
		try {
			return (T) value;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Set a configuration value
	 */
	public void set(String key, Object value) {
		config.put(key, value);
	}
}
