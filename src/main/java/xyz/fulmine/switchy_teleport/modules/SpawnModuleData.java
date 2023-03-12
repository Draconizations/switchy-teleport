package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchySerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.SwitchyTeleport;

public class SpawnModuleData implements SwitchySerializable {
	public final static Identifier ID = new Identifier(SwitchyTeleport.ID, "spawn_point");

	public final static String KEY_SPAWN = "respawn_point";

	@Nullable protected Location spawn = null;

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
}
