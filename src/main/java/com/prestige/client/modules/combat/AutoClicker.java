package com.prestige.client.modules.combat;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;

/**
 * Automatic clicking/attacking
 */
public class AutoClicker extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Integer> cps = new Setting<>("CPS", "Clicks per second", 12).setRange(1, 30);
	private final Setting<Boolean> leftClick = new Setting<>("Left Click", "Auto left click", false);
	private final Setting<Boolean> rightClick = new Setting<>("Right Click", "Auto right click", false);
	private final Setting<Boolean> swing = new Setting<>("Swing", "Show swing animation", true);

	private int ticksSinceLastClick = 0;

	public AutoClicker() {
		super("AutoClicker", "Automatic mouse clicking", ModuleCategory.COMBAT);
		registerSetting(cps);
		registerSetting(leftClick);
		registerSetting(rightClick);
		registerSetting(swing);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null) {
			return;
		}

		ticksSinceLastClick++;

		int clickDelay = Math.max(1, 20 / cps.getValue());

		if (ticksSinceLastClick >= clickDelay) {
			if (leftClick.getValue()) {
				// Left click logic
				if (swing.getValue()) {
					client.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
				}
			}

			if (rightClick.getValue()) {
				// Right click logic
				if (swing.getValue()) {
					client.player.swingHand(net.minecraft.util.Hand.OFF_HAND);
				}
			}

			ticksSinceLastClick = 0;
		}
	}
}
