package xyz.fulmine.switchy_teleport.modules.client;

import com.mojang.datafixers.util.Pair;
import folk.sisby.switchy.client.api.SwitchyClientEvents;
import folk.sisby.switchy.client.api.module.SwitchyClientModule;
import folk.sisby.switchy.client.api.module.SwitchyClientModuleRegistry;
import folk.sisby.switchy.ui.api.SwitchyUIPosition;
import folk.sisby.switchy.ui.api.module.SwitchyUIModule;
import folk.sisby.switchy.util.Feedback;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.ItemComponent;
import io.wispforest.owo.ui.core.Component;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.modules.SpawnModuleData;

public class SpawnClientModule extends SpawnModuleData implements SwitchyClientModule, SwitchyUIModule, SwitchyClientEvents.Init {
	@Override
	public @Nullable Pair<Component, SwitchyUIPosition> getPreviewComponent(String presetName) {
		if (location == null) return null;
		ItemComponent item = Components.item(Items.RED_BED.getDefaultStack());
		item.tooltip(Feedback.translatable(
			"switchy.modules.switchy_teleport.spawn_point.preview.tooltip",
			Feedback.literal(WordUtils.capitalize(location.dimension().getPath().replace('_', ' '))).setStyle(Style.EMPTY.withColor(Formatting.WHITE)),
			Feedback.literal(String.valueOf(Math.round(location.x()))).setStyle(Style.EMPTY.withColor(Formatting.WHITE)),
			Feedback.literal(String.valueOf(Math.round(location.y()))).setStyle(Style.EMPTY.withColor(Formatting.WHITE)),
			Feedback.literal(String.valueOf(Math.round(location.z()))).setStyle(Style.EMPTY.withColor(Formatting.WHITE))
		).setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
		return Pair.of(item, SwitchyUIPosition.GRID_RIGHT);
	}

	@Override
	public void onInitialize() {
		SwitchyClientModuleRegistry.registerModule(ID, SpawnClientModule::new);
	}
}
