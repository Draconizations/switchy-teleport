package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchyEvents;
import folk.sisby.switchy.api.module.SwitchyModule;
import folk.sisby.switchy.api.module.SwitchyModuleEditable;
import folk.sisby.switchy.api.module.SwitchyModuleInfo;
import folk.sisby.switchy.api.module.SwitchyModuleRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.SwitchyTeleport;

public class SpawnModule implements SwitchyModule, SwitchyEvents.Init {
	final static Identifier ID = new Identifier(SwitchyTeleport.ID, "spawn_point");

	final static String KEY_SPAWN = "respawn_point";

	@Nullable private Location spawn = null;

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
	public NbtCompound toNbt() {
		NbtCompound outNbt = new NbtCompound();
		NbtCompound nbtSpawn = new NbtCompound();

		if (this.spawn != null) {
			nbtSpawn.putDouble("x", this.spawn.getX());
			nbtSpawn.putDouble("y", this.spawn.getY());
			nbtSpawn.putDouble("z", this.spawn.getZ());

			nbtSpawn.putFloat("angle", this.spawn.getYaw());

			nbtSpawn.putString("dimension", this.spawn.getDimensionID().toString());
		}

		outNbt.put(KEY_SPAWN, nbtSpawn);

		return outNbt;
	}

	@Override
	public void fillFromNbt(NbtCompound nbt) {
		if (nbt.contains(KEY_SPAWN, NbtElement.COMPOUND_TYPE)) {
			NbtCompound nbtSpawn = nbt.getCompound(KEY_SPAWN);
			if (nbtSpawn.contains("x", NbtElement.DOUBLE_TYPE)) {
				this.spawn = new Location(
						nbtSpawn.getDouble("x"),
						nbtSpawn.getDouble("y"),
						nbtSpawn.getDouble("z"),
						0,
						nbtSpawn.getFloat("angle"),
						new Identifier(nbtSpawn.getString("dimension"))
				);
			}
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
