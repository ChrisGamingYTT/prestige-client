package com.prestige.client.modules.utility;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Always show player coordinates
 */
public class Coordinates extends Module {
	private final Setting<Boolean> xyz = new Setting<>("XYZ", "Show coordinates", true);
	private final Setting<Boolean> biome = new Setting<>("Biome", "Show biome", true);
	private final Setting<Integer> scale = new Setting<>("Scale", "Text scale", 1).setRange(1, 3);
	private final Setting<Boolean> background = new Setting<>("Background", "Draw text background", true);

	public Coordinates() {
		super("Coordinates", "Display coordinates", ModuleCategory.UTILITY);
		registerSetting(xyz);
		registerSetting(biome);
		registerSetting(scale);
		registerSetting(background);
	}
}
