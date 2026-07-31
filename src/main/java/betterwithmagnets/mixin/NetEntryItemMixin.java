package betterwithmagnets.mixin;

import betterwithmagnets.Magnets;
import net.minecraft.core.net.entity.entries.NetEntryItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NetEntryItem.class, remap = false)
public class NetEntryItemMixin {

	@Inject(method = "getMovementPacketDelay", at = @At("HEAD"), cancellable = true)
	void magnetMovementPacketDelay(CallbackInfoReturnable<Integer> cir){
		if (Magnets.isEnabled()) {
			cir.setReturnValue(Magnets.MOVEMENT_PACKET_DELAY);
		}
	}
}
