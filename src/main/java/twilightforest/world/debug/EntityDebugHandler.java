package twilightforest.world.debug;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.jspecify.annotations.Nullable;
import twilightforest.TwilightForestMod;

import java.util.List;

public final class EntityDebugHandler {
	private static final int ENTITY_Y = 70;
	private static final int CHUNK_SPACING = 2;

	private static int tickCounter;

	private static final String ENTITY_TAG = TwilightForestMod.ID + "_entity_debug";
	private static final String BABY_TAG = TwilightForestMod.ID + "_entity_debug_baby";
	private static final String LABEL_TAG = TwilightForestMod.ID + "_entity_debug_label";
	private static final String LABEL_ENTITY_PREFIX = TwilightForestMod.ID + "_entity_debug_for:";

	public static void setup(ServerTickEvent.Post event) {
		if (++tickCounter < 20) {
			return;
		}

		tickCounter = 0;

		for (ServerLevel level : event.getServer().getAllLevels()) {
			if (level.getChunkSource().getGenerator() instanceof EntityDebugLevelSource) {
				initialize(level);
			}
		}
	}

	public static void initialize(ServerLevel level) {
		List<List<DebugEntity<?>>> rows = DebugEntities.createDebugEntities(level.registryAccess());
		for (int row = 0; row < rows.size(); row++) {
			List<DebugEntity<?>> entities = rows.get(row);
			for (int column = 0; column < entities.size(); column++) {
				DebugEntity<?> debugEntity = entities.get(column);
				ChunkPos chunkPos = new ChunkPos(column * CHUNK_SPACING, row * CHUNK_SPACING);
				level.setChunkForced(chunkPos.x(), chunkPos.z(), true);
				level.getChunk(chunkPos.x(), chunkPos.z());
				ensureEntity(level, chunkPos, debugEntity);
			}
		}
	}

	private static void ensureEntity(ServerLevel level, ChunkPos chunkPos, DebugEntity<?> debugEntity) {
		Entity entity = findEntity(level, chunkPos, debugEntity.type(), false);
		if (entity == null) {
			entity = spawnEntity(level, chunkPos, debugEntity, false);
		} else {
			ensureEntityLabel(level, entity);
		}

		if (entity instanceof AgeableMob) {
			Entity baby = findEntity(level, chunkPos, debugEntity.type(), true);
			if (baby == null) {
				spawnEntity(level, chunkPos, debugEntity, true);
			} else {
				ensureEntityLabel(level, baby);
			}
		}
	}

	private static @Nullable Entity findEntity(ServerLevel level, ChunkPos chunkPos, EntityType<?> type, boolean baby) {
		for (Entity entity : level.getEntities().getAll()) {
			if (!isDebugEntity(entity)) {
				continue;
			}

			if (entity instanceof Display) {
				continue;
			}

			if (entity.chunkPosition().equals(chunkPos) && entity.getType() == type && isBaby(entity) == baby) {
				return entity;
			}
		}

		return null;
	}

	private static @Nullable Entity spawnEntity(ServerLevel level, ChunkPos chunkPos, DebugEntity<?> debugEntity, boolean baby) {
		BlockPos spawnPos = new BlockPos(chunkPos.getMiddleBlockX() + (baby ? 2 : 0), ENTITY_Y, chunkPos.getMiddleBlockZ());
		Entity entity = createEntity(level, ChunkPos.containing(spawnPos), debugEntity, baby);

		if (entity == null) {
			TwilightForestMod.LOGGER.warn("Failed to create debug entity {}", BuiltInRegistries.ENTITY_TYPE.getKey(debugEntity.type()));
			return null;
		}

		level.addFreshEntity(entity);
		addEntityLabel(level, entity);

		return entity;
	}

	private static <T extends Entity> @Nullable Entity createEntity(ServerLevel level, ChunkPos chunkPos, DebugEntity<T> debugEntity, boolean baby) {
		BlockPos spawnPos = new BlockPos(chunkPos.getMiddleBlockX() + (baby ? 2 : 0), ENTITY_Y, chunkPos.getMiddleBlockZ());
		return debugEntity.type().create(
			level,
			configured -> {
				configured.addTag(ENTITY_TAG);

				if (baby) {
					configured.addTag(BABY_TAG);
				}

				configured.setNoGravity(true);
				if (configured instanceof Mob mob) {
					mob.setNoAi(true);
				}

				if (baby && configured instanceof AgeableMob ageable) {
					ageable.setBaby(true);
				}

				debugEntity.configuration().accept(configured);
			},
			spawnPos,
			EntitySpawnReason.COMMAND,
			false,
			false
		);
	}

	private static boolean isBaby(Entity entity) {
		return entity.entityTags().contains(BABY_TAG);
	}

	private static void ensureEntityLabel(ServerLevel level, Entity entity) {
		String entityReference = LABEL_ENTITY_PREFIX + entity.getUUID();
		for (Entity candidate : level.getEntities().getAll()) {
			if (!(candidate instanceof Display.TextDisplay label)) {
				continue;
			}

			if (label.entityTags().contains(entityReference)) {
				positionLabel(label, entity);
				return;
			}
		}

		addEntityLabel(level, entity);
	}

	private static void addEntityLabel(ServerLevel level, Entity entity) {
		Display.TextDisplay label = EntityType.TEXT_DISPLAY.create(level, EntitySpawnReason.COMMAND);
		if (label == null) {
			return;
		}

		String entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
		String summonCommand = "/summon " + entityId;

		Component text = Component.literal(entityId)
			.withStyle(style -> style
				.withColor(ChatFormatting.WHITE)
				.withBold(true)
				.withClickEvent(new ClickEvent.CopyToClipboard(summonCommand))
				.withHoverEvent(new HoverEvent.ShowText(Component.literal("Click to copy " + summonCommand)))
			);

		label.setText(text);
		label.setBillboardConstraints(Display.BillboardConstraints.CENTER);
		label.setNoGravity(true);
		label.setInvulnerable(true);
		label.addTag(LABEL_TAG);
		label.addTag(LABEL_ENTITY_PREFIX + entity.getUUID());

		positionLabel(label, entity);
		level.addFreshEntity(label);
	}

	private static void positionLabel(Display.TextDisplay label, Entity entity) {
		double height = entity.getBoundingBox().getYsize();
		label.snapTo(entity.getX(), entity.getY() + height + 0.5D, entity.getZ(), 0.0F, 0.0F);
	}

	private static boolean isDebugEntity(Entity entity) {
		return entity.entityTags().contains(ENTITY_TAG);
	}

	private EntityDebugHandler() {
	}
}
