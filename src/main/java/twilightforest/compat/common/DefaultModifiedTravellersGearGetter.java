package twilightforest.compat.common;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import twilightforest.init.TFItems;
import twilightforest.TFRegistries;
import twilightforest.init.custom.TravellersModifiers;
import twilightforest.item.travellers_gear.modifiers.TravellersModifier;
import twilightforest.util.TravellersModifierUtil;

import java.util.List;

public class DefaultModifiedTravellersGearGetter {
	public static List<ItemStack> getDefaultModifiedTravellersGear(HolderLookup.Provider registries) {
		return List.of(
			getDefaultGoggles(registries),
			getDefaultVest(registries),
			getDefaultWings(registries),
			getDefaultBoots(registries)
		);
	}

	public static ItemStack getDemodifiedStack(ItemStack modifiedStack) {
		return new ItemStack(modifiedStack.getItem(), modifiedStack.getCount());
	}

	private static void addModifier(HolderLookup.Provider registries, ItemStack stack, ResourceKey<TravellersModifier> key) {
		Holder<TravellersModifier> modifier = registries.lookupOrThrow(TFRegistries.Keys.TRAVELLERS_MODIFIERS).getOrThrow(key);
		TravellersModifierUtil.addModifier(stack, modifier);
	}

	private static ItemStack getDefaultGoggles(HolderLookup.Provider registries) {
		ItemStack goggles = new ItemStack(TFItems.TRAVELLERS_GOGGLES.get());
		addModifier(registries, goggles, TravellersModifiers.AUTO_REPAIR_MODIFIER);
		addModifier(registries, goggles, TravellersModifiers.RED_THREAD_VISION_MODIFIER);
		addModifier(registries, goggles, TravellersModifiers.ALL_NIGHT_GOGGLES_MODIFIER);
		return goggles;
	}

	private static ItemStack getDefaultVest(HolderLookup.Provider registries) {
		ItemStack vest = new ItemStack(TFItems.TRAVELLERS_VEST.get());
		addModifier(registries, vest, TravellersModifiers.AUTO_REPAIR_MODIFIER);
		addModifier(registries, vest, TravellersModifiers.PERFECT_DODGE_MODIFIER);
		addModifier(registries, vest, TravellersModifiers.STEALTH_MODIFIER);
		return vest;
	}

	private static ItemStack getDefaultWings(HolderLookup.Provider registries) {
		ItemStack wings = new ItemStack(TFItems.TRAVELLERS_WINGS.get());
		addModifier(registries, wings, TravellersModifiers.AUTO_REPAIR_MODIFIER);
		addModifier(registries, wings, TravellersModifiers.AGILE_RANGER_MODIFIER);
		addModifier(registries, wings, TravellersModifiers.SIDESTEP_MODIFIER);
		return wings;
	}

	private static ItemStack getDefaultBoots(HolderLookup.Provider registries) {
		ItemStack boots = new ItemStack(TFItems.TRAVELLERS_BOOTS.get());
		addModifier(registries, boots, TravellersModifiers.AUTO_REPAIR_MODIFIER);
		addModifier(registries, boots, TravellersModifiers.STRAIGHT_AHEAD_MODIFIER);
		addModifier(registries, boots, TravellersModifiers.WATER_WALK_MODIFIER);
		return boots;
	}
}
