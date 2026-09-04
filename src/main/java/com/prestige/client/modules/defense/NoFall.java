package com.prestige.client.modules.defense;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Prevents fall damage
 */
public class NoFall extends Module {
	private final Setting<Boolean> packet = new Setting<>("Packet", "Use packet mode", true);
	private final Setting<Boolean> motion = new Setting<>("Motion", "Use motion mode", false);
	private final Setting<Integer> distance = new Setting<>("Distance", "Minimum fall distance", 4).setRange(1, 20);

	public NoFall() {
		super("NoFall", "No fall damage", ModuleCategory.DEFENSE);
		registerSetting(packet);
		registerSetting(motion);
		registerSetting(distance);
	}

	public boolean usePacketMode() {
		return packet.getValue();
	}

	public boolean useMotionMode() {
		return motion.getValue();
	}

	public int getFallDistance() {
		return distance.getValue();
	}
}
