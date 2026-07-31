package betterwithmagnets.mixin;

import betterwithmagnets.MagnetAvailability;
import betterwithmagnets.Magnets;
import betterwithmagnets.PlayerMagnetInterface;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinMagnet implements PlayerMagnetInterface {

	@Shadow
	public ContainerInventory inventory;

	@Shadow
	public abstract ItemStack getHeldItem();

	@Unique
	boolean hasMagnet = false;

	@Inject(method = "tick", at = @At("TAIL"))
	void tick(CallbackInfo ci){
		if (!MagnetAvailability.isActive(((Player) (Object) this).world)) {
			hasMagnet = false;
			return;
		}

		//a magnet counts if it is worn on the head or held in hand
		hasMagnet = Magnets.isMagnet(this.inventory.armorItemInSlot(HumanArmorShape.HEAD))
			|| Magnets.isMagnet(this.getHeldItem());
	}

	@Override
	public boolean hasMagnet() {
		return hasMagnet;
	}
}
