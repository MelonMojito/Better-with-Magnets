package betterwithmagnets;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;

/**
 * Shared magnet logic. Loaded on both client and server, so nothing here may
 * reference client-only classes.
 */
public final class Magnets {

	/** Metadata value that turns a fireball charge into a magnet. */
	public static final int MAGNET_META = 1;

	/** How far away a magnet pulls items, in blocks. */
	public static final double RANGE = 15.0;

	/** Pull strength applied per tick. */
	public static final double STRENGTH = 0.06;

	/**
	 * How often (in ticks) the server resends the position of an item a magnet is pulling,
	 * for viewers who cannot predict the pull themselves. The game default of 20 leaves a
	 * vanilla client dead-reckoning for a whole second before being snapped back.
	 * Everything else keeps the default.
	 */
	public static final int MAGNETIZED_PACKET_DELAY = 1;

	/**
	 * Whether magnets are active. Always true on a server; the client entrypoint
	 * swaps this out for the in-game option so singleplayer can toggle it.
	 */
	public static BooleanSupplier enabled = () -> true;

	private Magnets() {
	}

	public static boolean isEnabled() {
		return enabled.getAsBoolean();
	}

	public static boolean isMagnet(@Nullable ItemStack stack) {
		return stack != null
			&& stack.getItem().equals(Items.AMMO_FIREBALL)
			&& stack.getMetadata() == MAGNET_META;
	}

	public static @Nullable Player closestPlayerWithMagnetToItem(World world, Entity entity){
		Player closestPlayer = null;
		float closestDistance = Float.MAX_VALUE;
		for(Player player : world.players){
			if(!(((PlayerMagnetInterface) player).hasMagnet())){
				continue;
			}
			float distanceTo = player.distanceTo(entity);
			if(distanceTo < closestDistance){
				closestPlayer = player;
				closestDistance = distanceTo;
			}
		}
		return closestPlayer;
	}
}
