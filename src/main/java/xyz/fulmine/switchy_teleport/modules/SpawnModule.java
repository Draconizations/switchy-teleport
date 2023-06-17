package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchyEvents;
import folk.sisby.switchy.api.module.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
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
			player.setSpawnPoint(RegistryKey.of(RegistryKeys.WORLD, location.dimension()),
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
				Text.translatable("switchy.teleport.module.spawn_point.description"))
				.withDescriptionWhenEnabled(Text.translatable("switchy.teleport.module.spawn_point.enabled"))
				.withDescriptionWhenDisabled(Text.translatable("switchy.teleport.module.spawn_point.disabled"))
				.withDeletionWarning(Text.translatable("switchy.teleport.module.spawn_point.warning"))
		);
	}
}
