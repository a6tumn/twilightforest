package twilightforest.datagen.data.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import twilightforest.components.item.ItemDisplayContents;
import twilightforest.init.TFAttributeModifiers;
import twilightforest.init.TFDataComponents;
import twilightforest.init.custom.TravellersModifiers;
import twilightforest.item.travellers_gear.TravellersArmorBeltItem;
import twilightforest.item.travellers_gear.modifiers.*;

import java.util.List;

public class TravellersModifierGenerator {

    public static void bootstrap(BootstrapContext<TravellersModifier> context) {
        context.register(TravellersModifiers.AUTO_REPAIR_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.ARMOR, TFDataComponents.AUTO_REPAIR_PROBABILITY.get(), 0.001F, componentText(TravellersModifiers.AUTO_REPAIR_MODIFIER)));
        context.register(TravellersModifiers.ZOOM_ABILITY, new BuiltinTravellersComponentModifier(EquipmentSlotGroup.HEAD, TFDataComponents.ZOOM_ABILITY_MODIFIER.get()));
        context.register(TravellersModifiers.AQUATIC_AGILITY_MODIFIER, new TravellersEntryModifier(EquipmentSlotGroup.HEAD, List.of(
            new ItemAttributeModifiers.Entry(Attributes.OXYGEN_BONUS, TFAttributeModifiers.TRAVELLERS_AQUATIC_AGILITY_OXYGEN, EquipmentSlotGroup.HEAD),
            new ItemAttributeModifiers.Entry(Attributes.SUBMERGED_MINING_SPEED, TFAttributeModifiers.TRAVELLERS_AQUATIC_AGILITY_MINING, EquipmentSlotGroup.HEAD)
        ), TFDataComponents.AQUATIC_AGILITY, componentText(TravellersModifiers.AQUATIC_AGILITY_MODIFIER), false));
        context.register(TravellersModifiers.RED_THREAD_VISION_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.HEAD, TFDataComponents.RED_THREAD_VISION.get(), Unit.INSTANCE, componentText(TravellersModifiers.RED_THREAD_VISION_MODIFIER)));
        context.register(TravellersModifiers.ALL_NIGHT_GOGGLES_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.HEAD, TFDataComponents.ALL_NIGHT_GOGGLES.get(), Unit.INSTANCE, componentText(TravellersModifiers.ALL_NIGHT_GOGGLES_MODIFIER)));
        context.register(TravellersModifiers.ITEM_DISPLAY_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.HEAD, TFDataComponents.ITEM_DISPLAY.get(), ItemDisplayContents.EMPTY, componentText(TravellersModifiers.ITEM_DISPLAY_MODIFIER)));

        context.register(TravellersModifiers.SWIFT_SWIM_ABILITY, new TravellersEntryModifier(EquipmentSlotGroup.CHEST, List.of(new ItemAttributeModifiers.Entry(Attributes.WATER_MOVEMENT_EFFICIENCY, TFAttributeModifiers.TRAVELLERS_SWIFT_SWIM, EquipmentSlotGroup.CHEST)), TFDataComponents.SWIFT_SWIM, true));
        context.register(TravellersModifiers.STEALTH_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.CHEST, TFDataComponents.STEALTH_CROUCHING.get(), Unit.INSTANCE, componentText(TravellersModifiers.STEALTH_MODIFIER)));
        context.register(TravellersModifiers.ARROW_MAGNETISM_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.CHEST, TFDataComponents.ARROW_MAGNETISM.get(), Unit.INSTANCE, componentText(TravellersModifiers.ARROW_MAGNETISM_MODIFIER)));
        context.register(TravellersModifiers.EFFICIENT_EATER_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.CHEST, TFDataComponents.EFFICIENT_EATER.get(), 2F, componentText(TravellersModifiers.EFFICIENT_EATER_MODIFIER)));
        context.register(TravellersModifiers.PERFECT_DODGE_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.CHEST, TFDataComponents.PERFECT_DODGE_PROBABILITY.get(), 0.3F, componentText(TravellersModifiers.PERFECT_DODGE_MODIFIER)));
        context.register(TravellersModifiers.HASTE_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.CHEST, TFDataComponents.HASTE_AMPLIFIER.get(), 1, componentText(TravellersModifiers.HASTE_MODIFIER)));

        context.register(TravellersModifiers.SWAP_HOTBAR_ABILITY, new BuiltinTravellersComponentModifier(EquipmentSlotGroup.LEGS, TFDataComponents.SWAP_HOTBAR_ABILITY.get()));
        context.register(TravellersModifiers.SWAP_HOTBAR_MODIFIER, new TransferableComponentModifier(EquipmentSlotGroup.LEGS, TFDataComponents.SWAP_HOTBAR_MODIFIER.get(), DataComponents.CONTAINER, TravellersArmorBeltItem.DEFAULT_EMPTY_BELT_CONTAINER, componentText(TravellersModifiers.SWAP_HOTBAR_MODIFIER)));

        context.register(TravellersModifiers.HIGH_JUMP_ABILITY, new BuiltinTravellersComponentModifier(EquipmentSlotGroup.LEGS, TFDataComponents.HIGH_JUMP_AMPLIFIER.get()));
        context.register(TravellersModifiers.GRADUAL_GLIDE_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.LEGS, TFDataComponents.GRADUALLY_GLIDING_MULTIPLIER.get(), 1 - 1 / 6F, componentText(TravellersModifiers.GRADUAL_GLIDE_MODIFIER)));
        context.register(TravellersModifiers.AGILE_RANGER_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.LEGS, TFDataComponents.AGILE_RANGER_MODIFIER.get(), Unit.INSTANCE, componentText(TravellersModifiers.AGILE_RANGER_MODIFIER)));
        context.register(TravellersModifiers.DOUBLE_JUMP_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.LEGS, TFDataComponents.DOUBLE_JUMP.get(), Unit.INSTANCE, componentText(TravellersModifiers.DOUBLE_JUMP_MODIFIER)));
        context.register(TravellersModifiers.SIDESTEP_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.LEGS, TFDataComponents.SIDESTEP_COOLDOWN.get(), 2 * 20L, componentText(TravellersModifiers.SIDESTEP_MODIFIER, Component.keybind("key.left"), Component.keybind("key.right"))));

        context.register(TravellersModifiers.STEP_UP_ABILITY, new TravellersEntryModifier(EquipmentSlotGroup.FEET, List.of(new ItemAttributeModifiers.Entry(Attributes.STEP_HEIGHT, TFAttributeModifiers.TRAVELLERS_HIGH_STEP, EquipmentSlotGroup.FEET)), TFDataComponents.HIGH_STEP, true));
        context.register(TravellersModifiers.STRAIGHT_AHEAD_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.FEET, TFDataComponents.STRAIGHT_AHEAD_MULTIPLIER.get(), 1.4, componentText(TravellersModifiers.STRAIGHT_AHEAD_MODIFIER)));
        context.register(TravellersModifiers.SLIMY_SOLES_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.FEET, TFDataComponents.SLIMY_SOLES_COEFFICIENT.get(), 0.5F, componentText(TravellersModifiers.SLIMY_SOLES_MODIFIER)));
        context.register(TravellersModifiers.UNRESTRAINED_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.FEET, TFDataComponents.UNRESTRAINED.get(), Unit.INSTANCE, componentText(TravellersModifiers.UNRESTRAINED_MODIFIER)));
        context.register(TravellersModifiers.WATER_WALK_MODIFIER, new TravellersComponentModifier(EquipmentSlotGroup.FEET, TFDataComponents.WATER_WALK.get(), Unit.INSTANCE, componentText(TravellersModifiers.WATER_WALK_MODIFIER)));
    }

    private static List<Component> componentText(ResourceKey<TravellersModifier> modifier, Object... args) {
        return List.of(Component.translatable(modifier.identifier().toLanguageKey("travellers_gear.modifier", "description"), args));
    }

}
