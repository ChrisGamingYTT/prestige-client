package com.prestige.client.modules.combat;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.setting.Setting;
import com.prestige.client.util.TargetUtil;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Helps with targeting and target switching
 */
public class TargetAssist extends Module {
	private final Setting<Boolean> lowestHealth = new Setting<>("Lowest Health", "Target lowest health player", true);
	private final Setting<Boolean> nearest = new Setting<>("Nearest", "Target nearest player", false);
	private final Setting<Double> range = new Setting<>("Range", "Targeting range", 20.0).setRange(5.0, 128.0);
	private final Setting<Boolean> switchOnDeath = new Setting<>("Switch on Death", "Switch target when current dies", true);

	private PlayerEntity currentTarget;
	private long lastSwitchTime = 0;

	public TargetAssist() {
		super("TargetAssist", "Intelligent target selection and switching", ModuleCategory.COMBAT);
		registerSetting(lowestHealth);
		registerSetting(nearest);
		registerSetting(range);
		registerSetting(switchOnDeath);
	}

	/**
	 * Get current target
	 */
	public PlayerEntity getTarget() {
		if (currentTarget != null && !currentTarget.isDead() && TargetUtil.isAlive(currentTarget)) {
			return currentTarget;
		}

		// Find new target
		if (lowestHealth.getValue()) {
			var target = TargetUtil.getLowestHealthPlayer(range.getValue());
			if (target.isPresent()) {
				currentTarget = target.get();
				lastSwitchTime = System.currentTimeMillis();
				return currentTarget;
			}
		} else if (nearest.getValue()) {
			var target = TargetUtil.getNearestPlayer(range.getValue());
			if (target.isPresent()) {
				currentTarget = target.get();
				lastSwitchTime = System.currentTimeMillis();
				return currentTarget;
			}
		}

		currentTarget = null;
		return null;
	}

	/**
	 * Set target manually
	 */
	public void setTarget(PlayerEntity player) {
		this.currentTarget = player;
	}

	/**
	 * Clear target
	 */
	public void clearTarget() {
		this.currentTarget = null;
	}

	public long getLastSwitchTime() {
		return lastSwitchTime;
	}
}
