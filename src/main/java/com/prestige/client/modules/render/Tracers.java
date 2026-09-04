package com.prestige.client.modules.render;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Draw lines to targets and crystals
 */
public class Tracers extends Module {
	private final Setting<Boolean> players = new Setting<>("Players", "Trace lines to players", true);
	private final Setting<Boolean> crystals = new Setting<>("Crystals", "Trace lines to crystals", true);
	private final Setting<Double> range = new Setting<>("Range", "Tracer range", 64.0).setRange(10.0, 256.0);
	private final Setting<Integer> width = new Setting<>("Width", "Line width", 2).setRange(1, 10);

	public Tracers() {
		super("Tracers", "Draw tracer lines to entities", ModuleCategory.RENDER);
		registerSetting(players);
		registerSetting(crystals);
		registerSetting(range);
		registerSetting(width);
	}
}
