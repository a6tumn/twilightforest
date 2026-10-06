package twilightforest.world.debug;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Consumer;

public record DebugEntity<T extends Entity>(
	EntityType<T> type,
	Consumer<T> configuration
) {
}
