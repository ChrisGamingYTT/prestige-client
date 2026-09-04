package com.prestige.client.module;

/**
 * Categories for organizing modules in the GUI
 */
public enum ModuleCategory {
	CRYSTAL("Crystal"),
	COMBAT("Combat"),
	DEFENSE("Defense"),
	MOVEMENT("Movement"),
	RENDER("Render"),
	AWARENESS("Awareness"),
	UTILITY("Utility");

	private final String displayName;

	ModuleCategory(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
