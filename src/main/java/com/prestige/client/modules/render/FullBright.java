package com.prestige.client.modules.render;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Increases world brightness
 */
public class FullBright extends Module {
	private final Setting<Double> brightness = new Setting<>("Brightness", "Brightness level", 1.0).setRange(0.5, 2.0);
	private final Setting<Boolean> gamma = new Setting<>("Gamma", "Adjust gamma", true);

	public FullBright() {
		super("FullBright", "Brightness enhancement", ModuleCategory.RENDER);
		registerSetting(brightness);
		registerSetting(gamma);
	}

	public double getBrightness() {
		return brightness.getValue();
	}
}
