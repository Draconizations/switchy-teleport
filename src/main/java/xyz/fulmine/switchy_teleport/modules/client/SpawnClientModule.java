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
import xyz.fulmine.switchy_teleport.modules.SpawnModuleData;

public class SpawnClientModule extends SpawnModuleData implements SwitchyClientModule, SwitchyUIModule, SwitchyClientEvents.Init {
	@Override
	public @Nullable Pair<Component, SwitchyUIPosition> getPreviewComponent(String presetName) {
		if (location == null) return null;
		ItemComponent item = Components.item(Items.RED_BED.getDefaultStack());
		item.tooltip(Text.translatable("switchy.teleport.module.spawn_point.tooltip", Math.round(location.x()), Math.round(location.y()), Math.round(location.z()), location.dimension().getPath()));
		return Pair.of(item, SwitchyUIPosition.SIDE_RIGHT);
	}

	@Override
	public void onInitialize() {
		SwitchyClientModuleRegistry.registerModule(ID, SpawnClientModule::new);
	}
}
