package com.prestige.client.modules.render;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Custom rendering for End Crystals
 */
public class CrystalRender extends Module {
	private final Setting<Boolean> box = new Setting<>("Box", "Draw crystal bounding box", true);
	private final Setting<Boolean> radius = new Setting<>("Radius", "Show explosion radius", true);
	private final Setting<Boolean> line = new Setting<>("Line", "Draw line to crystals", false);
	private final Setting<Boolean> health = new Setting<>("Health", "Show crystal health", true);

	public CrystalRender() {
		super("CrystalRender", "Enhanced crystal rendering", ModuleCategory.RENDER);
		registerSetting(box);
		registerSetting(radius);
		registerSetting(line);
		registerSetting(health);
	}
}
