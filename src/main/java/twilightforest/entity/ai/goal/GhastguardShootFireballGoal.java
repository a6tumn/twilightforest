package twilightforest.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.monster.CarminiteGhastguard;
import twilightforest.entity.monster.CarminiteGhastling;

/**
 * [VanillaCopy] of {@link net.minecraft.world.entity.monster.Ghast.GhastShootFireballGoal} with adjustments noted
 */
public class GhastguardShootFireballGoal extends Goal {
	private static final int STARE_GRACE_TICKS = 20; // TF: 1-second grace period before becoming angry
	private final CarminiteGhastguard ghast;
	private int stareStartTick = -1; // TF: Timer for gaze anger
	public int chargeTime;
	public int prevChargeTime; // TF: Used for rendering

	public GhastguardShootFireballGoal(CarminiteGhastguard ghast) {
		this.ghast = ghast;
	}

	// TF: Added staring checks for Carminite Ghastling
	@Override
	public boolean canUse() {
		LivingEntity target = this.ghast.getTarget();
		if (target == null || !this.ghast.shouldAttack(target)) {
			this.stareStartTick = -1;
			return false;
		}

		if (!(this.ghast instanceof CarminiteGhastling carminiteGhastling))
			return true;

		if (carminiteGhastling.isEngagedTarget(target))
			return true;

		if (this.stareStartTick < 0)
			this.stareStartTick = carminiteGhastling.tickCount;

		if (carminiteGhastling.tickCount - this.stareStartTick < STARE_GRACE_TICKS)
			return false;

		carminiteGhastling.engage(target);
		this.stareStartTick = -1;
		return true;
	}

	@Override
	public void start() {
		this.chargeTime = this.prevChargeTime = 0; // TF: Added prevChargeTime check
	}

	@Override
	public void stop() {
		this.ghast.setCharging(false);
	}

	// TF: Removed requiresUpdateEveryTick override

	// TF: Commit to the attack if the timer has passed the warning stage
	@Override
	public boolean canContinueToUse() {
		LivingEntity target = this.ghast.getTarget();
		if (target == null)
			return false;

		return this.chargeTime >= 10 || this.ghast.shouldAttack(target);
	}

	@Override
	public void tick() {
		LivingEntity target = this.ghast.getTarget();
		if (target != null) {
			if (target.distanceToSqr(this.ghast) < 4096.0D && this.ghast.hasLineOfSight(target)) {
				Level level = this.ghast.level();
				this.prevChargeTime = this.chargeTime; // TF: Set prevChargeTime
				++this.chargeTime;
				this.ghast.getLookControl().setLookAt(target, 10.0F, this.ghast.getMaxHeadXRot()); // TF: Always face target
				if (this.chargeTime == 10 && !this.ghast.isSilent()) {
					this.ghast.playSound(this.ghast.getWarnSound(), 10.0F, this.ghast.getVoicePitch()); // TF: Replace level event with custom sound
				}

				if (this.chargeTime == 20) {
					Vec3 viewVector = this.ghast.getViewVector(1.0F);
					// TF: Handle scale for Carminite Ghastling
					double sizeScale = this.ghast.getBbWidth() / 4.0D;
					double forward = 4.0D * sizeScale;
					double up = 0.5D * sizeScale;
					double xdd = target.getX() - (this.ghast.getX() + viewVector.x * forward);
					double ydd = target.getY(0.5F) - (up + this.ghast.getY(0.5F));
					double zdd = target.getZ() - (this.ghast.getZ() + viewVector.z * forward);
					Vec3 direction = new Vec3(xdd, ydd, zdd);
					if (!this.ghast.isSilent()) {
						this.ghast.playSound(this.ghast.getFireSound(), 10.0F, this.ghast.getVoicePitch()); // TF: Replace level event with custom sound
					}

					LargeFireball entity = new LargeFireball(level, this.ghast, direction.normalize(), this.ghast.getExplosionPower());
					entity.setPos(this.ghast.getX() + viewVector.x * forward, this.ghast.getY(0.5F) + up, entity.getZ() + viewVector.z * forward);
					level.addFreshEntity(entity);
					if (!(this.ghast instanceof CarminiteGhastling) && this.ghast.getRandom().nextInt(6) == 0) {  // TF: Ghast guards have a 1-in-6 chance to stop targeting after an attack
						this.ghast.setTarget(null);
					}
					this.prevChargeTime = this.chargeTime; // TF: Set prevChargeTime
					this.chargeTime = -40;
				}
			} else if (this.chargeTime > 0) {
				this.prevChargeTime = this.chargeTime; // TF: Set prevChargeTime
				--this.chargeTime;
			}

			this.ghast.setCharging(this.chargeTime > 10);
		}
	}
}
