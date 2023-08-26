package xyz.fulmine.switchy_teleport.modules.client;

import com.mojang.datafixers.util.Pair;
import folk.sisby.switchy.client.api.SwitchyClientEvents;
import folk.sisby.switchy.client.api.module.SwitchyClientModule;
import folk.sisby.switchy.client.api.module.SwitchyClientModuleRegistry;
import folk.sisby.switchy.ui.api.SwitchyUIPosition;
import folk.sisby.switchy.ui.api.module.SwitchyUIModule;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.ItemComponent;
import io.wispforest.owo.ui.core.Component;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.modules.LocationModuleData;

public class LocationClientModule extends LocationModuleData implements SwitchyClientModule, SwitchyUIModule, SwitchyClientEvents.Init {
	@Override
	public @Nullable Pair<Component, SwitchyUIPosition> getPreviewComponent(String presetName) {
		if (location == null) return null;
		ItemComponent item = Components.item(Items.COMPASS.getDefaultStack());
		item.tooltip(Text.translatable(
			"switchy.modules.switchy_teleport.last_location.preview.tooltip",
			Text.literal(WordUtils.capitalize(location.dimension().getPath().replace('_', ' '))).setStyle(Style.EMPTY.withColor(Formatting.WHITE)),
			Text.literal(String.valueOf(Math.round(location.x()))).setStyle(Style.EMPTY.withColor(Formatting.WHITE)),
			Text.literal(String.valueOf(Math.round(location.y()))).setStyle(Style.EMPTY.withColor(Formatting.WHITE)),
			Text.literal(String.valueOf(Math.round(location.z()))).setStyle(Style.EMPTY.withColor(Formatting.WHITE))
		).setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
		return Pair.of(item, SwitchyUIPosition.GRID_RIGHT);
	}

	@Override
	public void onInitialize() {
		SwitchyClientModuleRegistry.registerModule(ID, LocationClientModule::new);
	}
}
