package twilightforest.client.state.block;

import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import org.jspecify.annotations.Nullable;

public class JarRenderState extends BlockEntityRenderState {
	public Item lid;
	public @Nullable StandaloneModelKey<BlockStateModelPart> lidKey;
	public DecoratedPotBlockEntity.WobbleStyle lastWobbleStyle;
	public float gameTime;
}