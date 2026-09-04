package com.prestige.client.modules.movement;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Enhanced air movement control
 */
public class Strafe extends Module {
	private final Setting<Double> speed = new Setting<>("Speed", "Air strafe speed", 1.0).setRange(0.1, 3.0);
	private final Setting<Boolean> autoStrafe = new Setting<>("Auto Strafe", "Auto strafe away from players", false);
	private final Setting<Double> strafeRange = new Setting<>("Range", "Range for auto strafe", 8.0).setRange(3.0, 20.0);

	public Strafe() {
		super("Strafe", "Enhanced air movement", ModuleCategory.MOVEMENT);
		registerSetting(speed);
		registerSetting(autoStrafe);
		registerSetting(strafeRange);
	}

	public double getSpeed() {
		return speed.getValue();
	}

	public boolean shouldAutoStrafe() {
		return autoStrafe.getValue();
	}

	public double getStrafeRange() {
		return strafeRange.getValue();
	}
}
