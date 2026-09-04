package com.prestige.client.modules.combat;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.util.RotationUtil;
import com.prestige.client.util.TargetUtil;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Smooth head rotation system
 */
public class Rotation extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Double> speed = new Setting<>("Speed", "Rotation speed in degrees per tick", 45.0).setRange(1.0, 180.0);
	private final Setting<Boolean> smooth = new Setting<>("Smooth", "Use smooth interpolation", true);
	private final Setting<Boolean> silent = new Setting<>("Silent", "Hide rotation from others", true);
	private final Setting<Integer> tolerance = new Setting<>("Tolerance", "Angle tolerance in degrees", 5).setRange(1, 30);

	private float lastYaw = 0;
	private float lastPitch = 0;

	public Rotation() {
		super("Rotation", "Smooth rotation towards targets", ModuleCategory.COMBAT);
		registerSetting(speed);
		registerSetting(smooth);
		registerSetting(silent);
		registerSetting(tolerance);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null) {
			return;
		}

		lastYaw = client.player.getYaw();
		lastPitch = client.player.getPitch();
	}

	/**
	 * Rotate to face a player
	 */
	public void rotateToPlayer(PlayerEntity player) {
		if (client.player == null) return;

		float[] angles = RotationUtil.calculateAngles(player);
		rotateTo(angles[0], angles[1]);
	}

	/**
	 * Rotate to specific angles
	 */
	public void rotateTo(float yaw, float pitch) {
		if (client.player == null) return;

		if (smooth.getValue()) {
			float newYaw = RotationUtil.smooth(client.player.getYaw(), yaw, speed.getValue().floatValue());
			float newPitch = RotationUtil.smooth(client.player.getPitch(), pitch, speed.getValue().floatValue());
			client.player.setYaw(newYaw);
			client.player.setPitch(newPitch);
		} else {
			client.player.setYaw(yaw);
			client.player.setPitch(pitch);
		}
	}

	/**
	 * Check if rotation is locked to target
	 */
	public boolean isRotationLocked() {
		if (client.player == null) return false;
		
		var target = TargetUtil.getNearestPlayer(20.0);
		if (target.isEmpty()) return false;

		float[] angles = RotationUtil.calculateAngles(target.get());
		return RotationUtil.isRotationClose(client.player.getYaw(), client.player.getPitch(), 
			angles[0], angles[1], tolerance.getValue());
	}
}
