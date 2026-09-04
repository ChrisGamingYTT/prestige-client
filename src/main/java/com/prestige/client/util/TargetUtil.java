package com.prestige.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class for target finding and filtering
 */
public class TargetUtil {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	/**
	 * Get nearby players within range
	 */
	public static List<PlayerEntity> getNearbyPlayers(double range) {
		List<PlayerEntity> nearby = new ArrayList<>();
		if (client.world == null || client.player == null) {
			return nearby;
		}

		for (Entity entity : client.world.getEntities()) {
			if (entity instanceof PlayerEntity player) {
				if (player != client.player && !player.isSpectator()) {
					double distance = client.player.distanceTo(player);
					if (distance <= range) {
						nearby.add(player);
					}
				}
			}
		}

		return nearby;
	}

	/**
	 * Get nearest player within range
	 */
	public static Optional<PlayerEntity> getNearestPlayer(double range) {
		List<PlayerEntity> nearby = getNearbyPlayers(range);
		if (nearby.isEmpty()) {
			return Optional.empty();
		}

		PlayerEntity nearest = nearby.get(0);
		double nearestDist = client.player.distanceTo(nearest);

		for (PlayerEntity player : nearby) {
			double dist = client.player.distanceTo(player);
			if (dist < nearestDist) {
				nearest = player;
				nearestDist = dist;
			}
		}

		return Optional.of(nearest);
	}

	/**
	 * Get player with lowest health in range
	 */
	public static Optional<PlayerEntity> getLowestHealthPlayer(double range) {
		List<PlayerEntity> nearby = getNearbyPlayers(range);
		if (nearby.isEmpty()) {
			return Optional.empty();
		}

		PlayerEntity lowest = nearby.get(0);
		float lowestHealth = lowest.getHealth();

		for (PlayerEntity player : nearby) {
			if (player.getHealth() < lowestHealth) {
				lowest = player;
				lowestHealth = player.getHealth();
			}
		}

		return Optional.of(lowest);
	}

	/**
	 * Check if a player is alive
	 */
	public static boolean isAlive(LivingEntity entity) {
		return !entity.isDead() && entity.getHealth() > 0;
	}

	/**
	 * Get distance to entity
	 */
	public static double getDistance(Entity entity) {
		if (client.player == null) {
			return Double.MAX_VALUE;
		}
		return client.player.distanceTo(entity);
	}

	/**
	 * Check if entity is in view
	 */
	public static boolean isInView(Entity entity) {
		if (client.player == null) {
			return false;
		}
		// Simple FOV check
		float[] angles = RotationUtil.calculateAngles(entity);
		float yawDiff = RotationUtil.getYawDifference(client.player.getYaw(), angles[0]);
		float pitchDiff = Math.abs(client.player.getPitch() - angles[1]);
		return Math.abs(yawDiff) < 90 && Math.abs(pitchDiff) < 90;
	}
}
