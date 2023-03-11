package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchySerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.SwitchyTeleport;

public class LocationModuleData implements SwitchySerializable {
	public final static Identifier ID = new Identifier(SwitchyTeleport.ID, "last_location");

	public final static String KEY_LOCATION = "last_location";

	@Nullable protected Location location = null;

	@Override
	public NbtCompound toNbt() {
		NbtCompound outNbt = new NbtCompound();

		NbtCompound nbtLast = new NbtCompound();

		if (this.location != null) {
			nbtLast.putDouble("x", this.location.getX());
			nbtLast.putDouble("y", this.location.getY());
			nbtLast.putDouble("z", this.location.getZ());

			nbtLast.putFloat("pitch", this.location.getPitch());
			nbtLast.putFloat("yaw", this.location.getYaw());

			nbtLast.putString("dimension", this.location.getDimensionID().toString());
		}

		outNbt.put(KEY_LOCATION, nbtLast);

		return outNbt;
	}

	@Override
	public void fillFromNbt(NbtCompound nbt) {
		if (nbt.contains(KEY_LOCATION, NbtElement.COMPOUND_TYPE)) {
			NbtCompound nbtLast = nbt.getCompound(KEY_LOCATION);
			if (nbtLast.contains("x", NbtElement.DOUBLE_TYPE)) {
				this.location = new Location(
						nbtLast.getDouble("x"),
						nbtLast.getDouble("y"), nbtLast.getDouble("z"),
						nbtLast.getFloat("pitch"),
						nbtLast.getFloat("yaw"),
						new Identifier(nbtLast.getString("dimension"))
				);
			}
		}
	}
}
