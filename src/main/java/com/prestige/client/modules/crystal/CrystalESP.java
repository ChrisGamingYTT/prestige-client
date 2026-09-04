package com.prestige.client.modules.crystal;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.util.CrystalUtil;

/**
 * Renders End Crystals with visual indicators
 */
public class CrystalESP extends Module {
	private final Setting<Boolean> box = new Setting<>("Box", "Draw box around crystals", true);
	private final Setting<Boolean> healthBar = new Setting<>("Health", "Draw crystal health", true);
	private final Setting<Boolean> distance = new Setting<>("Distance", "Show distance to crystal", true);
	private final Setting<Boolean> radius = new Setting<>("Radius", "Show explosion radius", false);
	private final Setting<Double> range = new Setting<>("Range", "Rendering range", 64.0).setRange(10.0, 256.0);

	public CrystalESP() {
		super("CrystalESP", "Highlight nearby End Crystals", ModuleCategory.CRYSTAL);
		registerSetting(box);
		registerSetting(healthBar);
		registerSetting(distance);
		registerSetting(radius);
		registerSetting(range);
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}
