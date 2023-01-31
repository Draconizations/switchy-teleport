package xyz.fulmine.switchy_teleport.compat;

import folk.sisby.switchy.api.ModuleImportable;
import folk.sisby.switchy.api.PresetModule;
import folk.sisby.switchy.api.PresetModuleRegistry;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.SwitchyTeleport;

import java.util.Set;

public class SpawnPointCompat implements PresetModule {
	final static Identifier ID = new Identifier(SwitchyTeleport.ID, "spawn_point");

	final static String KEY_RESPAWN_POINT = "respawn_point";

	@Nullable private Location respawnLocation = null;

	@Override
	public void updateFromPlayer(PlayerEntity player, @Nullable String nextPreset) {
		ServerPlayerEntity serverPlayer = ((ServerPlayerEntity)player);
		BlockPos respawnPoint = serverPlayer.getSpawnPointPosition();

		if (respawnPoint != null) {
			this.respawnLocation = new Location(
					respawnPoint.getX(), respawnPoint.getY(), respawnPoint.getZ(),
					0, serverPlayer.getSpawnAngle(),
					serverPlayer.getSpawnPointDimension().getValue()
			);
		}
	}

	@Override
	public void applyToPlayer(PlayerEntity player) {
		if (this.respawnLocation != null) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
			serverPlayer.setSpawnPoint(RegistryKey.of(Registry.WORLD_KEY, respawnLocation.getDimensionID()),
					new BlockPos(respawnLocation.getX(), respawnLocation.getY(), respawnLocation.getZ()),
					respawnLocation.getYaw(),
					false, false);
		}
	}

	@Override
	public NbtCompound toNbt() {
		NbtCompound outNbt = new NbtCompound();
		NbtCompound nbtSpawn = new NbtCompound();

		if (this.respawnLocation != null) {
			nbtSpawn.putDouble("x", this.respawnLocation.getX());
			nbtSpawn.putDouble("y", this.respawnLocation.getY());
			nbtSpawn.putDouble("z", this.respawnLocation.getZ());

			nbtSpawn.putFloat("angle", this.respawnLocation.getYaw());

			nbtSpawn.putString("dimension", this.respawnLocation.getDimensionID().toString());
		}

		outNbt.put(KEY_RESPAWN_POINT, nbtSpawn);

		return outNbt;
	}

	@Override
	public void fillFromNbt(NbtCompound nbt) {
		if (nbt.contains(KEY_RESPAWN_POINT, NbtType.COMPOUND)) {
			NbtCompound nbtSpawn = nbt.getCompound(KEY_RESPAWN_POINT);
			if (nbtSpawn.contains("x", NbtType.DOUBLE)) {
				this.respawnLocation = new Location(
						nbtSpawn.getDouble("x"),
						nbtSpawn.getDouble("y"),
						nbtSpawn.getDouble( "z"),
						0,
						nbtSpawn.getFloat("angle"),
						new Identifier(nbtSpawn.getString("dimension"))
				);
			}
		}
	}

	public static void touch() {
	}

	// Runs on touch() - but only once.
	static {
		PresetModuleRegistry.registerModule(ID, SpawnPointCompat::new, false, ModuleImportable.OPERATOR, Set.of(), new TranslatableText("commands.switchy_teleport.module.warn.spawn_point"));
	}
}
