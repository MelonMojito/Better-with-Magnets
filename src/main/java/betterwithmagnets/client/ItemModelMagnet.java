package betterwithmagnets.client;

import betterwithmagnets.Magnets;
import betterwithmagnets.PlayerMagnetInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.DisplayPos;

public class ItemModelMagnet extends ItemModelStandard {
	protected IconCoordinate magnet = TextureRegistry.getTexture("betterwithmagnets:item/magnet");
	protected IconCoordinate magnet_activated = TextureRegistry.getTexture("betterwithmagnets:item/magnet_activated");

	public ItemModelMagnet(Item item, String namespace) {
		super(item, namespace);
		setFullBright();

		setDisplayPos("head", flipped(DEFAULT_ITEM_HEAD));
		setDisplayPos("thirdperson_righthand", flipped(DEFAULT_ITEM_THIRD_PERSON_RIGHT_HAND));
		setDisplayPos("thirdperson_lefthand", flipped(DEFAULT_ITEM_THIRD_PERSON_LEFT_HAND));
		setDisplayPos("firstperson_righthand", flipped(DEFAULT_ITEM_FIRST_PERSON_RIGHT_HAND));
		setDisplayPos("firstperson_lefthand", flipped(DEFAULT_ITEM_FIRST_PERSON_LEFT_HAND));
	}

	/** The same placement, turned to face the other way. */
	private static DisplayPos flipped(DisplayPos pos) {
		return new DisplayPos(
			pos.tx, pos.ty, pos.tz,
			pos.rx, pos.ry + 180.0f, pos.rz,
			pos.sx, pos.sy, pos.sz
		);
	}

	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
		if(itemStack.getMetadata() == Magnets.MAGNET_META) {
			if(holderHasMagnet(entity)){
				return this.magnet_activated;
			}
			return this.magnet;
		} else {
			return super.getIcon(entity, itemStack);
		}
	}

	/**
	 * Inventory and hotbar slots render with no entity, so fall back to the local player.
	 * Without this the magnet only lit up while held and looked inert everywhere else.
	 */
	private static boolean holderHasMagnet(@Nullable Entity entity) {
		Player player = entity instanceof Player ? (Player) entity : localPlayer();
		return player != null && ((PlayerMagnetInterface) player).hasMagnet();
	}

	private static @Nullable Player localPlayer() {
		Minecraft minecraft = Minecraft.getMinecraft();
		return minecraft.thePlayer;
	}
}
