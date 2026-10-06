package twilightforest.world.debug;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EntityDebugLevelSource extends ChunkGenerator {
	public static final MapCodec<EntityDebugLevelSource> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(RegistryOps.retrieveElement(Biomes.PLAINS)).apply(i, i.stable(EntityDebugLevelSource::new)));
	private static final int PLATFORM_Y = 60;
	private static final BlockState BARRIER = Blocks.BARRIER.defaultBlockState();

	public EntityDebugLevelSource(Holder.Reference<Biome> plains) {
		super(new FixedBiomeSource(plains));
	}

	@Override
	protected MapCodec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	@Override
	public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
		ChunkPos chunkPos = chunk.getPos();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int worldX = chunkPos.getMinBlockX() + x;
				int worldZ = chunkPos.getMinBlockZ() + z;

				level.setBlock(
					pos.set(worldX, PLATFORM_Y - 10, worldZ),
					BARRIER,
					2
				);
			}
		}
	}

	@Override
	public void applyCarvers(WorldGenRegion level, long seed, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk) {
	}

	@Override
	public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState randomState, ChunkAccess chunk) {
	}

	@Override
	public void spawnOriginalMobs(WorldGenRegion level) {
	}

	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
		return CompletableFuture.completedFuture(chunk);
	}

	@Override
	public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor heightAccessor, RandomState randomState) {
		return PLATFORM_Y + 1;
	}

	@Override
	public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor, RandomState randomState) {
		return new NoiseColumn(0, new BlockState[0]);
	}

	@Override
	public int getMinY() {
		return 0;
	}

	@Override
	public int getGenDepth() {
		return 384;
	}

	@Override
	public int getSeaLevel() {
		return 63;
	}

	@Override
	public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {
	}
}