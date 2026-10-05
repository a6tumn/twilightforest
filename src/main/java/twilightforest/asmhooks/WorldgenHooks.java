package twilightforest.asmhooks;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.TFRegistries;
import twilightforest.world.components.chunkblanketing.ChunkBlanketProcessor;
import twilightforest.world.components.chunkgenerators.CustomTerrainBeardifier;
import twilightforest.world.components.structures.CustomDensitySource;
import twilightforest.world.components.structures.util.CustomStructureData;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings({"JavadocReference", "unused"})
public class WorldgenHooks {

	/**
	 * {@link twilightforest.asm.transformers.beardifier.InjectCustomTerrainBeardifierDuringCreateNoiseChunkTransformer}<p/>
	 *
	 * Injection point:<br/>
	 * {@link net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator#createNoiseChunk(ChunkAccess, StructureManager, Blender, RandomState)}
	 */
	public static DensityFunctions.BeardifierOrMarker gatherCustomTerrain(Beardifier vanilla, StructureManager structureManager, ChunkAccess chunkAccess) {
		ChunkPos chunkPos = chunkAccess.getPos();
		List<StructureStart> structureStarts = structureManager.startsForStructure(chunkPos, s -> s instanceof CustomDensitySource);

		if (structureStarts.isEmpty())
			return vanilla;

		ObjectArrayList<DensityFunction> customStructureTerraforms = new ObjectArrayList<>(structureStarts.size());

		for (StructureStart structureStart : structureStarts)
			if (structureStart.getStructure() instanceof CustomDensitySource customDensitySource)
				customStructureTerraforms.add(customDensitySource.getStructureTerraformer(chunkPos, structureStart));

		return customStructureTerraforms.isEmpty() ? vanilla : new CustomTerrainBeardifier(vanilla, customStructureTerraforms);
	}

	/**
	 * {@link twilightforest.asm.transformers.chunk.ChunkStatusTaskTransformer}<p/>
	 *
	 * Injection Point:<br/>
	 * {@link net.minecraft.world.level.chunk.status.ChunkStatusTasks#generateSurface(WorldGenContext, ChunkStep, StaticCache2D, ChunkAccess)}
	 */
	public static void chunkBlanketing(ChunkAccess access, WorldGenRegion region) {
		ChunkPos chunkPos = access.getPos();
		Set<Holder<Biome>> biomesInChunk = new ObjectArraySet<>();

		for (LevelChunkSection levelchunksection : region.getChunk(chunkPos.x(), chunkPos.z()).getSections()) {
			levelchunksection.getBiomes().getAll(biomesInChunk::add);
		}

		Iterator<ChunkBlanketProcessor> modifierIterator = region.registryAccess()
			.lookup(TFRegistries.Keys.CHUNK_BLANKET_PROCESSORS)
			.map(Registry::stream)
			.orElseGet(Stream::empty)
			.filter(modifier -> modifier.biomesForApplication().stream().anyMatch(biomesInChunk::contains))
			.iterator();

		Function<BlockPos, Holder<Biome>> biomeGetter = region::getBiome;
		while (modifierIterator.hasNext()) {
			modifierIterator.next().processChunk(region, region.getRandom().fork(), biomeGetter, access);
		}
	}

	/**
	 * {@link twilightforest.asm.transformers.conquered.StructureStartLoadStaticTransformer}<p/>
	 *
	 * Injection Point:<br/>
	 * {@link net.minecraft.world.level.levelgen.structure.StructureStart#loadStaticStart(StructurePieceSerializationContext, CompoundTag, long)}<br/>
	 * Targets: {@link net.minecraft.world.level.levelgen.structure.StructureStart#StructureStart(Structure, ChunkPos, int, PiecesContainer)}
	 */
	public static StructureStart loadStaticStart(StructureStart start, PiecesContainer piecesContainer, CompoundTag nbt) {
		if (start.getStructure() instanceof CustomStructureData s)
			return s.forDeserialization(start.getStructure(), start.getChunkPos(), start.getReferences(), piecesContainer, nbt);
		return start;
	}
}
