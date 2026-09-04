package com.prestige.client.modules.awareness;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Minimap showing player positions
 */
public class Radar extends Module {
	private final Setting<Integer> size = new Setting<>("Size", "Radar size in pixels", 100).setRange(50, 200);
	private final Setting<Double> range = new Setting<>("Range", "Radar range", 64.0).setRange(10.0, 256.0);
	private final Setting<Boolean> showPlayers = new Setting<>("Players", "Show player dots", true);
	private final Setting<Boolean> showCrystals = new Setting<>("Crystals", "Show crystal dots", true);

	public Radar() {
		super("Radar", "Minimap radar", ModuleCategory.AWARENESS);
		registerSetting(size);
		registerSetting(range);
		registerSetting(showPlayers);
		registerSetting(showCrystals);
	}
}
