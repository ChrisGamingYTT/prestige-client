package com.prestige.client.modules.crystal;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.util.CrystalUtil;
import com.prestige.client.util.RotationUtil;
import com.prestige.client.util.TargetUtil;
import com.prestige.client.event.EventBus;
import com.prestige.client.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Auto-places End Crystals on nearby players
 */
public class CrystalAura extends Module {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final Setting<Double> range = new Setting<>("Range", "Detection range for crystal placement", 6.0).setRange(3.0, 12.0);
	private final Setting<Integer> delay = new Setting<>("Delay", "Delay between placements in ticks", 2).setRange(0, 10);
	private final Setting<Boolean> rotate = new Setting<>("Rotate", "Rotate to face placed crystals", true);
	private final Setting<Boolean> swing = new Setting<>("Swing", "Swing animation on place", true);
	private final Setting<Double> rotationSpeed = new Setting<>("Rotation Speed", "Speed of rotation in degrees", 45.0).setRange(1.0, 180.0);

	private int ticksSinceLastPlace = 0;

	public CrystalAura() {
		super("CrystalAura", "Automatically place End Crystals on enemies", ModuleCategory.CRYSTAL);
		registerSetting(range);
		registerSetting(delay);
		registerSetting(rotate);
		registerSetting(swing);
		registerSetting(rotationSpeed);

		EventBus.subscribe(UpdateEvent.class, this::onUpdate);
	}

	@Override
	public void onEnable() {
		super.onEnable();
		ticksSinceLastPlace = 0;
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	private void onUpdate(com.prestige.client.event.Event event) {
		if (!isEnabled() || client.player == null || client.world == null) {
			return;
		}

		ticksSinceLastPlace++;

		if (ticksSinceLastPlace < delay.getValue()) {
			return;
		}

		// Find nearby players
		var target = TargetUtil.getNearestPlayer(range.getValue());
		if (target.isEmpty()) {
			return;
		}

		PlayerEntity player = target.get();
		Vec3d playerPos = player.getPos();

		// Find valid placement spots
		var placements = CrystalUtil.findValidPlacements(playerPos, 3.0);
		if (placements.isEmpty()) {
			return;
		}

		// Choose closest placement
		BlockPos bestPlacement = placements.get(0);
		double closestDist = client.player.getPos().distanceTo(Vec3d.ofCenter(bestPlacement));

		for (BlockPos pos : placements) {
			double dist = client.player.getPos().distanceTo(Vec3d.ofCenter(pos));
			if (dist < closestDist) {
				bestPlacement = pos;
				closestDist = dist;
			}
		}

		// Rotate if enabled
		if (rotate.getValue()) {
			Vec3d targetPos = Vec3d.ofCenter(bestPlacement).add(0, 1, 0);
			float[] angles = new float[2];
			angles[0] = RotationUtil.calculateYaw(client.player.getPos(), targetPos);
			angles[1] = RotationUtil.calculatePitch(client.player.getPos(), targetPos);

			float currentYaw = client.player.getYaw();
			float currentPitch = client.player.getPitch();

			currentYaw = RotationUtil.smooth(currentYaw, angles[0], rotationSpeed.getValue().floatValue());
			currentPitch = RotationUtil.smooth(currentPitch, angles[1], rotationSpeed.getValue().floatValue());

			client.player.setYaw(currentYaw);
			client.player.setPitch(currentPitch);
		}

		// Place crystal
		if (closestDist <= 5.0) {
			placeBlock(bestPlacement);
			if (swing.getValue()) {
				client.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
			}
			ticksSinceLastPlace = 0;
		}
	}

	private void placeBlock(BlockPos pos) {
		// This would use packet sending in a real implementation
		// For now, this is a placeholder
	}
}
