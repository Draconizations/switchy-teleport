package xyz.fulmine.switchy_teleport.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import xyz.fulmine.switchy_teleport.Location;

public class TeleportUtils {

	public static void teleportPlayer(PlayerEntity player, Location location) {
		ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;

		serverPlayer.teleport(
				serverPlayer.getWorld().getServer().getWorld(RegistryKey.of(Registry.WORLD_KEY, location.getDimensionID())),
				location.getX(), location.getY(), location.getZ(),
				location.getYaw(), location.getPitch()
		);
	}
}
