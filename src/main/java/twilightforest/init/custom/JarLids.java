package twilightforest.init.custom;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import twilightforest.TFRegistries;
import twilightforest.block.JarLid;
import twilightforest.init.TFBlocks;

public final class JarLids {
	/*public static final ResourceKey<JarLid> ACACIA_LOG = key(Items.ACACIA_LOG);
	public static final ResourceKey<JarLid> BIRCH_LOG = key(Items.BIRCH_LOG);
	public static final ResourceKey<JarLid> CHERRY_LOG = key(Items.CHERRY_LOG);
	public static final ResourceKey<JarLid> DARK_OAK_LOG = key(Items.DARK_OAK_LOG);
	public static final ResourceKey<JarLid> JUNGLE_LOG = key(Items.JUNGLE_LOG);
	public static final ResourceKey<JarLid> MANGROVE_LOG = key(Items.MANGROVE_LOG);
	public static final ResourceKey<JarLid> OAK_LOG = key(Items.OAK_LOG);
	public static final ResourceKey<JarLid> SPRUCE_LOG = key(Items.SPRUCE_LOG);
	public static final ResourceKey<JarLid> CRIMSON_STEM = key(Items.CRIMSON_STEM);
	public static final ResourceKey<JarLid> WARPED_STEM = key(Items.WARPED_STEM);

	public static final ResourceKey<JarLid> STRIPPED_ACACIA_LOG = key(Items.STRIPPED_ACACIA_LOG);
	public static final ResourceKey<JarLid> STRIPPED_BIRCH_LOG = key(Items.STRIPPED_BIRCH_LOG);
	public static final ResourceKey<JarLid> STRIPPED_CHERRY_LOG = key(Items.STRIPPED_CHERRY_LOG);
	public static final ResourceKey<JarLid> STRIPPED_DARK_OAK_LOG = key(Items.STRIPPED_DARK_OAK_LOG);
	public static final ResourceKey<JarLid> STRIPPED_JUNGLE_LOG = key(Items.STRIPPED_JUNGLE_LOG);
	public static final ResourceKey<JarLid> STRIPPED_MANGROVE_LOG = key(Items.STRIPPED_MANGROVE_LOG);
	public static final ResourceKey<JarLid> STRIPPED_OAK_LOG = key(Items.STRIPPED_OAK_LOG);
	public static final ResourceKey<JarLid> STRIPPED_SPRUCE_LOG = key(Items.STRIPPED_SPRUCE_LOG);
	public static final ResourceKey<JarLid> STRIPPED_CRIMSON_STEM = key(Items.STRIPPED_CRIMSON_STEM);
	public static final ResourceKey<JarLid> STRIPPED_WARPED_STEM = key(Items.STRIPPED_WARPED_STEM);

	public static final ResourceKey<JarLid> PUMPKIN = key(Items.PUMPKIN);
	public static final ResourceKey<JarLid> BAMBOO_BLOCK = key(Items.BAMBOO_BLOCK);
	public static final ResourceKey<JarLid> STRIPPED_BAMBOO_BLOCK = key(Items.STRIPPED_BAMBOO_BLOCK);

	public static final ResourceKey<JarLid> MANGROVE_LOG_TWILIGHT = key(TFBlocks.MANGROVE_LOG.asItem());
	public static final ResourceKey<JarLid> CANOPY_LOG = key(TFBlocks.CANOPY_LOG.asItem());
	public static final ResourceKey<JarLid> DARK_LOG = key(TFBlocks.DARK_LOG.asItem());
	public static final ResourceKey<JarLid> MINING_LOG = key(TFBlocks.MINING_LOG.asItem());
	public static final ResourceKey<JarLid> SORTING_LOG = key(TFBlocks.SORTING_LOG.asItem());
	public static final ResourceKey<JarLid> TIME_LOG = key(TFBlocks.TIME_LOG.asItem());
	public static final ResourceKey<JarLid> TRANSFORMATION_LOG = key(TFBlocks.TRANSFORMATION_LOG.asItem());
	public static final ResourceKey<JarLid> TWILIGHT_OAK_LOG = key(TFBlocks.TWILIGHT_OAK_LOG.asItem());

	public static final ResourceKey<JarLid> STRIPPED_MANGROVE_LOG_TWILIGHT = key(TFBlocks.STRIPPED_MANGROVE_LOG.asItem());
	public static final ResourceKey<JarLid> STRIPPED_CANOPY_LOG = key(TFBlocks.STRIPPED_CANOPY_LOG.asItem());
	public static final ResourceKey<JarLid> STRIPPED_DARK_LOG = key(TFBlocks.STRIPPED_DARK_LOG.asItem());
	public static final ResourceKey<JarLid> STRIPPED_MINING_LOG = key(TFBlocks.STRIPPED_MINING_LOG.asItem());
	public static final ResourceKey<JarLid> STRIPPED_SORTING_LOG = key(TFBlocks.STRIPPED_SORTING_LOG.asItem());
	public static final ResourceKey<JarLid> STRIPPED_TIME_LOG = key(TFBlocks.STRIPPED_TIME_LOG.asItem());
	public static final ResourceKey<JarLid> STRIPPED_TRANSFORMATION_LOG = key(TFBlocks.STRIPPED_TRANSFORMATION_LOG.asItem());
	public static final ResourceKey<JarLid> STRIPPED_TWILIGHT_OAK_LOG = key(TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.asItem());
	public static final ResourceKey<JarLid> CINDER_LOG = key(TFBlocks.CINDER_LOG.asItem());*/

