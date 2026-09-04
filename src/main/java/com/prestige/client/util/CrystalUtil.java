package com.prestige.client.util;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

/**
 * Utility class for crystal detection and placement
 */
public class CrystalUtil {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	/**
	 * Find all nearby crystals
	 */
	public static List<EndCrystalEntity> getNearByCrystals(double range) {
		List<EndCrystalEntity> crystals = new ArrayList<>();
		if (client.world == null || client.player == null) {
			return crystals;
		}

		for (Entity entity : client.world.getEntities()) {
			if (entity instanceof EndCrystalEntity crystal) {
				double distance = client.player.distanceTo(crystal);
				if (distance <= range) {
					crystals.add(crystal);
				}
			}
		}

		return crystals;
	}

	/**
	 * Get nearest crystal to a position
	 */
	public static Optional<EndCrystalEntity> getNearestCrystal(Vec3d pos, double range) {
		List<EndCrystalEntity> crystals = getNearByCrystals(range);
		if (crystals.isEmpty()) {
			return Optional.empty();
		}

		EndCrystalEntity nearest = crystals.get(0);
		double nearestDist = pos.distanceTo(nearest.getPos());

		for (EndCrystalEntity crystal : crystals) {
			double dist = pos.distanceTo(crystal.getPos());
			if (dist < nearestDist) {
				nearest = crystal;
				nearestDist = dist;
			}
		}

		return Optional.of(nearest);
	}

	/**
	 * Check if a block is valid for crystal placement
	 */
	public static boolean isValidCrystalPlacement(BlockPos pos) {
		if (client.world == null) {
			return false;
		}

		World world = client.world;
		BlockPos abovePos = pos.up();

		// Block must be obsidian or bedrock
		if (!(world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN ||
				world.getBlockState(pos).getBlock() == Blocks.BEDROCK)) {
			return false;
		}

		// Space above must be empty
		if (!world.getBlockState(abovePos).isAir()) {
			return false;
		}

		// Space above that must also be empty
		if (!world.getBlockState(abovePos.up()).isAir()) {
			return false;
		}

		return true;
	}

	/**
	 * Find valid crystal placements around a position
	 */
	public static List<BlockPos> findValidPlacements(Vec3d pos, double radius) {
		List<BlockPos> placements = new ArrayList<>();
		BlockPos center = BlockPos.ofFloored(pos);

		int searchRadius = (int) Math.ceil(radius);
		for (int x = center.getX() - searchRadius; x <= center.getX() + searchRadius; x++) {
			for (int z = center.getZ() - searchRadius; z <= center.getZ() + searchRadius; z++) {
				for (int y = center.getY() - 2; y <= center.getY() + 2; y++) {
					BlockPos blockPos = new BlockPos(x, y, z);
					if (isValidCrystalPlacement(blockPos)) {
						double distance = pos.distanceTo(Vec3d.ofCenter(blockPos));
						if (distance <= radius) {
							placements.add(blockPos);
						}
					}
				}
			}
		}

		return placements;
	}

	/**
	 * Calculate damage from crystal at position to player
	 */
	public static float calculateDamage(Vec3d crystalPos, PlayerEntity player) {
		double distance = crystalPos.distanceTo(player.getPos().add(0, player.getHeight() / 2, 0));
		
		// Crystal explosion damage calculation
		float base = 6.0f;
		double maxDistance = 12.0;
		
		if (distance >= maxDistance) {
			return 0;
		}
		
		double damage = base * ((1.0 - (distance / maxDistance)) * 0.5);
		
		// Apply armor reduction (simplified)
		float armorReduction = (float) (damage * 0.2);
		damage -= armorReduction;
		
		return (float) Math.max(0, damage);
	}

	/**
	 * Get closest player to a position
	 */
	public static Optional<PlayerEntity> getNearestPlayerToPosition(Vec3d pos, double range) {
		if (client.world == null) {
			return Optional.empty();
		}

		PlayerEntity nearest = null;
		double nearestDist = range;

		for (Entity entity : client.world.getEntities()) {
			if (entity instanceof PlayerEntity player) {
				if (player != client.player && !player.isSpectator()) {
					double distance = pos.distanceTo(player.getPos());
					if (distance < nearestDist) {
						nearest = player;
						nearestDist = distance;
					}
				}
			}
		}

		return Optional.ofNullable(nearest);
	}

	/**
	 * Check if crystal position is near a player
	 */
	public static boolean isCrystalNearPlayer(Vec3d crystalPos, double range) {
		return getNearestPlayerToPosition(crystalPos, range).isPresent();
	}
}
