package com.prestige.client.event.events;

import com.prestige.client.event.Event;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

/**
 * Fired when a block interaction occurs
 */
public class InteractBlockEvent extends Event {
	private final BlockPos blockPos;
	private final Direction direction;

	public InteractBlockEvent(BlockPos blockPos, Direction direction) {
		this.blockPos = blockPos;
		this.direction = direction;
	}

	public BlockPos getBlockPos() {
		return blockPos;
	}

	public Direction getDirection() {
		return direction;
	}
}
