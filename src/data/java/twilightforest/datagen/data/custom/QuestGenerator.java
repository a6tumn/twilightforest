package twilightforest.datagen.data.custom;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.JsonCodecProvider;
import twilightforest.TwilightForestMod;
import twilightforest.entity.passive.quest.ram.QuestingRamContext;

import java.util.concurrent.CompletableFuture;

public class QuestGenerator extends JsonCodecProvider<QuestingRamContext> {

	public QuestGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, PackOutput.Target.DATA_PACK, "twilight/quests", QuestingRamContext.CODEC, lookupProvider, TwilightForestMod.ID);
	}

	@Override
	protected void gather() {
		unconditional(TwilightForestMod.prefix("questing_ram"), QuestingRamContext.FALLBACK);
	}
}
