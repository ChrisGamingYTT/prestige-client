package com.prestige.client.modules.movement;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;

/**
 * Continue moving when not pressing keys
 */
public class AutoWalk extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Boolean> forward = new Setting<>("Forward", "Walk forward", true);
	private final Setting<Boolean> strafe = new Setting<>("Strafe", "Strafe sideways", false);
	private final Setting<Integer> direction = new Setting<>("Direction", "Direction (0=forward, 1=left, 2=right, 3=back)", 0).setRange(0, 3);

	public AutoWalk() {
		super("AutoWalk", "Auto-walk in selected direction", ModuleCategory.MOVEMENT);
		registerSetting(forward);
		registerSetting(strafe);
		registerSetting(direction);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null) {
			return;
		}

		if (forward.getValue()) {
			client.player.input.movementForward = 1.0f;
		}

		if (strafe.getValue()) {
			int dir = direction.getValue();
			if (dir == 1) client.player.input.movementSideways = -1.0f;
			else if (dir == 2) client.player.input.movementSideways = 1.0f;
		}
	}
}
