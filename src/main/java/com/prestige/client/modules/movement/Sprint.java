package com.prestige.client.modules.movement;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;

/**
 * Auto-sprint in all directions
 */
public class Sprint extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Boolean> allDirections = new Setting<>("All Directions", "Sprint in all directions", false);
	private final Setting<Boolean> onlyForward = new Setting<>("Only Forward", "Only sprint forward", true);
	private final Setting<Integer> delay = new Setting<>("Delay", "Delay before sprinting in ticks", 0).setRange(0, 10);

	private int ticksSinceMoved = 0;

	public Sprint() {
		super("Sprint", "Automatic sprinting", ModuleCategory.MOVEMENT);
		registerSetting(allDirections);
		registerSetting(onlyForward);
		registerSetting(delay);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null) {
			return;
		}

		boolean moving = false;
		
		if (allDirections.getValue()) {
			// Check if moving in any direction
			moving = client.player.input.movementForward != 0 || client.player.input.movementSideways != 0;
		} else if (onlyForward.getValue()) {
			// Only sprint forward
			moving = client.player.input.movementForward > 0;
		}

		if (moving) {
			ticksSinceMoved++;
			if (ticksSinceMoved > delay.getValue()) {
				client.player.setSprinting(true);
			}
		} else {
			client.player.setSprinting(false);
			ticksSinceMoved = 0;
		}
	}
}
