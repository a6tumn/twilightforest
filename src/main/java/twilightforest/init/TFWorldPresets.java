package twilightforest.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import twilightforest.TwilightForestMod;

public final class TFWorldPresets {
	public static final ResourceKey<WorldPreset> ENTITY_DEBUG = makeKey("entity_debug");

	private static ResourceKey<WorldPreset> makeKey(String name) {
		return ResourceKey.create(Registries.WORLD_PRESET, TwilightForestMod.prefix(name));
	}
}
