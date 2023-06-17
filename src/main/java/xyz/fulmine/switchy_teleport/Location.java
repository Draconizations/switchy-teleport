package xyz.fulmine.switchy_teleport;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Nullable;

public record Location(double x, double y, double z, float pitch, float yaw, Identifier dimension, @Nullable Boolean setSpawn) {
	public static final String KEY_X = "x";
	public static final String KEY_Y = "y";
	public static final String KEY_Z = "z";
	public static final String KEY_PITCH = "pitch";
	public static final String KEY_YAW = "yaw";
	public static final String KEY_DIMENSION = "dimension";
	public static final String KEY_SET_SPAWN = "setSpawn";

	public Location(Vec3d coordinates, float pitch, float yaw, Identifier dimension, @Nullable Boolean setSpawn) {
		this(coordinates.getX(), coordinates.getY(), coordinates.getZ(), pitch, yaw, dimension, setSpawn);
	}

	public Vec3d getCoordinates() {
		return new Vec3d(x, y, z);
	}

	public Vec3i getRoundedCoordinates() {
		return new Vec3i((int) Math.round(x), (int) Math.round(y), (int) Math.round(z));
	}

	public NbtCompound toNbt() {
		NbtCompound outNbt = new NbtCompound();

		outNbt.putDouble(KEY_X, x);
		outNbt.putDouble(KEY_Y, y);
		outNbt.putDouble(KEY_Z, z);

		outNbt.putFloat(KEY_PITCH, pitch);
		outNbt.putFloat(KEY_YAW, yaw);

		outNbt.putString(KEY_DIMENSION, dimension.toString());

		if (setSpawn != null) outNbt.putBoolean(KEY_SET_SPAWN, setSpawn);

		return outNbt;
	}

	public static Location fromNbt(NbtCompound nbt) {
		return new Location(
				nbt.getDouble(KEY_X),
				nbt.getDouble(KEY_Y),
				nbt.getDouble(KEY_Z),
				nbt.getFloat(KEY_PITCH),
				nbt.getFloat(KEY_YAW),
				new Identifier(nbt.getString(KEY_DIMENSION)),
				nbt.contains(KEY_SET_SPAWN) ? nbt.getBoolean(KEY_SET_SPAWN) : null
		);
	}
}
