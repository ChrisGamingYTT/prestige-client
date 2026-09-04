package com.prestige.client.modules.crystal;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.util.CrystalUtil;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;

/**
 * Helps defend against enemy crystals
 */
public class CrystalDefense extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Boolean> avoid = new Setting<>("Avoid", "Move away from dangerous crystals", true);
	private final Setting<Double> dangerRange = new Setting<>("Danger Range", "Range to consider dangerous", 6.0).setRange(2.0, 12.0);
	private final Setting<Integer> prediction = new Setting<>("Prediction", "Ticks to predict ahead", 5).setRange(1, 20);

	public CrystalDefense() {
		super("CrystalDefense", "Predict and avoid crystal damage", ModuleCategory.CRYSTAL);
		registerSetting(avoid);
		registerSetting(dangerRange);
		registerSetting(prediction);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null) {
			return;
		}

		if (!avoid.getValue()) {
			return;
		}

		// Check for nearby crystals and predict damage
		var crystals = CrystalUtil.getNearByCrystals(dangerRange.getValue());
		
		for (var crystal : crystals) {
			float damage = CrystalUtil.calculateDamage(crystal.getPos(), client.player);
			if (damage > 2.0f) {
				// Would implement evasion logic here
			}
		}
	}
}
