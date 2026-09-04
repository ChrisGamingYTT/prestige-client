package com.prestige.client.event.events;

import com.prestige.client.event.Event;

/**
 * Fired every tick (20 times per second)
 */
public class UpdateEvent extends Event {
	private final float partialTicks;

	public UpdateEvent(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}
}
