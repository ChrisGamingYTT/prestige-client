package com.prestige.client.modules.defense;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;

/**
 * Prevents falling into the void
 */
public class AntiVoid extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Integer> yLevel = new Setting<>("Y Level", "Y level to activate at", 0).setRange(-64, 256);
	private final Setting<Boolean> jump = new Setting<>("Jump", "Jump to safety", true);
	private final Setting<Boolean> placeBlocks = new Setting<>("Place Blocks", "Place blocks under you", false);

	public AntiVoid() {
		super("AntiVoid", "Anti-void protection", ModuleCategory.DEFENSE);
		registerSetting(yLevel);
		registerSetting(jump);
		registerSetting(placeBlocks);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null || client.world == null) {
			return;
		}

		if (client.player.getY() < yLevel.getValue()) {
			if (jump.getValue()) {
				client.player.jump();
			}

			if (placeBlocks.getValue()) {
				// Would implement block placing logic
			}
		}
	}
}
