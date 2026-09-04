package com.prestige.client.manager;

import com.prestige.client.event.EventBus;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import com.prestige.client.event.events.UpdateEvent;
import org.lwjgl.glfw.GLFW;

/**
 * Manages all event listeners and integrations
 */
public class EventManager {

	public EventManager() {
		setupEventListeners();
	}

	/**
	 * Setup all event listeners with Fabric API
	 */
	private void setupEventListeners() {
		// Register client tick event
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			EventBus.post(new UpdateEvent(1.0f));
		});
	}

	/**
	 * Register a keybinding
	 */
	public void registerKeybinding(String name, int key, String category) {
		KeyBinding keybinding = new KeyBinding(name, key, category);
		KeyBindingHelper.registerKeyBinding(keybinding);
	}
}
