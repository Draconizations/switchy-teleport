package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchyEvents;
import folk.sisby.switchy.api.module.*;
import folk.sisby.switchy.util.Feedback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;

public class SpawnModule extends SpawnModuleData implements SwitchyModule, SwitchyModuleTransferable, SwitchyEvents.Init {
	@Override
	public void updateFromPlayer(ServerPlayerEntity player, @Nullable String nextPreset) {
		BlockPos respawnPoint = player.getSpawnPointPosition();

		if (respawnPoint != null) {
			location = new Location(
					respawnPoint.getX(), respawnPoint.getY(), respawnPoint.getZ(),
					0, player.getSpawnAngle(),
					player.getSpawnPointDimension().getValue(),
					player.isSpawnPointSet()
			);
		}
	}

	@Override
	public void applyToPlayer(ServerPlayerEntity player) {
		if (location != null) {
			player.setSpawnPoint(RegistryKey.of(Registry.WORLD_KEY, location.dimension()),
					new BlockPos(location.getCoordinates()),
					location.yaw(),
					Boolean.TRUE.equals(location.setSpawn()), false);
		}
	}

	@Override
	public void onInitialize() {
		SwitchyModuleRegistry.registerModule(ID, SpawnModule::new, new SwitchyModuleInfo(
				false,
				SwitchyModuleEditable.OPERATOR,
				Feedback.translatable("switchy.modules.switchy_teleport.spawn_point.description"))
				.withDescriptionWhenEnabled(Feedback.translatable("switchy.modules.switchy_teleport.spawn_point.enabled"))
				.withDescriptionWhenDisabled(Feedback.translatable("switchy.modules.switchy_teleport.spawn_point.disabled"))
				.withDeletionWarning(Feedback.translatable("switchy.modules.switchy_teleport.spawn_point.warning"))
		);
	}
}
