package twilightforest.entity.ai.goal;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import twilightforest.entity.monster.CarminiteGhastguard;

import java.util.EnumSet;

// [VanillaCopy] from Ghast but we use wanderFactor instead, we also stop moving when we have a target
public class GhastguardRandomFlyGoal extends Goal {
	private final CarminiteGhastguard parentEntity;

	@SuppressWarnings("this-escape")
	public GhastguardRandomFlyGoal(CarminiteGhastguard ghast) {
		this.parentEntity = ghast;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (this.parentEntity.isEngaged())
			return false;

		MoveControl control = this.parentEntity.getMoveControl();
		if (!control.hasWanted())
			return true;

		double d0 = control.getWantedX() - this.parentEntity.getX();
		double d1 = control.getWantedY() - this.parentEntity.getY();
		double d2 = control.getWantedZ() - this.parentEntity.getZ();
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		return d3 < 1.0D || d3 > 3600.0D;
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}

	@Override
	public void start() {
		RandomSource random = this.parentEntity.getRandom();
		double d0 = this.parentEntity.getX() + (random.nextFloat() * 2.0F - 1.0F) * parentEntity.getWanderFactor();
		double d1 = this.parentEntity.getY() + (random.nextFloat() * 2.0F - 1.0F) * parentEntity.getWanderFactor();
		double d2 = this.parentEntity.getZ() + (random.nextFloat() * 2.0F - 1.0F) * parentEntity.getWanderFactor();
		this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
	}
}