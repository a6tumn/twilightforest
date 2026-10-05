package twilightforest.asmhooks;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import twilightforest.asm.transformers.player.ReduceMovementFoodExhaustionTransformer;
import twilightforest.init.TFDataComponents;
import twilightforest.init.custom.TravellersModifiers;
import twilightforest.util.TravellersModifierUtil;

@SuppressWarnings({"JavadocReference", "unused"})
public class PlayerHooks {

	/**
	 * {@link ReduceMovementFoodExhaustionTransformer ()}<p/>
	 *
	 * Injection Points:<br/>
	 * {@link net.minecraft.server.level.ServerPlayer#checkMovementStatistics(double dx, double dy, double dz)}
	 * {@link net.minecraft.server.level.ServerPlayer#jumpFromGround()}
	 */

	public static float getFoodExhaustion(float f, Player player) {
		ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
		Float divisor = chestStack.get(TFDataComponents.EFFICIENT_EATER);
		if (!TravellersModifierUtil.isModifierActive(player, chestStack, TravellersModifiers.EFFICIENT_EATER_MODIFIER) || divisor == null)
			return f;
		return f * (1 / divisor);
	}
}
