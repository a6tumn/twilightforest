package twilightforest.client.state.block;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import org.jspecify.annotations.Nullable;

public class JarRenderState extends BlockEntityRenderState {
	public BlockModelRenderState blockModelRenderState;
	public BlockStateModel blockStateModel;
	public @Nullable StandaloneModelKey<BlockStateModelPart> lidKey;
	public DecoratedPotBlockEntity.WobbleStyle lastWobbleStyle;
	public float gameTime;
	public @Nullable ItemStackRenderState itemStackRenderState;
	public int itemRotation;
}