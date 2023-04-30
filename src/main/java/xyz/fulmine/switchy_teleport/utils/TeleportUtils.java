package xyz.fulmine.switchy_teleport.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import xyz.fulmine.switchy_teleport.Location;

public class TeleportUtils {
	public static void teleportPlayer(ServerPlayerEntity player, Location location) {
		player.teleport(
				player.getWorld().getServer().getWorld(RegistryKey.of(Registry.WORLD_KEY, location.dimension())),
				location.x(), location.y(), location.z(),
				location.yaw(), location.pitch()
		);
	}
}
