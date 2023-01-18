package xyz.fulmine.switchy_teleport.compat;

import folk.sisby.switchy.api.ModuleImportable;
import folk.sisby.switchy.api.PresetModule;
import folk.sisby.switchy.api.PresetModuleRegistry;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.SwitchyTeleport;
import xyz.fulmine.switchy_teleport.utils.TeleportUtils;

import java.util.Set;

public class LastLocationCompat implements PresetModule {
	final static Identifier ID = new Identifier(SwitchyTeleport.ID, "last_location");

	final static String KEY_LAST_LOCATION = "last_location";

	@Nullable private Location lastLocation = null;

	@Override
	public void updateFromPlayer(PlayerEntity player, @Nullable String nextPreset) {
		ServerPlayerEntity serverPlayer = ((ServerPlayerEntity)player);

		this.lastLocation = new Location(
				serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
				serverPlayer.getPitch(), serverPlayer.getYaw(),
				serverPlayer.getWorld().getRegistryKey().getValue()
		);
	}

	@Override
	public void applyToPlayer(PlayerEntity player) {

		if (this.lastLocation != null) {
			TeleportUtils.teleportPlayer(player, this.lastLocation);
		}
	}

	@Override
	public NbtCompound toNbt() {
		NbtCompound outNbt = new NbtCompound();

		NbtCompound nbtLast = new NbtCompound();

		if (this.lastLocation != null) {
			nbtLast.putDouble("x", this.lastLocation.getX());
			nbtLast.putDouble("y", this.lastLocation.getY());
			nbtLast.putDouble("z", this.lastLocation.getZ());

			nbtLast.putFloat("pitch", this.lastLocation.getPitch());
			nbtLast.putFloat("yaw", this.lastLocation.getYaw());

			nbtLast.putString("dimension", this.lastLocation.getDimensionID().toString());
		}

		outNbt.put(KEY_LAST_LOCATION, nbtLast);

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
	}

	public static void touch() {
	}

	// Runs on touch() - but only once.
	static {
		PresetModuleRegistry.registerModule(ID, LastLocationCompat::new, false, ModuleImportable.OPERATOR, Set.of(), Text.translatable("commands.switchy_teleport.module.warn.location"));
	}
}
