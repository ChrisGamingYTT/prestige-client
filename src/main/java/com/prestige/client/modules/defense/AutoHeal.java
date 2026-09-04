package com.prestige.client.modules.defense;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * Automatically eats gapples when health is low
 */
public class AutoHeal extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Integer> healthThreshold = new Setting<>("Health Threshold", "Health to start healing", 15).setRange(1, 20);
	private final Setting<Integer> delay = new Setting<>("Delay", "Delay between heals in ticks", 10).setRange(1, 40);
	private final Setting<Boolean> smartEat = new Setting<>("Smart Eat", "Only eat when safe", true);

	private int ticksSinceLastHeal = 0;

	public AutoHeal() {
		super("AutoHeal", "Automatically heal with gapples", ModuleCategory.DEFENSE);
		registerSetting(healthThreshold);
		registerSetting(delay);
		registerSetting(smartEat);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null) {
			return;
		}

		ticksSinceLastHeal++;

		if (client.player.getHealth() > healthThreshold.getValue()) {
			return;
		}

		if (ticksSinceLastHeal < delay.getValue()) {
			return;
		}

		// Find gapple in inventory
		int gappleSlot = findGapple();
		if (gappleSlot == -1) {
			return;
		}

		// Eat gapple
		eatItem(gappleSlot);
		ticksSinceLastHeal = 0;
	}

	/**
	 * Find gapple in inventory
	 */
	private int findGapple() {
		for (int i = 0; i < client.player.getInventory().main.size(); i++) {
			ItemStack stack = client.player.getInventory().main.get(i);
			if (stack.getItem() == Items.GOLDEN_APPLE || stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Eat item from slot
	 */
	private void eatItem(int slot) {
		// This would be implemented with proper inventory management
	}
}
