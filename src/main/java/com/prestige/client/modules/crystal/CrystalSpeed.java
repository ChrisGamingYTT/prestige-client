package com.prestige.client.modules.crystal;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.util.CrystalUtil;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffects;

/**
 * Applies speed boost while placing/attacking crystals
 */
public class CrystalSpeed extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Double> multiplier = new Setting<>("Speed", "Speed multiplier during crystal activity", 1.5).setRange(1.0, 3.0);
	private final Setting<Integer> duration = new Setting<>("Duration", "Duration of speed boost in ticks", 20).setRange(1, 100);
	private final Setting<Boolean> auto = new Setting<>("Auto", "Automatically apply based on crystal activity", true);

	private int speedTicks = 0;

	public CrystalSpeed() {
		super("CrystalSpeed", "Speed boost while using crystals", ModuleCategory.CRYSTAL);
		registerSetting(multiplier);
		registerSetting(duration);
		registerSetting(auto);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null) {
			return;
		}

		// Detect crystal activity
		var crystals = CrystalUtil.getNearByCrystals(8.0);
		
		if (!crystals.isEmpty() && auto.getValue()) {
			speedTicks = duration.getValue();
		}

		if (speedTicks > 0) {
			applySpeedBoost();
			speedTicks--;
		}
	}

	private void applySpeedBoost() {
		if (client.player == null) return;
		// Apply speed effect
		// This would be implemented with proper potion effect application
	}
}
