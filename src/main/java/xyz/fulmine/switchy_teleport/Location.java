package xyz.fulmine.switchy_teleport;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class Location implements IDirectionalDimensionPoint{
	final double x, y, z;
	final float pitch, yaw;
	final Identifier dimension;

	public Location(double x, double y, double z, float pitch, float yaw, Identifier dimension) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.dimension = dimension;
	}

	public Location(Vec3d coordinates, float pitch, float yaw, Identifier dimension) {
		this.x = coordinates.getX();
		this.y = coordinates.getY();
		this.z = coordinates.getZ();
		this.pitch = pitch;
		this.yaw = yaw;
		this.dimension = dimension;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

	@Override
	public float getYaw() {
		return yaw;
	}

	@Override
	public Identifier getDimensionID() {
		return dimension;
	}

	@Override
	public Vec3d getCoordinates() {
		return new Vec3d(x, y, z);
	}
}
