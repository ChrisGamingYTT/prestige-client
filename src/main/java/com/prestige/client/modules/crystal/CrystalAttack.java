package com.prestige.client.modules.crystal;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.util.CrystalUtil;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.EndCrystalEntity;

/**
 * Auto-attacks End Crystals
 */
public class CrystalAttack extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Double> range = new Setting<>("Range", "Attack range for crystals", 5.0).setRange(3.0, 10.0);
	private final Setting<Integer> delay = new Setting<>("Delay", "Delay between attacks in ticks", 1).setRange(0, 10);
	private final Setting<Boolean> swing = new Setting<>("Swing", "Swing animation on attack", true);
	private final Setting<Boolean> prioritizeOwn = new Setting<>("Prioritize Own", "Attack own crystals first", true);

	private int ticksSinceLastAttack = 0;

	public CrystalAttack() {
		super("CrystalAttack", "Automatically attack nearby End Crystals", ModuleCategory.CRYSTAL);
		registerSetting(range);
		registerSetting(delay);
		registerSetting(swing);
		registerSetting(prioritizeOwn);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	@Override
	public void onEnable() {
		super.onEnable();
		ticksSinceLastAttack = 0;
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null || client.world == null) {
			return;
		}

		ticksSinceLastAttack++;

		if (ticksSinceLastAttack < delay.getValue()) {
			return;
		}

		// Find nearby crystals
		var crystals = CrystalUtil.getNearByCrystals(range.getValue());
		if (crystals.isEmpty()) {
			return;
		}

		// Get nearest crystal
		EndCrystalEntity target = crystals.get(0);
		double nearestDist = client.player.distanceTo(target);

		for (EndCrystalEntity crystal : crystals) {
			double dist = client.player.distanceTo(crystal);
			if (dist < nearestDist) {
				target = crystal;
				nearestDist = dist;
			}
		}

		// Attack crystal
		if (nearestDist <= range.getValue()) {
			attackEntity(target);
			if (swing.getValue()) {
				client.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
			}
			ticksSinceLastAttack = 0;
		}
	}

	private void attackEntity(EndCrystalEntity crystal) {
		// This would be implemented with proper packet sending
		// Placeholder for now
	}
}
