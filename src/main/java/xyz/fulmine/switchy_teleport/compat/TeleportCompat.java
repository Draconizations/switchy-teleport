package xyz.fulmine.switchy_teleport.compat;

import folk.sisby.switchy.api.PresetModule;
import folk.sisby.switchy.api.PresetModuleRegistry;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.SwitchyTeleport;
import xyz.fulmine.switchy_teleport.utils.TeleportUtils;

public class TeleportCompat implements PresetModule {
	final static Identifier ID = new Identifier(SwitchyTeleport.ID, "teleport");
	final static boolean isDefault = false;

	final static String KEY_LAST_LOCATION = "last_location";
	final static String KEY_RESPAWN_POINT = "respawn_point";

	@Nullable private Location lastLocation = null;
	@Nullable private Location respawnLocation = null;

	@Override
	public void updateFromPlayer(PlayerEntity player) {
		ServerPlayerEntity serverPlayer = ((ServerPlayerEntity)player);

		this.lastLocation = new Location(
				serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
				serverPlayer.getPitch(), serverPlayer.getYaw(),
				serverPlayer.getWorld().getRegistryKey().getValue()
		);

		BlockPos respawnPoint = serverPlayer.getSpawnPointPosition();

		if (serverPlayer.isSpawnPointSet()) {
			this.respawnLocation = new Location(
					respawnPoint.getX(), respawnPoint.getY(), respawnPoint.getZ(),
					0, serverPlayer.getSpawnAngle(),
					serverPlayer.getSpawnPointDimension().getRegistry()
			);
		}
	}

	@Override
	public void applyToPlayer(PlayerEntity player) {

		if (this.lastLocation != null) {
			TeleportUtils.teleportPlayer(player, this.lastLocation);
		}

		if (this.respawnLocation != null) {
			ServerPlayerEntity serverPlayer = ((ServerPlayerEntity)player);
			serverPlayer.setSpawnPoint(RegistryKey.of(Registry.WORLD_KEY,respawnLocation.getDimensionID()),
					new BlockPos(respawnLocation.getX(), respawnLocation.getY(), respawnLocation.getZ()),
					respawnLocation.getYaw(),
					true, false);
		}
	}

	@Override
	public NbtCompound toNbt() {
		NbtCompound outNbt = new NbtCompound();

		NbtCompound nbtLast = new NbtCompound();
		NbtCompound nbtSpawn = new NbtCompound();

		if (this.lastLocation != null) {
			nbtLast.putDouble("x", this.lastLocation.getX());
			nbtLast.putDouble("y", this.lastLocation.getY());
			nbtLast.putDouble("z", this.lastLocation.getZ());

			nbtLast.putFloat("pitch", this.lastLocation.getPitch());
			nbtLast.putFloat("yaw", this.lastLocation.getYaw());

			nbtLast.putString("dimension", this.lastLocation.getDimensionID().toString());
		}

		if (this.respawnLocation != null) {
			nbtSpawn.putDouble("x", this.respawnLocation.getX());
			nbtSpawn.putDouble("y", this.respawnLocation.getY());
			nbtSpawn.putDouble("z", this.respawnLocation.getZ());

			nbtSpawn.putFloat("angle", this.respawnLocation.getYaw());

			nbtSpawn.putString("dimension", this.respawnLocation.getDimensionID().toString());
		}


		outNbt.put(KEY_LAST_LOCATION, nbtLast);
		outNbt.put(KEY_RESPAWN_POINT, nbtSpawn);

		return outNbt;
	}

	@Override
	public void fillFromNbt(NbtCompound nbt) {
		if (nbt.contains(KEY_LAST_LOCATION, NbtType.COMPOUND)) {
			NbtCompound nbtLast = nbt.getCompound(KEY_LAST_LOCATION);
			if (nbtLast.contains("x", NbtType.DOUBLE)) {
				this.lastLocation = new Location(
						nbtLast.getDouble("x"),
						nbtLast.getDouble("y"), nbtLast.getDouble( "z"),
						nbtLast.getFloat("pitch"),
						nbtLast.getFloat("yaw"),
						new Identifier(nbtLast.getString("dimension"))
				);
			}
		}

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

	@Override
	public Identifier getId() {
		return ID;
	}

	@Override
	public MutableText getDisableConfirmation() {
		return net.minecraft.text.Text.translatable("commands.switchy_teleport.module.warn.teleport");
	}

	@Override
	public boolean isDefault() {
		return isDefault;
	}

	public static void touch() {
	}

	// Runs on touch() - but only once.
	static {
		PresetModuleRegistry.registerModule(ID, TeleportCompat::new);
	}
}
