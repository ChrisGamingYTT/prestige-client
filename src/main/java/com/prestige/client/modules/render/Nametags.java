package com.prestige.client.modules.render;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Enhanced nametag rendering with health info
 */
public class Nametags extends Module {
	private final Setting<Boolean> health = new Setting<>("Health", "Show player health", true);
	private final Setting<Boolean> armor = new Setting<>("Armor", "Show armor", true);
	private final Setting<Boolean> distance = new Setting<>("Distance", "Show distance", true);
	private final Setting<Integer> scale = new Setting<>("Scale", "Text scale", 1).setRange(1, 3);

	public Nametags() {
		super("Nametags", "Enhanced player nametags", ModuleCategory.RENDER);
		registerSetting(health);
		registerSetting(armor);
		registerSetting(distance);
		registerSetting(scale);
	}
}
