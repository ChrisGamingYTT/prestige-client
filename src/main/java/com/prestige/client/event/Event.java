package com.prestige.client.event;

/**
 * Base event class for all events in Prestige Client
 */
public abstract class Event {
	private boolean cancelled = false;

	public void cancel() {
		this.cancelled = true;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
