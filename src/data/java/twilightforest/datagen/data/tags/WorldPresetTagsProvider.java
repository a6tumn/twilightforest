package twilightforest.datagen.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.tags.WorldPresetTags;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFWorldPresets;

import java.util.concurrent.CompletableFuture;

public class WorldPresetTagsProvider extends KeyTagProvider<WorldPreset> {
	public WorldPresetTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.WORLD_PRESET, lookupProvider, TwilightForestMod.ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.tag(WorldPresetTags.EXTENDED).add(TFWorldPresets.ENTITY_DEBUG);
	}
}
