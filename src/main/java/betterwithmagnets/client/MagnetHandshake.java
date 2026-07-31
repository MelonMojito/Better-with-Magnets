package betterwithmagnets.client;

import betterwithmagnets.BetterWithMagnets;
import betterwithmagnets.MagnetAvailability;
import betterwithmagnets.net.MagnetQueryMessage;
import net.minecraft.core.entity.player.Player;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.lang.ref.WeakReference;

public final class MagnetHandshake {

	private static final int JOIN_DELAY_TICKS = 40;
	private static final int REPLY_TIMEOUT_TICKS = 200;
	private static final int RETRY_INTERVAL_TICKS = 100;

	private static WeakReference<Player> lastPlayer = new WeakReference<>(null);
	private static int ticksSinceJoin;
	private static int ticksSinceAsked = -1;
	private static boolean unsupported;

	private MagnetHandshake() {
	}

	public static void tick(Player player) {
		if (player != lastPlayer.get()) {
			lastPlayer = new WeakReference<>(player);
			ticksSinceJoin = 0;
			ticksSinceAsked = -1;
			unsupported = false;
			MagnetAvailability.reset();
		}

		if (MagnetAvailability.isServerSupported() || unsupported) {
			return;
		}

		if (ticksSinceJoin < JOIN_DELAY_TICKS) {
			ticksSinceJoin++;
			return;
		}

		if (ticksSinceAsked >= 0) {
			ticksSinceAsked++;
			if (ticksSinceAsked > REPLY_TIMEOUT_TICKS) {
				unsupported = true;
				BetterWithMagnets.LOGGER.info("Server did not answer the magnet handshake; magnets are disabled here.");
				return;
			}
			if (ticksSinceAsked % RETRY_INTERVAL_TICKS != 0) {
				return;
			}
		}

		try {
			//a server without the mod ignores the payload instead of kicking us
			NetworkHandler.sendCompatibilityToServer(new MagnetQueryMessage());
			ticksSinceAsked = 0;
		} catch (Exception e) {
			unsupported = true;
			BetterWithMagnets.LOGGER.info("Server did not accept the magnet handshake; magnets are disabled here.");
		}
	}
}
