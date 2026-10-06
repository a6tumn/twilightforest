package twilightforest.world.debug;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import twilightforest.TFRegistries;
import twilightforest.entity.passive.*;
import twilightforest.init.TFEntities;
import twilightforest.init.custom.DwarfRabbitVariants;
import twilightforest.init.custom.TinyBirdVariants;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class DebugEntities {
	public static List<List<DebugEntity<?>>> createDebugEntities(RegistryAccess access) {
		return List.of(
			projectiles(),
			boats(),
			bighorns(),
			dwarfRabbits(access),
			tinyBirds(access),
			miscPassiveMobs(),
			spiderMonsters(),
			beetleMonsters(),
			wolfMonsters(),
			ghastMonsters(),
			giantMonsters(),
			miscMonsters(),
			bosses()
		);
	}

	public static List<DebugEntity<?>> projectiles() {
		return List.of(
			debugEntity(TFEntities.CHAIN_BLOCK.get()),
			debugEntity(TFEntities.FALLING_ICE.get()),
			debugEntity(TFEntities.HYDRA_MORTAR.get()),
			debugEntity(TFEntities.ICE_ARROW.get()),
			debugEntity(TFEntities.THROWN_ICE.get()),
			debugEntity(TFEntities.ICE_SNOWBALL.get()),
			debugEntity(TFEntities.LICH_BOLT.get()),
			debugEntity(TFEntities.LICH_BOMB.get()),
			debugEntity(TFEntities.MOONWORM_SHOT.get()),
			debugEntity(TFEntities.NATURE_BOLT.get()),
			debugEntity(TFEntities.SEEKER_ARROW.get()),
			debugEntity(TFEntities.SLIME_BLOB.get()),
			debugEntity(TFEntities.THROWN_BLOCK.get()),
			debugEntity(TFEntities.THROWN_WEP.get()),
			debugEntity(TFEntities.TOME_BOLT.get()),
			debugEntity(TFEntities.WAND_BOLT.get())
		);
	}

	public static List<DebugEntity<?>> boats() {
		return List.of(
			debugEntity(TFEntities.TWILIGHT_OAK_BOAT.get()),
			debugEntity(TFEntities.TWILIGHT_OAK_CHEST_BOAT.get()),
			debugEntity(TFEntities.CANOPY_BOAT.get()),
			debugEntity(TFEntities.CANOPY_CHEST_BOAT.get()),
			debugEntity(TFEntities.MANGROVE_BOAT.get()),
			debugEntity(TFEntities.MANGROVE_CHEST_BOAT.get()),
			debugEntity(TFEntities.DARK_BOAT.get()),
			debugEntity(TFEntities.DARK_CHEST_BOAT.get()),
			debugEntity(TFEntities.TIME_BOAT.get()),
			debugEntity(TFEntities.TIME_CHEST_BOAT.get()),
			debugEntity(TFEntities.TRANSFORMATION_BOAT.get()),
			debugEntity(TFEntities.TRANSFORMATION_CHEST_BOAT.get()),
			debugEntity(TFEntities.MINING_BOAT.get()),
			debugEntity(TFEntities.MINING_CHEST_BOAT.get()),
			debugEntity(TFEntities.SORTING_BOAT.get()),
			debugEntity(TFEntities.SORTING_CHEST_BOAT.get())
		);
	}

	public static List<DebugEntity<?>> bighorns() {
		return Arrays.stream(DyeColor.values())
			.map(color -> (DebugEntity<?>) debugEntity(TFEntities.BIGHORN_SHEEP.get(), bighorn -> bighorn.setColor(color)))
			.collect(Collectors.toList());
	}

	public static List<DebugEntity<?>> dwarfRabbits(RegistryAccess access) {
		HolderLookup.RegistryLookup<DwarfRabbitVariant> variants = access.lookupOrThrow(TFRegistries.Keys.DWARF_RABBIT_VARIANT);
		return List.of(
			debugEntity(TFEntities.DWARF_RABBIT.get(), rabbit -> rabbit.setVariant(variants.getOrThrow(DwarfRabbitVariants.BROWN))),
			debugEntity(TFEntities.DWARF_RABBIT.get(), rabbit -> rabbit.setVariant(variants.getOrThrow(DwarfRabbitVariants.DUTCH))),
			debugEntity(TFEntities.DWARF_RABBIT.get(), rabbit -> rabbit.setVariant(variants.getOrThrow(DwarfRabbitVariants.WHITE)))
		);
	}

	public static List<DebugEntity<?>> tinyBirds(RegistryAccess access) {
		HolderLookup.RegistryLookup<TinyBirdVariant> variants = access.lookupOrThrow(TFRegistries.Keys.TINY_BIRD_VARIANT);
		return List.of(
			debugEntity(TFEntities.TINY_BIRD.get(), bird -> bird.setVariant(variants.getOrThrow(TinyBirdVariants.BLUE))),
			debugEntity(TFEntities.TINY_BIRD.get(), bird -> bird.setVariant(variants.getOrThrow(TinyBirdVariants.BROWN))),
			debugEntity(TFEntities.TINY_BIRD.get(), bird -> bird.setVariant(variants.getOrThrow(TinyBirdVariants.GOLD))),
			debugEntity(TFEntities.TINY_BIRD.get(), bird -> bird.setVariant(variants.getOrThrow(TinyBirdVariants.RED)))
		);
	}

	public static List<DebugEntity<?>> miscPassiveMobs() {
		return List.of(
			debugEntity(TFEntities.BOAR.get()),
			debugEntity(TFEntities.DEER.get()),
			debugEntity(TFEntities.PENGUIN.get()),
			debugEntity(TFEntities.RAVEN.get()),
			debugEntity(TFEntities.SQUIRREL.get())
		);
	}

	public static List<DebugEntity<?>> spiderMonsters() {
		return List.of(
			debugEntity(TFEntities.SWARM_SPIDER.get()),
			debugEntity(TFEntities.HEDGE_SPIDER.get()),
			debugEntity(TFEntities.CARMINITE_BROODLING.get()),
			debugEntity(TFEntities.KING_SPIDER.get())
		);
	}

	public static List<DebugEntity<?>> beetleMonsters() {
		return List.of(
			debugEntity(TFEntities.FIRE_BEETLE.get()),
			debugEntity(TFEntities.FIRE_BEETLE.get()),
			debugEntity(TFEntities.SLIME_BEETLE.get())
		);
	}

	public static List<DebugEntity<?>> wolfMonsters() {
		return List.of(
			debugEntity(TFEntities.HOSTILE_WOLF.get()),
			debugEntity(TFEntities.MIST_WOLF.get()),
			debugEntity(TFEntities.WINTER_WOLF.get())
		);
	}

	public static List<DebugEntity<?>> ghastMonsters() {
		return List.of(
			debugEntity(TFEntities.CARMINITE_GHASTLING.get()),
			debugEntity(TFEntities.CARMINITE_GHASTGUARD.get())
		);
	}

	public static List<DebugEntity<?>> giantMonsters() {
		return List.of(
			debugEntity(TFEntities.ARMORED_GIANT.get()),
			debugEntity(TFEntities.GIANT_MINER.get())
		);
	}

	public static List<DebugEntity<?>> miscMonsters() {
		return List.of(
			debugEntity(TFEntities.KOBOLD.get()),
			debugEntity(TFEntities.REDCAP.get()),
			debugEntity(TFEntities.REDCAP_SAPPER.get()),
			debugEntity(TFEntities.SKELETON_DRUID.get()),
			debugEntity(TFEntities.WRAITH.get()),
			debugEntity(TFEntities.DEATH_TOME.get()),
			debugEntity(TFEntities.RISING_ZOMBIE.get()),
			debugEntity(TFEntities.LICH_MINION.get()),
			debugEntity(TFEntities.LOYAL_ZOMBIE.get()),
			debugEntity(TFEntities.MOSQUITO_SWARM.get()),
			debugEntity(TFEntities.MAZE_SLIME.get()),
			debugEntity(TFEntities.MINOTAUR.get()),
			debugEntity(TFEntities.HELMET_CRAB.get()),
			debugEntity(TFEntities.LOWER_GOBLIN_KNIGHT.get()),
			debugEntity(TFEntities.UPPER_GOBLIN_KNIGHT.get()),
			debugEntity(TFEntities.BLOCKCHAIN_GOBLIN.get()),
			debugEntity(TFEntities.TOWERWOOD_BORER.get()),
			debugEntity(TFEntities.CARMINITE_GOLEM.get()),
			debugEntity(TFEntities.YETI.get()),
			debugEntity(TFEntities.SNOW_GUARDIAN.get()),
			debugEntity(TFEntities.ICE_CRYSTAL.get()),
			debugEntity(TFEntities.STABLE_ICE_CORE.get()),
			debugEntity(TFEntities.UNSTABLE_ICE_CORE.get()),
			debugEntity(TFEntities.TROLL.get()),
			debugEntity(TFEntities.ADHERENT.get()),
			debugEntity(TFEntities.HARBINGER_CUBE.get()),
			debugEntity(TFEntities.ROVING_CUBE.get())
		);
	}

	public static List<DebugEntity<?>> bosses() {
		return List.of(
			debugEntity(TFEntities.NAGA.get()),
			debugEntity(TFEntities.LICH.get()),
			debugEntity(TFEntities.MINOSHROOM.get()),
			debugEntity(TFEntities.HYDRA.get()),
			debugEntity(TFEntities.KNIGHT_PHANTOM.get()),
			debugEntity(TFEntities.UR_GHAST.get()),
			debugEntity(TFEntities.ALPHA_YETI.get()),
			debugEntity(TFEntities.SNOW_QUEEN.get()),
			debugEntity(TFEntities.QUEST_RAM.get())
		);
	}

	private static <T extends Entity> DebugEntity<T> debugEntity(EntityType<T> type) {
		return new DebugEntity<>(type, _ -> {});
	}

	private static <T extends Entity> DebugEntity<T> debugEntity(EntityType<T> type, Consumer<T> configuration) {
		return new DebugEntity<>(type, configuration);
	}

	private DebugEntities() {
	}
}
