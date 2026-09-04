package com.prestige.client.modules.movement;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;

/**
 * Momentum-based jumping for faster movement
 */
public class BunnyHop extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Double> speed = new Setting<>("Speed", "Bunny hop speed multiplier", 1.3).setRange(1.0, 2.0);
	private final Setting<Integer> delay = new Setting<>("Delay", "Delay between hops in ticks", 1).setRange(1, 10);
	private final Setting<Boolean> autoJump = new Setting<>("Auto Jump", "Automatically jump", true);

	private int hopTicks = 0;

	public BunnyHop() {
		super("BunnyHop", "Momentum-based jumping", ModuleCategory.MOVEMENT);
		registerSetting(speed);
		registerSetting(delay);
		registerSetting(autoJump);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null) {
			return;
		}

		hopTicks++;

		if (hopTicks >= delay.getValue()) {
			if (autoJump.getValue() && client.player.isOnGround()) {
				client.player.jump();
				hopTicks = 0;
			}
		}
	}
}
