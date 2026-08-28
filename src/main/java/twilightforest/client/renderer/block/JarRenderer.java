package twilightforest.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity.WobbleStyle;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import org.jspecify.annotations.Nullable;
import tamaized.beanification.Autowired;
import tamaized.beanification.Configurable;
import twilightforest.block.entity.JarBlockEntity;
import twilightforest.block.entity.MasonJarBlockEntity;
import twilightforest.client.state.block.JarRenderState;
import twilightforest.enums.extensions.TFItemDisplayContextEnumExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JarRenderer<T extends JarBlockEntity> implements BlockEntityRenderer<T, JarRenderState> {
	protected static final float WOBBLE_AMPLITUDE = 0.125F;

	private final BlockModelResolver blockModelResolver;

	public JarRenderer(BlockEntityRendererProvider.Context context) {
		this.blockModelResolver = context.blockModelResolver();
	}

	@Override
	public JarRenderState createRenderState() {
		return new JarRenderState();
	}

	@Override
	public void extractRenderState(T blockEntity, JarRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

		state.blockStateModel = this.blockModelResolver.modelManager.getBlockStateModelSet().get(blockEntity.getBlockState());

		state.blockModelRenderState = new BlockModelRenderState();
		this.blockModelResolver.update(
			state.blockModelRenderState,
			blockEntity.getBlockState(),
			BlockDisplayContext.create()
		);

		state.lidKey = null;
		var level = blockEntity.getLevel();
		if (level != null) {
			state.lidKey = LidModelKeyRegistry.get(blockEntity.lid.builtInRegistryHolder().key());
		}

		state.lastWobbleStyle = blockEntity.lastWobbleStyle;
		state.gameTime = level != null
			? (float) (level.getGameTime() - blockEntity.wobbleStartedAtTick) + partialTicks
			: 0.0F;
	}

	@Override
	public int getViewDistance() {
		return 256;
	}

	@Override
	public void submit(JarRenderState blockEntity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.translate(0.5, 0.0, 0.5);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		poseStack.translate(-0.5, 0.0, -0.5);

		WobbleStyle wobbleStyle = blockEntity.lastWobbleStyle;
		if (wobbleStyle != null) {
			float f = blockEntity.gameTime / (float) wobbleStyle.duration;
			if (f >= 0.0F && f <= 1.0F) {
				if (wobbleStyle == WobbleStyle.POSITIVE) {
					float f1 = 0.015625F;
					float f2 = f * (float) (Math.PI * 2);
					float f3 = -1.5F * (Mth.cos(f2) + 0.5F) * Mth.sin(f2 / 2.0F);
					poseStack.rotateAround(Axis.XP.rotation(f3 * f1), 0.5F, 0.0F, 0.5F);
					float f4 = Mth.sin(f2);
					poseStack.rotateAround(Axis.ZP.rotation(f4 * f1), 0.5F, 0.0F, 0.5F);
				} else {
					float f5 = Mth.sin(-f * 3.0F * (float) Math.PI) * WOBBLE_AMPLITUDE;
					float f6 = 1.0F - f;
					poseStack.rotateAround(Axis.YP.rotation(f5 * f6), 0.5F, 0.0F, 0.5F);
				}
			}
		}

		blockEntity.blockModelRenderState.submit(
			poseStack,
			buffer,
			blockEntity.lightCoords,
			OverlayTexture.NO_OVERLAY,
			0
		);

		if (blockEntity.breakProgress != null) {
			buffer.submitBreakingBlockModel(
				poseStack,
				blockEntity.blockStateModel,
				42L,
				blockEntity.breakProgress.progress()
			);
		}

		if (blockEntity.lidKey != null) {
			BlockStateModelPart lid = blockModelResolver.modelManager.getStandaloneModel(blockEntity.lidKey);
			if (lid != null) {
				buffer.submitMultiLayerBlockModel(
					poseStack,
					List.of(lid),
					false,
					new int[0],
					blockEntity.lightCoords,
					OverlayTexture.NO_OVERLAY,
					0
				);

				if (blockEntity.breakProgress != null) {
					buffer.submitBreakingBlockModel(
						poseStack,
						singlePartModel(lid),
						42L,
						blockEntity.breakProgress.progress()
					);
				}
			}
		}

		if (blockEntity.itemStackRenderState != null) {
			poseStack.pushPose();
			poseStack.translate(0.5D, 0.4375D, 0.5D);
			poseStack.mulPose(Axis.YN.rotationDegrees(RotationSegment.convertToDegrees(blockEntity.itemRotation)));
			poseStack.scale(0.5F, 0.5F, 0.5F);
			blockEntity.itemStackRenderState.submit(
				poseStack,
				buffer,
				blockEntity.lightCoords,
				OverlayTexture.NO_OVERLAY,
				0
			);
			poseStack.popPose();
		}

		poseStack.popPose();
	}

	private static BlockStateModel singlePartModel(BlockStateModelPart part) {
		return new BlockStateModel() {
			@Override
			public void collectParts(RandomSource random, List<BlockStateModelPart> parts) {
				parts.add(part);
			}

			@Override
			public Material.Baked particleMaterial() {
				return part.particleMaterial();
			}

			@Override
			public int materialFlags() {
				return part.materialFlags();
			}
		};
	}

	@Configurable
	public static class MasonJarRenderer extends JarRenderer<MasonJarBlockEntity> {

		@Autowired(dist = Dist.CLIENT)
		private TFItemDisplayContextEnumExtension itemDisplayContextEnumExtension;

		private final ItemModelResolver itemModelResolver;

		public MasonJarRenderer(BlockEntityRendererProvider.Context context) {
			super(context);
			this.itemModelResolver = context.itemModelResolver();
		}

		@Override
		public void extractRenderState(MasonJarBlockEntity blockEntity, JarRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
			super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

			state.itemStackRenderState = null;
			state.itemRotation = 0;

			ItemStack stack = blockEntity.getItemHandler().getItem();
			if (!stack.isEmpty()) {
				state.itemStackRenderState = new ItemStackRenderState();
				this.itemModelResolver.updateForTopItem(
					state.itemStackRenderState,
					stack,
					itemDisplayContextEnumExtension.JARRED,
					null,
					null,
					0
				);
				state.itemRotation = blockEntity.getItemRotation();
			}
		}
	}

	public static final class LidModelKeyRegistry {
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
}