package com.prestige.client.modules.combat;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.util.TargetUtil;
import com.prestige.client.util.RotationUtil;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Automatically attacks nearby players
 */
public class KillAura extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Double> range = new Setting<>("Range", "Attack range", 5.5).setRange(3.0, 10.0);
	private final Setting<Integer> cps = new Setting<>("CPS", "Clicks per second", 15).setRange(1, 30);
	private final Setting<Boolean> rotate = new Setting<>("Rotate", "Rotate to target", true);
	private final Setting<Boolean> swing = new Setting<>("Swing", "Swing animation", true);
	private final Setting<Double> rotationSpeed = new Setting<>("Rotation Speed", "Rotation speed in degrees", 45.0).setRange(1.0, 180.0);

	private int ticksSinceLastAttack = 0;
	private PlayerEntity currentTarget;

	public KillAura() {
		super("KillAura", "Auto-attack nearby players", ModuleCategory.COMBAT);
		registerSetting(range);
		registerSetting(cps);
		registerSetting(rotate);
		registerSetting(swing);
		registerSetting(rotationSpeed);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null || client.world == null) {
			return;
		}

		ticksSinceLastAttack++;

		// Find target
		var target = TargetUtil.getNearestPlayer(range.getValue());
		if (target.isEmpty()) {
			currentTarget = null;
			return;
		}

		currentTarget = target.get();

		// Calculate delay between attacks based on CPS
		int attackDelay = Math.max(1, 20 / cps.getValue());

		if (ticksSinceLastAttack >= attackDelay) {
			// Rotate if enabled
			if (rotate.getValue()) {
				float[] angles = RotationUtil.calculateAngles(currentTarget);
				float newYaw = RotationUtil.smooth(client.player.getYaw(), angles[0], rotationSpeed.getValue().floatValue());
				float newPitch = RotationUtil.smooth(client.player.getPitch(), angles[1], rotationSpeed.getValue().floatValue());
				client.player.setYaw(newYaw);
				client.player.setPitch(newPitch);
			}

			// Attack
			if (swing.getValue()) {
				client.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
			}
			
			ticksSinceLastAttack = 0;
		}
	}

	public PlayerEntity getCurrentTarget() {
		return currentTarget;
	}
}
