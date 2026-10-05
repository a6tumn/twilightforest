package twilightforest.init.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.chunkblanketing.CanopyBlanketProcessor;
import twilightforest.world.components.chunkblanketing.ChunkBlanketProcessor;
import twilightforest.world.components.chunkblanketing.ChunkBlanketType;
import twilightforest.world.components.chunkblanketing.GlacierBlanketProcessor;

public final class ChunkBlanketProcessors {
	public static final DeferredRegister<ChunkBlanketType> CHUNK_BLANKETING_TYPES = DeferredRegister.create(TFRegistries.Keys.CHUNK_BLANKET_TYPE, TwilightForestMod.ID);
	public static final Codec<ChunkBlanketType> TYPE_CODEC = Codec.lazyInitialized(TFRegistries.CHUNK_BLANKET_TYPES::byNameCodec);
	public static final Codec<ChunkBlanketProcessor> DISPATCH_CODEC = TYPE_CODEC.dispatch("type", ChunkBlanketProcessor::getType, ChunkBlanketType::getCodec);

	public static final DeferredHolder<ChunkBlanketType, ChunkBlanketType> CANOPY = registerType("canopy", CanopyBlanketProcessor.CODEC);
	public static final DeferredHolder<ChunkBlanketType, ChunkBlanketType> GLACIER = registerType("glacier", GlacierBlanketProcessor.CODEC);

	public static final ResourceKey<ChunkBlanketProcessor> DARK_FOREST_CANOPY = ResourceKey.create(TFRegistries.Keys.CHUNK_BLANKET_PROCESSORS, TwilightForestMod.prefix("dark_forest_canopy"));
	public static final ResourceKey<ChunkBlanketProcessor> SNOWY_FOREST_GLACIER = ResourceKey.create(TFRegistries.Keys.CHUNK_BLANKET_PROCESSORS, TwilightForestMod.prefix("snowy_forest_glacier"));

	public static DeferredHolder<ChunkBlanketType, ChunkBlanketType> registerType(String name, MapCodec<? extends ChunkBlanketProcessor> codec) {
		return CHUNK_BLANKETING_TYPES.register(name, () -> () -> codec);
	}
}
