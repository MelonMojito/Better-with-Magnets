package betterwithmagnets;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.world.World;

public final class MagnetAvailability {

	private static final boolean DEDICATED_SERVER =
		FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;

	private static volatile boolean serverSupported = false;

	private MagnetAvailability() {
	}

	/** Set from the server's handshake reply. */
	public static void setServerSupported(boolean supported) {
		serverSupported = supported;
	}

	public static boolean isServerSupported() {
		return serverSupported;
	}

	/** Called when joining a world, so a modless server can't inherit the last one's answer. */
	public static void reset() {
		serverSupported = false;
	}

	/**
	 * For code with no world to hand, such as looking up a crafting result.
	 */
	public static boolean isUsable() {
		if (!Magnets.isEnabled()) return false;
		return DEDICATED_SERVER || serverSupported;
	}

	/**
	 * For code that knows which side it is on. The server half of singleplayer is the
	 * authority and does not wait on the handshake.
	 */
	public static boolean isActive(World world) {
		if (!Magnets.isEnabled()) return false;
		return !world.isClientSide || serverSupported;
	}
}
