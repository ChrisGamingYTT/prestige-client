package com.prestige.client.event.events;

import com.prestige.client.event.Event;
import net.minecraft.client.render.GameRenderer;

/**
 * Fired during render phase every frame
 */
public class RenderEvent extends Event {
	private final GameRenderer renderer;
	private final float tickDelta;

	public RenderEvent(GameRenderer renderer, float tickDelta) {
		this.renderer = renderer;
		this.tickDelta = tickDelta;
	}

	public GameRenderer getRenderer() {
		return renderer;
	}

	public float getTickDelta() {
		return tickDelta;
	}
}
