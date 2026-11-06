package twilightforest.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.monster.CarminiteGhastguard;

import java.util.function.BooleanSupplier;

/**
 * [VanillaCopy] of {@link net.minecraft.world.entity.monster.Ghast.GhastMoveControl} with adjustments noted
 */
public class GhastguardMoveControl extends MoveControl {
	private final CarminiteGhastguard ghast;
	private int floatDuration;
	// TF: Removed the careful field
	// TF: Removed the shouldBeStopped field
	private final BooleanSupplier shouldHover; // TF: Hover in place when engaged with a target

	public GhastguardMoveControl(CarminiteGhastguard ghast, BooleanSupplier shouldHover) {
		super(ghast);
		this.ghast = ghast;
		this.shouldHover = shouldHover;
	}

	// TF: Removed happy ghast logic and simplified method
	public void tick() {
		// TF: Dampen motion while engaged with a target
		if (this.shouldHover.getAsBoolean()) {
			this.ghast.setDeltaMovement(this.ghast.getDeltaMovement().scale(0.75D));
			return;
		}

		if (this.operation == Operation.MOVE_TO && this.floatDuration-- <= 0) {
			this.floatDuration = this.floatDuration + this.ghast.getRandom().nextInt(20) + 20; // TF: Shorter interval between movement
			Vec3 travel = new Vec3(this.wantedX - this.ghast.getX(), this.wantedY - this.ghast.getY(), this.wantedZ - this.ghast.getZ());
			if (this.canReach(travel)) {
				this.ghast.setDeltaMovement(this.ghast.getDeltaMovement().add(travel.normalize().scale(this.ghast.getAttributeValue(Attributes.FLYING_SPEED) * (double)5.0F / (double)3.0F)));
			} else {
				this.operation = Operation.WAIT;
			}
		}
	}

	// TF: Removed happy ghast logic and simplified method
	private boolean canReach(Vec3 travel) {
		AABB aabb = this.ghast.getBoundingBox();
		AABB aabbAtDestination = aabb.move(travel);
		Vec3 start = this.ghast.position();
		Vec3 end = start.add(travel);
		return BlockGetter.forEachBlockIntersectedBetween(start, end, aabbAtDestination, (blockPos, _) -> aabb.intersects(blockPos) || this.blockTraversalPossible(this.ghast.level(), start, end, blockPos));
	}

	// TF: Removed happy ghast logic and simplified method
	private boolean blockTraversalPossible(BlockGetter level, Vec3 start, Vec3 end, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.isAir())
			return true;

		return !this.ghast.collidedWithShapeMovingFrom(start, end, state.getCollisionShape(level, pos).move(new Vec3(pos)).toAabbs());
	}
}
