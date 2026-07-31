package betterwithmagnets.net;

import betterwithmagnets.Magnets;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class MagnetQueryMessage implements NetworkMessage {

	public MagnetQueryMessage() {
	}

	@Override
	public void encodeToUniversalPacket(@NotNull UniversalPacket packet) {
	}

	@Override
	public void decodeFromUniversalPacket(@NotNull UniversalPacket packet) {
	}

	@Override
	public void handle(NetworkContext context) {
		if (context.player == null) return;
		NetworkHandler.sendCompatibilityToPlayer(context.player, new MagnetAvailabilityMessage(Magnets.isEnabled()));
	}
}
