package com.prestige.client.modules.movement;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Quick bridge building for escape
 */
public class FastBridge extends Module {
	private final Setting<Integer> range = new Setting<>("Range", "Block placement range", 5).setRange(3, 10);
	private final Setting<Integer> delay = new Setting<>("Delay", "Delay between placements", 1).setRange(0, 10);
	private final Setting<Boolean> tower = new Setting<>("Tower", "Place blocks upward", false);
	private final Setting<Boolean> swing = new Setting<>("Swing", "Swing animation", true);

	public FastBridge() {
		super("FastBridge", "Quick bridge building", ModuleCategory.MOVEMENT);
		registerSetting(range);
		registerSetting(delay);
		registerSetting(tower);
		registerSetting(swing);
	}

	public int getRange() {
		return range.getValue();
	}

	public int getDelay() {
		return delay.getValue();
	}

	public boolean shouldTower() {
		return tower.getValue();
	}

	public boolean shouldSwing() {
		return swing.getValue();
	}
}
