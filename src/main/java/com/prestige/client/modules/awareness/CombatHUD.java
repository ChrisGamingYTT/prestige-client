package com.prestige.client.modules.awareness;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Displays combat information on HUD
 */
public class CombatHUD extends Module {
	private final Setting<Boolean> target = new Setting<>("Target Info", "Show current target", true);
	private final Setting<Boolean> nearbyPlayers = new Setting<>("Nearby Players", "Show nearby player list", true);
	private final Setting<Boolean> health = new Setting<>("Health", "Show player health", true);
	private final Setting<Boolean> hunger = new Setting<>("Hunger", "Show hunger level", true);
	private final Setting<Integer> maxPlayers = new Setting<>("Max Players", "Maximum players to show", 5).setRange(1, 20);

	public CombatHUD() {
		super("CombatHUD", "Combat information display", ModuleCategory.AWARENESS);
		registerSetting(target);
		registerSetting(nearbyPlayers);
		registerSetting(health);
		registerSetting(hunger);
		registerSetting(maxPlayers);
	}
}
