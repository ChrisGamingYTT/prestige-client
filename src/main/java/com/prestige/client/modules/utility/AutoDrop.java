package com.prestige.client.modules.utility;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Automatically drop items
 */
public class AutoDrop extends Module {
	private final Setting<Integer> delay = new Setting<>("Delay", "Delay between drops", 5).setRange(1, 40);
	private final Setting<Boolean> dropGarbage = new Setting<>("Drop Garbage", "Auto-drop junk items", false);

	public AutoDrop() {
		super("AutoDrop", "Automatic item dropping", ModuleCategory.UTILITY);
		registerSetting(delay);
		registerSetting(dropGarbage);
	}
}
