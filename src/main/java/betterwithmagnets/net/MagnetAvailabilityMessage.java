package betterwithmagnets.net;

import betterwithmagnets.MagnetAvailability;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class MagnetAvailabilityMessage implements NetworkMessage {

	private boolean available;

	//required: the network handler constructs instances reflectively when decoding
	public MagnetAvailabilityMessage() {
	}

	public MagnetAvailabilityMessage(boolean available) {
		this.available = available;
	}

	@Override
	public void encodeToUniversalPacket(UniversalPacket packet) {
		packet.writeBoolean(available);
	}

	@Override
	public void decodeFromUniversalPacket(UniversalPacket packet) {
		available = packet.readBoolean();
	}

	@Override
	public void handle(NetworkContext context) {
		MagnetAvailability.setServerSupported(available);
	}
}
