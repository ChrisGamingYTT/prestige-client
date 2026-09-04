package com.prestige.client.modules.render;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Renders boxes around nearby players
 */
public class PlayerESP extends Module {
	private final Setting<Boolean> box = new Setting<>("Box", "Draw box around players", true);
	private final Setting<Boolean> health = new Setting<>("Health", "Show player health", true);
	private final Setting<Boolean> distance = new Setting<>("Distance", "Show distance to player", true);
	private final Setting<Boolean> armor = new Setting<>("Armor", "Show armor status", false);
	private final Setting<Double> range = new Setting<>("Range", "Rendering range", 128.0).setRange(10.0, 512.0);

	public PlayerESP() {
		super("PlayerESP", "Highlight nearby players", ModuleCategory.RENDER);
		registerSetting(box);
		registerSetting(health);
		registerSetting(distance);
		registerSetting(armor);
		registerSetting(range);
	}
}
