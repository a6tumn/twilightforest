package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import twilightforest.init.TFSounds;

import java.util.Objects;

public class CarminiteGhastling extends CarminiteGhastguard {

	private @Nullable LivingEntity engagedTarget;
	private boolean isMinion = false;

	@SuppressWarnings("this-escape")
	public CarminiteGhastling(EntityType<? extends CarminiteGhastling> type, Level world) {
		super(type, world);
		this.wanderFactor = 4.0F;

		if (this.isMinion() && this.getAttribute(Attributes.MAX_HEALTH) != null) {
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(6);
			this.setHealth(this.getMaxHealth());
		}
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 16;
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return CarminiteGhastguard.registerAttributes()
			.add(Attributes.MAX_HEALTH, 10)
			.add(Attributes.FOLLOW_RANGE, 16.0D)
			.add(Attributes.FLYING_SPEED, 0.01D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.CARMINITE_GHASTLING_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.CARMINITE_GHASTLING_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.CARMINITE_GHASTLING_DEATH.get();
	}

	@Override
	public SoundEvent getFireSound() {
		return TFSounds.CARMINITE_GHASTLING_SHOOT.get();
	}

	@Override
	public SoundEvent getWarnSound() {
		return TFSounds.CARMINITE_GHASTLING_WARN.get();
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		if (this.engagedTarget == null)
			return;

		// Lose interest if not engaged with target
		if (!this.isEngagedWith(this.engagedTarget)) {
			this.engagedTarget = null;
		}
	}

	@Override
	public boolean shouldAttack(LivingEntity living) {
		ItemStack helmet = living.getItemBySlot(EquipmentSlot.HEAD);

		// Lose interest if target is disguised
		if (!helmet.isEmpty() && helmet.is(ItemTags.GAZE_DISGUISE_EQUIPMENT)) {
			this.engagedTarget = null;
			return false;
		}

		// Lose interest if not engaged with target
		if (!this.isEngagedWith(living)) {
			if (this.isEngagedTarget(living))
				this.engagedTarget = null;
			return false;
		}

		return this.isEngagedTarget(living) || this.isLookedAtBy(living);
	}

	public void engage(LivingEntity living) {
		this.engagedTarget = living;
	}

	public boolean isEngagedTarget(LivingEntity living) {
		return living == this.engagedTarget;
	}

	private boolean isLookedAtBy(LivingEntity living) {
		if (living.distanceTo(this) <= 3.5F)
			return true;

		Vec3 view = living.getViewVector(1.0F).normalize();
		Vec3 toGhast = new Vec3(
			this.getX() - living.getX(),
			this.getBoundingBox().minY + this.getEyeHeight() - (living.getY() + living.getEyeHeight()),
			this.getZ() - living.getZ()
		);
		double dist = toGhast.length();

		return view.dot(toGhast.normalize()) > 1.0D - 0.025D / dist;
	}

	//This does not factor into whether the entity is a Minion or not. However, since it is spawned via MOB_SUMMONED, it will always spawn if that is the SpawnReason
	public static boolean canSpawnHere(EntityType<CarminiteGhastling> entity, ServerLevelAccessor world, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && (reason == EntitySpawnReason.MOB_SUMMONED || Monster.isDarkEnoughToSpawn(world, pos, random)) && checkMobSpawnRules(entity, world, reason, pos, random);
	}

	public void makeBossMinion() {
		this.wanderFactor = 0.005F;
		this.isMinion = true;

		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(6);
		this.setHealth(this.getMaxHealth());
	}

	public boolean isMinion() {
		return this.isMinion;
	}

	@Override
	public void addAdditionalSaveData(ValueOutput compound) {
		compound.putBoolean("isMinion", this.isMinion);
		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (compound.getBooleanOr("isMinion", false)) {
			this.makeBossMinion();
		}
	}

	//prevents ghastlings from triggering the traps on accident
	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}
}
