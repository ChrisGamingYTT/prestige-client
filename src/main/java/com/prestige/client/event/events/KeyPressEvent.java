package com.prestige.client.event.events;

import com.prestige.client.event.Event;

/**
 * Fired when a key is pressed
 */
public class KeyPressEvent extends Event {
	private final int key;
	private final int scancode;

	public KeyPressEvent(int key, int scancode) {
		this.key = key;
		this.scancode = scancode;
	}

	public int getKey() {
		return key;
	}

	public int getScancode() {
		return scancode;
	}
}
