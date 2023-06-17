package xyz.fulmine.switchy_teleport.utils;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.fulmine.switchy_teleport.Location;

public class TeleportUtils {
	public static void teleportPlayer(ServerPlayerEntity player, Location location) {
		player.teleport(
				player.getWorld().getServer().getWorld(RegistryKey.of(RegistryKeys.WORLD, location.dimension())),
				location.x(), location.y(), location.z(),
				location.yaw(), location.pitch()
		);
	}
}
