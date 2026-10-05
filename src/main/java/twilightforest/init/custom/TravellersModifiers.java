package twilightforest.init.custom;

import net.minecraft.resources.ResourceKey;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.item.travellers_gear.modifiers.*;

import java.util.Set;

public class TravellersModifiers {

	// all
	public static final ResourceKey<TravellersModifier> AUTO_REPAIR_MODIFIER = makeKey("auto_repair");
	// goggles
	public static final ResourceKey<TravellersModifier> ZOOM_ABILITY = makeKey("zoom");
	public static final ResourceKey<TravellersModifier> AQUATIC_AGILITY_MODIFIER = makeKey("aquatic_agility");
	public static final ResourceKey<TravellersModifier> RED_THREAD_VISION_MODIFIER = makeKey("red_thread_vision");
	public static final ResourceKey<TravellersModifier> ALL_NIGHT_GOGGLES_MODIFIER = makeKey("all_night_goggles");
	public static final ResourceKey<TravellersModifier> ITEM_DISPLAY_MODIFIER = makeKey("item_display");
	// vest
	public static final ResourceKey<TravellersModifier> SWIFT_SWIM_ABILITY = makeKey("swift_swim");
	public static final ResourceKey<TravellersModifier> STEALTH_MODIFIER = makeKey("stealth");
	public static final ResourceKey<TravellersModifier> ARROW_MAGNETISM_MODIFIER = makeKey("arrow_magnetism");
	public static final ResourceKey<TravellersModifier> EFFICIENT_EATER_MODIFIER = makeKey("efficient_eater");
	public static final ResourceKey<TravellersModifier> PERFECT_DODGE_MODIFIER = makeKey("perfect_dodge");
	public static final ResourceKey<TravellersModifier> HASTE_MODIFIER = makeKey("haste");
	// belt
	public static final ResourceKey<TravellersModifier> SWAP_HOTBAR_ABILITY = makeKey("swap_hotbar_ability");
	public static final ResourceKey<TravellersModifier> SWAP_HOTBAR_MODIFIER = makeKey("swap_hotbar");
	// wings
	public static final ResourceKey<TravellersModifier> HIGH_JUMP_ABILITY = makeKey("high_jump");
	public static final ResourceKey<TravellersModifier> GRADUAL_GLIDE_MODIFIER = makeKey("gradual_glide");
	public static final ResourceKey<TravellersModifier> AGILE_RANGER_MODIFIER = makeKey("agile_ranger");
	public static final ResourceKey<TravellersModifier> DOUBLE_JUMP_MODIFIER = makeKey("double_jump");
	public static final ResourceKey<TravellersModifier> SIDESTEP_MODIFIER = makeKey("side_step");
	// boots
	public static final ResourceKey<TravellersModifier> STEP_UP_ABILITY = makeKey("step_up");
	public static final ResourceKey<TravellersModifier> STRAIGHT_AHEAD_MODIFIER = makeKey("straight_ahead");
	public static final ResourceKey<TravellersModifier> SLIMY_SOLES_MODIFIER = makeKey("slimy_soles");
	public static final ResourceKey<TravellersModifier> UNRESTRAINED_MODIFIER = makeKey("unrestrained");
	public static final ResourceKey<TravellersModifier> WATER_WALK_MODIFIER = makeKey("water_walk");

	public static final Set<ResourceKey<TravellersModifier>> ALWAYS_ACTIVE = Set.of(AUTO_REPAIR_MODIFIER);

	private static ResourceKey<TravellersModifier> makeKey(String name) {
		return ResourceKey.create(TFRegistries.Keys.TRAVELLERS_MODIFIERS, TwilightForestMod.prefix(name));
	}
}
