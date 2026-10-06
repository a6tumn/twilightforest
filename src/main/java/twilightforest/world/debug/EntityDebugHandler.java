package twilightforest.world.debug;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import twilightforest.TwilightForestMod;

import java.util.List;

public final class EntityDebugHandler {
	private static final int ENTITY_Y = 70;
	private static int tickCounter;

	private static final String ENTITY_TAG = TwilightForestMod.ID + "_entity_debug";
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
		Registry<EntityType<?>> registry = level.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);
		List<EntityType<?>> entityTypes = registry.stream()
			.filter(EntityDebugHandler::isTwilightForestEntity)
			.toList();

		if (entityTypes.isEmpty()) {
			return;
		}

		int entityCount = entityTypes.size();
		int gridWidth = Mth.ceil(Mth.sqrt(entityCount));
		int gridHeight = Mth.ceil((float) entityCount / gridWidth);

		for (int z = 0; z < gridHeight; z++) {
			for (int x = 0; x < gridWidth; x++) {
				int index = z * gridWidth + x;

				if (index >= entityCount) {
					continue;
				}

				ChunkPos chunkPos = new ChunkPos(x, z);
				level.setChunkForced(chunkPos.x(), chunkPos.z(), true);
				level.getChunk(chunkPos.x(), chunkPos.z());
				ensureEntity(level, chunkPos, entityTypes.get(index));
			}
		}
	}

	private static void ensureEntity(ServerLevel level, ChunkPos chunkPos, EntityType<?> type) {
		for (Entity entity : level.getEntities().getAll()) {
			if (!isDebugEntity(entity)) {
				continue;
			}

			if (entity instanceof Display) {
				continue;
			}

			if (entity.chunkPosition().equals(chunkPos) && entity.getType() == type) {
				ensureEntityLabel(level, entity);
				return;
			}
		}
		spawnEntity(level, chunkPos, type);
	}

	private static void spawnEntity(ServerLevel level, ChunkPos chunkPos, EntityType<?> type) {
		BlockPos spawnPos = new BlockPos(chunkPos.getMiddleBlockX(), ENTITY_Y, chunkPos.getMiddleBlockZ());
		Entity entity = type.create(
			level,
			configured -> {
				configured.addTag(ENTITY_TAG);
				configured.setNoGravity(true);
				if (configured instanceof Mob mob) {
					mob.setNoAi(true);
				}
			},
			spawnPos,
			EntitySpawnReason.COMMAND,
			false,
			false
		);

		if (entity == null) {
			TwilightForestMod.LOGGER.warn("Failed to create debug entity {}", BuiltInRegistries.ENTITY_TYPE.getKey(type));
			return;
		}

		level.addFreshEntity(entity);
		addEntityLabel(level, entity);
	}

	private static void ensureEntityLabel(ServerLevel level, Entity entity) {
		String entityReference = LABEL_ENTITY_PREFIX + entity.getUUID();
		for (Entity candidate : level.getEntities().getAll()) {
			if (!(candidate instanceof Display.TextDisplay label)) {
				continue;
			}

			if (label.entityTags().contains(entityReference)) {
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

	private static boolean isTwilightForestEntity(EntityType<?> type) {
		var key = type.builtInRegistryHolder().getKey();
		return key != null && TwilightForestMod.ID.equals(key.identifier().getNamespace());
	}
}
