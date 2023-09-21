package xyz.fulmine.switchy_teleport.modules;

import folk.sisby.switchy.api.SwitchySerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import xyz.fulmine.switchy_teleport.Location;
import xyz.fulmine.switchy_teleport.SwitchyTeleport;

public class LocationModuleData implements SwitchySerializable {
	public final static Identifier ID = new Identifier(SwitchyTeleport.ID, "last_location");
	public static final String KEY_LOCATION = "last_location";

	public @Nullable Location location;

	@Override
	public NbtCompound toNbt() {
		NbtCompound outNbt = new NbtCompound();
		if (location != null) outNbt.put(KEY_LOCATION, location.toNbt());
		return outNbt;
	}

	@Override
	public void fillFromNbt(NbtCompound nbt) {
		if (nbt.contains(KEY_LOCATION)) location = Location.fromNbt(nbt.getCompound(KEY_LOCATION));
	}
}
