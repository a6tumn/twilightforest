package twilightforest.entity.ai.goal;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import twilightforest.entity.monster.CarminiteGhastguard;

import java.util.EnumSet;

// [VanillaCopy] from Ghast but we use wanderFactor instead, we also stop moving when we have a target
public class GhastguardRandomFlyGoal extends Goal {
	private final CarminiteGhastguard ghast;

	@SuppressWarnings("this-escape")
	public GhastguardRandomFlyGoal(CarminiteGhastguard ghast) {
		this.ghast = ghast;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		MoveControl moveControl = this.ghast.getMoveControl();
		if (!moveControl.hasWanted()) {
			return !this.ghast.isEngaged();
		} else {
			double xd = moveControl.getWantedX() - this.ghast.getX();
			double yd = moveControl.getWantedY() - this.ghast.getY();
			double zd = moveControl.getWantedZ() - this.ghast.getZ();
			double dd = xd * xd + yd * yd + zd * zd;
			return !this.ghast.isEngaged() && (dd < 1.0D || dd > 3600.0D);
		}
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}

	@Override
	public void start() {
		RandomSource random = this.ghast.getRandom();
		double d0 = this.ghast.getX() + (random.nextFloat() * 2.0F - 1.0F) * ghast.getWanderFactor();
		double d1 = this.ghast.getY() + (random.nextFloat() * 2.0F - 1.0F) * ghast.getWanderFactor();
		double d2 = this.ghast.getZ() + (random.nextFloat() * 2.0F - 1.0F) * ghast.getWanderFactor();
		this.ghast.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
	}
}