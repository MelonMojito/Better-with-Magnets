package betterwithmagnets;

/**
 * Marks a dropped item that a magnet is currently pulling, so the entity tracker can
 * send its position more often than the once-per-second the game normally uses.
 */
public interface MagnetizedItem {

	boolean isMagnetized();

	void setMagnetized(boolean magnetized);
}
