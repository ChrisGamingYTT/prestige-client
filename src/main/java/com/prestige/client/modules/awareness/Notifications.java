package com.prestige.client.modules.awareness;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Chat and visual notifications
 */
public class Notifications extends Module {
	private final Setting<Boolean> lowHealth = new Setting<>("Low Health", "Alert when low health", true);
	private final Setting<Boolean> playerNear = new Setting<>("Player Near", "Alert on new player nearby", true);
	private final Setting<Boolean> crystalNear = new Setting<>("Crystal Near", "Alert on crystal nearby", true);
	private final Setting<Integer> healthThreshold = new Setting<>("Health Threshold", "Health to alert at", 10).setRange(1, 20);

	public Notifications() {
		super("Notifications", "Chat and notification alerts", ModuleCategory.AWARENESS);
		registerSetting(lowHealth);
		registerSetting(playerNear);
		registerSetting(crystalNear);
		registerSetting(healthThreshold);
	}
}
