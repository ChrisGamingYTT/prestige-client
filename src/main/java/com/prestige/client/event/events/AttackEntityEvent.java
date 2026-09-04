package com.prestige.client.event.events;

import com.prestige.client.event.Event;
import net.minecraft.entity.Entity;

/**
 * Fired when the player attacks an entity
 */
public class AttackEntityEvent extends Event {
	private final Entity entity;

	public AttackEntityEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
