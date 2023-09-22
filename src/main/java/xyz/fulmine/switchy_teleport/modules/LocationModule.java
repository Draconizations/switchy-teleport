package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchyEvents;
import folk.sisby.switchy.api.module.*;
import folk.sisby.switchy.util.Feedback;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.utils.TeleportUtils;

public class LocationModule extends LocationModuleData implements SwitchyModule, SwitchyModuleTransferable, SwitchyEvents.Init {
	@Override
	public void updateFromPlayer(ServerPlayerEntity player, @Nullable String nextPreset) {
		location = new Location(
				player.getX(), player.getY(), player.getZ(),
				player.getPitch(), player.getYaw(),
				player.getWorld().getRegistryKey().getValue(),
				null
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
				Feedback.translatable("switchy.modules.switchy_teleport.last_location.description"))
				.withDescriptionWhenEnabled(Feedback.translatable("switchy.modules.switchy_teleport.last_location.enabled"))
				.withDescriptionWhenDisabled(Feedback.translatable("switchy.modules.switchy_teleport.last_location.disabled"))
				.withDeletionWarning(Feedback.translatable("switchy.modules.switchy_teleport.last_location.warning"))
		);
	}
}