	public static void bootstrap(BootstrapContext<JarLid> context) {
		register(context, Items.ACACIA_LOG);
		register(context, Items.BIRCH_LOG);
		register(context, Items.CHERRY_LOG);
		register(context, Items.DARK_OAK_LOG);
		register(context, Items.JUNGLE_LOG);
		register(context, Items.MANGROVE_LOG);
		register(context, Items.OAK_LOG);
		register(context, Items.SPRUCE_LOG);
		register(context, Items.CRIMSON_STEM);
		register(context, Items.WARPED_STEM);

		register(context, Items.STRIPPED_ACACIA_LOG);
		register(context, Items.STRIPPED_BIRCH_LOG);
		register(context, Items.STRIPPED_CHERRY_LOG);
		register(context, Items.STRIPPED_DARK_OAK_LOG);
		register(context, Items.STRIPPED_JUNGLE_LOG);
		register(context, Items.STRIPPED_MANGROVE_LOG);
		register(context, Items.STRIPPED_OAK_LOG);
		register(context, Items.STRIPPED_SPRUCE_LOG);
		register(context, Items.STRIPPED_CRIMSON_STEM);
		register(context, Items.STRIPPED_WARPED_STEM);

		register(context, Items.PUMPKIN);
		register(context, Items.BAMBOO_BLOCK);
		register(context, Items.STRIPPED_BAMBOO_BLOCK);

		register(context, TFBlocks.MANGROVE_LOG.asItem());
		register(context, TFBlocks.CANOPY_LOG.asItem());
		register(context, TFBlocks.DARK_LOG.asItem());
		register(context, TFBlocks.MINING_LOG.asItem());
		register(context, TFBlocks.SORTING_LOG.asItem());
		register(context, TFBlocks.TIME_LOG.asItem());
		register(context, TFBlocks.TRANSFORMATION_LOG.asItem());
		register(context, TFBlocks.TWILIGHT_OAK_LOG.asItem());

		register(context, TFBlocks.STRIPPED_MANGROVE_LOG.asItem());
		register(context, TFBlocks.STRIPPED_CANOPY_LOG.asItem());
		register(context, TFBlocks.STRIPPED_DARK_LOG.asItem());
		register(context, TFBlocks.STRIPPED_MINING_LOG.asItem());
		register(context, TFBlocks.STRIPPED_SORTING_LOG.asItem());
		register(context, TFBlocks.STRIPPED_TIME_LOG.asItem());
		register(context, TFBlocks.STRIPPED_TRANSFORMATION_LOG.asItem());
		register(context, TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.asItem());
		register(context, TFBlocks.CINDER_LOG.asItem());
	}

	public static Identifier model(Item item) {
		Identifier itemId = item.builtInRegistryHolder().key().identifier();

		return Identifier.fromNamespaceAndPath(
			itemId.getNamespace(),
			"block/lid/" + itemId.getPath()
		);
	}

	private static ResourceKey<JarLid> key(Item item) {
		return ResourceKey.create(
			TFRegistries.Keys.JAR_LIDS,
			item.builtInRegistryHolder().key().identifier()
		);
	}

	private static void register(BootstrapContext<JarLid> context, Item item) {
		context.register(
			key(item),
			new JarLid(
				item.builtInRegistryHolder().key(),
				model(item)
			)
		);
	}
}