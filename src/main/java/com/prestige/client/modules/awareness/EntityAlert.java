package com.prestige.client.modules.awareness;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Alerts when players target you
 */
public class EntityAlert extends Module {
	private final Setting<Double> range = new Setting<>("Range", "Alert range", 64.0).setRange(10.0, 256.0);
	private final Setting<Boolean> sound = new Setting<>("Sound", "Play alert sound", true);
	private final Setting<Boolean> visual = new Setting<>("Visual", "Show visual alert", true);
	private final Setting<Boolean> chat = new Setting<>("Chat", "Show chat message", false);

	public EntityAlert() {
		super("EntityAlert", "Entity targeting alerts", ModuleCategory.AWARENESS);
		registerSetting(range);
		registerSetting(sound);
		registerSetting(visual);
		registerSetting(chat);
	}
}
