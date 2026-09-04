package com.prestige.client.module;

import com.prestige.client.PrestigeClient;
import com.prestige.client.event.EventBus;
import com.prestige.client.setting.Setting;
import java.util.*;

/**
 * Base class for all modules in Prestige Client
 */
public abstract class Module {
	private final String name;
	private final String description;
	private final ModuleCategory category;
	private final List<Setting<?>> settings;
	private boolean enabled = false;
	private int keybind = -1;

	public Module(String name, String description, ModuleCategory category) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.settings = new ArrayList<>();
	}

	/**
	 * Called when the module is enabled
	 */
	public void onEnable() {
		PrestigeClient.LOGGER.info("Enabling module: " + name);
	}

	/**
	 * Called when the module is disabled
	 */
	public void onDisable() {
		PrestigeClient.LOGGER.info("Disabling module: " + name);
	}

	/**
	 * Toggle the module on/off
	 */
	public void toggle() {
		if (enabled) {
			disable();
		} else {
			enable();
		}
	}

	/**
	 * Enable the module
	 */
	public void enable() {
		if (!enabled) {
			enabled = true;
			onEnable();
		}
	}

	/**
	 * Disable the module
	 */
	public void disable() {
		if (enabled) {
			enabled = false;
			onDisable();
		}
	}

	/**
	 * Register a setting for this module
	 */
	protected <T> Setting<T> registerSetting(Setting<T> setting) {
		settings.add(setting);
		return setting;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ModuleCategory getCategory() {
		return category;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getKeybind() {
		return keybind;
	}

	public void setKeybind(int keybind) {
		this.keybind = keybind;
	}

	public List<Setting<?>> getSettings() {
		return settings;
	}
}
