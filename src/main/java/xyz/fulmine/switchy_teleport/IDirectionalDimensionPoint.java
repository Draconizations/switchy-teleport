package xyz.fulmine.switchy_teleport;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public interface IDirectionalDimensionPoint {
	double getX();
	double getY();
	double getZ();

	float getPitch();
	float getYaw();

	Identifier getDimensionID();

	Vec3d getCoordinates();
}
