package twilightforest.datagen.data.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import twilightforest.init.TFWorldPresets;
import twilightforest.world.debug.EntityDebugLevelSource;

import java.util.Map;

public class TFWorldPresetGenerator {
	public static void bootstrap(BootstrapContext<WorldPreset> context) {
		HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(
			TFWorldPresets.ENTITY_DEBUG,
			new WorldPreset(
				Map.of(
					LevelStem.OVERWORLD,
					new LevelStem(dimensionTypes.getOrThrow(BuiltinDimensionTypes.OVERWORLD), new EntityDebugLevelSource(biomes.getOrThrow(Biomes.PLAINS)))
				)
			)
		);
	}
}
