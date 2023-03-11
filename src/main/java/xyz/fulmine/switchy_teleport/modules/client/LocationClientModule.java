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
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.modules.LocationModuleData;

public class LocationClientModule extends LocationModuleData implements SwitchyClientModule, SwitchyUIModule, SwitchyClientEvents.Init {
	@Override
	public @Nullable Pair<Component, SwitchyUIPosition> getPreviewComponent(String presetName) {
		if (location == null) return null;
		ItemComponent item = Components.item(Items.COMPASS.getDefaultStack());
		item.tooltip(Text.translatable("switchy.teleport.module.last_location.tooltip", Math.round(location.getX()), Math.round(location.getY()), Math.round(location.getZ())));
		return Pair.of(item, SwitchyUIPosition.SIDE_RIGHT);
	}

	@Override
	public void onInitialize() {
		SwitchyClientModuleRegistry.registerModule(ID, LocationClientModule::new);
	}
}
