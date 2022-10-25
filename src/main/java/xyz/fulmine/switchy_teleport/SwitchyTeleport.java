package xyz.fulmine.switchy_teleport;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.fulmine.switchy_teleport.compat.LastLocationCompat;
import xyz.fulmine.switchy_teleport.compat.SpawnPointCompat;

public class SwitchyTeleport implements ModInitializer {
	public static final String ID = "switchy_teleport";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize(ModContainer mod) {
		LastLocationCompat.touch();
		SpawnPointCompat.touch();
	}
}
