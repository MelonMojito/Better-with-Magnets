package betterwithmagnets.client;

import betterwithmagnets.Magnets;
import betterwithmagnets.PlayerMagnetInterface;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemModelMagnet extends ItemModelStandard {
	protected IconCoordinate magnet = TextureRegistry.getTexture("betterwithmagnets:item/magnet");
	protected IconCoordinate magnet_activated = TextureRegistry.getTexture("betterwithmagnets:item/magnet_activated");

	public ItemModelMagnet(Item item, String namespace) {
		super(item, namespace);
	}

	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
		if(itemStack.getMetadata() == Magnets.MAGNET_META) {
			if(entity instanceof Player){
				if(((PlayerMagnetInterface) entity).hasMagnet()){
					return this.magnet_activated;
				}
			}
			return this.magnet;
		} else {
			return super.getIcon(entity, itemStack);
		}
	}
}
