package twilightforest.entity.passive;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import twilightforest.init.TFSounds;
import twilightforest.inventory.InventoryUtil;
import twilightforest.loot.TFLootTables;
import twilightforest.tags.TFItemTags;

public class Raven extends FlyingBird {
	private static final int INTERACTION_FAIL_COOLDOWN = 100;
	private boolean hasDroppedItem;
	private int interactionFailCooldownTimer;

	public Raven(EntityType<? extends Raven> type, Level world) {
		super(type, world);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return FlyingBird.createAnimalAttributes()
			.add(Attributes.MAX_HEALTH, 10.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.2D)
			.add(Attributes.STEP_HEIGHT, 1.0D);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide() && this.interactionFailCooldownTimer > 0) {
			this.interactionFailCooldownTimer--;
		}
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
		if (!this.hasDroppedItem && player.getItemInHand(hand).isEmpty() && this.level() instanceof ServerLevel serverLevel) {
			this.hurtServer(serverLevel, this.damageSources().playerAttack(player), 0F);
			this.dropFromEntityInteractLootTable(serverLevel, TFLootTables.RAVEN_INTERACTION, player, player.getItemInHand(hand), (_, stack) -> InventoryUtil.giveItemToPlayerAndPlaySound(player, stack));
			this.hasDroppedItem = true;
			return InteractionResult.SUCCESS;
		} else if (this.interactionFailCooldownTimer == 0) {
			this.level().playSound(null, BlockPos.containing(this.position()), this.getHurtSound(this.damageSources().playerAttack(player)), SoundSource.NEUTRAL);
			this.interactionFailCooldownTimer = INTERACTION_FAIL_COOLDOWN;
		}

		return InteractionResult.FAIL;
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("HasDroppedItem", hasDroppedItem);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		hasDroppedItem = input.getBooleanOr("HasDroppedItem", false);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.RAVEN_CAW.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.RAVEN_SQUAWK.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.RAVEN_SQUAWK.get();
	}

	@Override
	public boolean isSpooked() {
		return this.getLastHurtByMob() != null;
	}

	@Override
	public TagKey<Item> getTemptItems() {
		return TFItemTags.RAVEN_TEMPT_ITEMS;
	}
}
