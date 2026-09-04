package com.prestige.client.modules.utility;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Automatically switch to best tool
 */
public class AutoTool extends Module {
	private final Setting<Boolean> pickaxe = new Setting<>("Pickaxe", "Switch to pickaxe for mining", true);
	private final Setting<Boolean> axe = new Setting<>("Axe", "Switch to axe for wood", true);
	private final Setting<Boolean> sword = new Setting<>("Sword", "Switch to sword for combat", true);
	private final Setting<Integer> delay = new Setting<>("Delay", "Delay between switches", 2).setRange(0, 10);

	public AutoTool() {
		super("AutoTool", "Automatic tool switching", ModuleCategory.UTILITY);
		registerSetting(pickaxe);
		registerSetting(axe);
		registerSetting(sword);
		registerSetting(delay);
	}
}
