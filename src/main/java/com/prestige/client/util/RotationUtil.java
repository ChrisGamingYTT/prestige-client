package com.prestige.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/**
 * Utility class for rotation calculations
 */
public class RotationUtil {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	/**
	 * Calculate yaw to look at a position
	 */
	public static float calculateYaw(Vec3d from, Vec3d to) {
		double dx = to.x - from.x;
		double dz = to.z - from.z;
		return (float) (Math.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
	}

	/**
	 * Calculate pitch to look at a position
	 */
	public static float calculatePitch(Vec3d from, Vec3d to) {
		double dx = to.x - from.x;
		double dy = to.y - from.y;
		double dz = to.z - from.z;
		double distance = Math.sqrt(dx * dx + dz * dz);
		return (float) -(Math.atan2(dy, distance) * 180.0D / Math.PI);
	}

	/**
	 * Calculate yaw and pitch to an entity
	 */
	public static float[] calculateAngles(Entity entity) {
		if (client.player == null) return new float[]{0, 0};
		Vec3d playerPos = client.player.getPos();
		Vec3d entityPos = entity.getPos().add(0, entity.getHeight() / 2, 0);
		float yaw = calculateYaw(playerPos, entityPos);
		float pitch = calculatePitch(playerPos, entityPos);
		return new float[]{yaw, pitch};
	}

	/**
	 * Calculate distance between two vectors
	 */
	public static double distance(Vec3d from, Vec3d to) {
		return from.distanceTo(to);
	}

	/**
	 * Smooth rotation towards a target angle
	 */
	public static float smooth(float current, float target, float speed) {
		float delta = target - current;
		
		// Normalize angle
		while (delta > 180) delta -= 360;
		while (delta < -180) delta += 360;
		
		if (Math.abs(delta) < speed) {
			return target;
		}
		
		return current + (delta > 0 ? speed : -speed);
	}

	/**
	 * Get normalized yaw difference
	 */
	public static float getYawDifference(float from, float to) {
		float diff = to - from;
		while (diff > 180) diff -= 360;
		while (diff < -180) diff += 360;
		return diff;
	}

	/**
	 * Check if rotation is close enough
	 */
	public static boolean isRotationClose(float currentYaw, float currentPitch, float targetYaw, float targetPitch, float tolerance) {
		float yawDiff = Math.abs(getYawDifference(currentYaw, targetYaw));
		float pitchDiff = Math.abs(currentPitch - targetPitch);
		return yawDiff < tolerance && pitchDiff < tolerance;
	}
}
