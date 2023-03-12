package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchyEvents;
import folk.sisby.switchy.api.module.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
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
			this.spawn = new Location(
					respawnPoint.getX(), respawnPoint.getY(), respawnPoint.getZ(),
					0, player.getSpawnAngle(),
					player.getSpawnPointDimension().getValue()
			);
		}
	}

	@Override
	public void applyToPlayer(ServerPlayerEntity player) {
		if (this.spawn != null) {
			player.setSpawnPoint(RegistryKey.of(Registry.WORLD_KEY, spawn.getDimensionID()),
					new BlockPos(spawn.getX(), spawn.getY(), spawn.getZ()),
					spawn.getYaw(),
					false, false);
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
