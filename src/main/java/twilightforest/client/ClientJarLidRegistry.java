package twilightforest.client;

import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

import java.util.HashMap;
import java.util.Map;

public final class ClientJarLidRegistry {
	private static final Map<ResourceKey<Item>, StandaloneModelKey<BlockStateModelPart>> JAR_LID_KEYS = new HashMap<>();

	public static void register(ResourceKey<Item> item, StandaloneModelKey<BlockStateModelPart> key) {
		JAR_LID_KEYS.put(item, key);
	}

	public static StandaloneModelKey<BlockStateModelPart> get(ResourceKey<Item> item) {
		return JAR_LID_KEYS.get(item);
	}

	public static void clear() {
		JAR_LID_KEYS.clear();
	}
}