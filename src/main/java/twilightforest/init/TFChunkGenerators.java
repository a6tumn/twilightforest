package twilightforest.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.TwilightForestMod;
import twilightforest.world.debug.EntityDebugLevelSource;

public final class TFChunkGenerators {
	public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registries.CHUNK_GENERATOR, TwilightForestMod.ID);

	static {
		CHUNK_GENERATORS.register("entity_debug", () -> EntityDebugLevelSource.CODEC);
	}
}
