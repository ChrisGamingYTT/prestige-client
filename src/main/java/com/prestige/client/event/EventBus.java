package com.prestige.client.event;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Central event management system for the client
 */
public class EventBus {
	private static final Map<Class<?>, List<EventListener>> listeners = new HashMap<>();

	/**
	 * Subscribe to an event type
	 */
	public static void subscribe(Class<? extends Event> eventClass, EventListener listener) {
		listeners.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(listener);
	}

	/**
	 * Unsubscribe from an event type
	 */
	public static void unsubscribe(Class<? extends Event> eventClass, EventListener listener) {
		List<EventListener> eventListeners = listeners.get(eventClass);
		if (eventListeners != null) {
			eventListeners.remove(listener);
		}
	}

	/**
	 * Post an event to all listeners
	 */
	public static void post(Event event) {
		List<EventListener> eventListeners = listeners.get(event.getClass());
		if (eventListeners != null) {
			for (EventListener listener : eventListeners) {
				try {
					listener.onEvent(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Clear all listeners
	 */
	public static void clear() {
		listeners.clear();
	}

	@FunctionalInterface
	public interface EventListener {
		void onEvent(Event event);
	}
}
