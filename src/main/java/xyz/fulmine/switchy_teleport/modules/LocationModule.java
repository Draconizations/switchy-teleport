package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchyEvents;
import folk.sisby.switchy.api.module.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.utils.TeleportUtils;

public class LocationModule extends LocationModuleData implements SwitchyModule, SwitchyModuleTransferable, SwitchyEvents.Init {
	@Override
	public void updateFromPlayer(ServerPlayerEntity player, @Nullable String nextPreset) {
		location = new Location(
				player.getX(), player.getY(), player.getZ(),
				player.getPitch(), player.getYaw(),
				player.getWorld().getRegistryKey().getValue()
		);
	}

	@Override
	public void applyToPlayer(ServerPlayerEntity player) {

		if (location != null) {
			TeleportUtils.teleportPlayer(player, location);
		}
	}

	@Override
	public void onInitialize() {
		SwitchyModuleRegistry.registerModule(ID, LocationModule::new, new SwitchyModuleInfo(
				false,
				SwitchyModuleEditable.OPERATOR,
				Text.translatable("switchy.teleport.module.last_location.description"))
				.withDescriptionWhenEnabled(Text.translatable("switchy.teleport.module.last_location.enabled"))
				.withDescriptionWhenDisabled(Text.translatable("switchy.teleport.module.last_location.disabled"))
				.withDeletionWarning(Text.translatable("switchy.teleport.module.last_location.warning"))
		);
	}
}
