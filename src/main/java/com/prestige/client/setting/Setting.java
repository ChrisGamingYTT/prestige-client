package com.prestige.client.setting;

/**
 * Represents a configurable setting for a module
 */
public class Setting<T> {
	private final String name;
	private final String description;
	private T value;
	private final T defaultValue;
	private T min;
	private T max;

	public Setting(String name, String description, T defaultValue) {
		this.name = name;
		this.description = description;
		this.value = defaultValue;
		this.defaultValue = defaultValue;
	}

	public Setting<T> setRange(T min, T max) {
		this.min = min;
		this.max = max;
		return this;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getDefaultValue() {
		return defaultValue;
	}

	public T getMin() {
		return min;
	}

	public T getMax() {
		return max;
	}

	public void reset() {
		this.value = defaultValue;
	}
}
