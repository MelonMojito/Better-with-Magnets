package betterwithmagnets.mixin;

import betterwithmagnets.MagnetizedItem;
import betterwithmagnets.Magnets;
import betterwithmagnets.PlayerMagnetInterface;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.server.entity.EntityTrackerEntryImpl;
import net.minecraft.server.entity.player.PlayerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;

@Mixin(value = EntityTrackerEntryImpl.class, remap = false)
public abstract class EntityTrackerEntryImplMixin {

	@Shadow
	public Entity trackedEntity;

	@Shadow
	public int movementPacketDelay;

	@Shadow
	public Set<PlayerServer> trackedPlayers;

	@Unique
	private int defaultDelay = -1;

	@Inject(method = "tick", at = @At("HEAD"))
	private void magnetPacketRate(List<Player> players, CallbackInfo ci) {
		if (!(trackedEntity instanceof MagnetizedItem)) return;

		if (defaultDelay < 0) {
			defaultDelay = movementPacketDelay;
		}

		boolean boost = ((MagnetizedItem) trackedEntity).isMagnetized()
			&& betterwithmagnets$anyViewerNeedsUpdates();

		movementPacketDelay = boost ? Magnets.MAGNETIZED_PACKET_DELAY : defaultDelay;
	}

	/** True if at least one player watching this item cannot predict the pull itself. */
	@Unique
	private boolean betterwithmagnets$anyViewerNeedsUpdates() {
		if (trackedPlayers == null || trackedPlayers.isEmpty()) return false;
		for (PlayerServer viewer : trackedPlayers) {
			if (!((PlayerMagnetInterface) viewer).hasMod()) {
				return true;
			}
		}
		return false;
	}
}
