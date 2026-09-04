package com.prestige.client.modules.defense;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;

/**
 * Reduces knockback from hits
 */
public class Velocity extends Module {
	private final Setting<Integer> reduction = new Setting<>("Reduction", "Knockback reduction percentage", 80).setRange(0, 100);
	private final Setting<Boolean> crystals = new Setting<>("Crystals", "Reduce crystal knockback", true);
	private final Setting<Boolean> hits = new Setting<>("Hits", "Reduce hit knockback", true);
	private final Setting<Boolean> explosions = new Setting<>("Explosions", "Reduce explosion knockback", false);

	public Velocity() {
		super("Velocity", "Reduce knockback from damage", ModuleCategory.DEFENSE);
		registerSetting(reduction);
		registerSetting(crystals);
		registerSetting(hits);
		registerSetting(explosions);
	}

	public int getReduction() {
		return reduction.getValue();
	}

	public boolean shouldReduceCrystalKnockback() {
		return crystals.getValue();
	}

	public boolean shouldReduceHitKnockback() {
		return hits.getValue();
	}

	public boolean shouldReduceExplosionKnockback() {
		return explosions.getValue();
	}
}
