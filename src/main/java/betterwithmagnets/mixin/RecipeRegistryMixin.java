package betterwithmagnets.mixin;

import betterwithmagnets.MagnetAvailability;
import betterwithmagnets.Magnets;
import net.minecraft.core.data.registry.recipe.RecipeRegistry;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RecipeRegistry.class, remap = false)
public class RecipeRegistryMixin {

	@Inject(method = "findMatchingRecipe", at = @At("RETURN"), cancellable = true)
	void blockUnavailableMagnet(ContainerCrafting inventorycrafting, CallbackInfoReturnable<ItemStack> cir) {
		if (!Magnets.isMagnet(cir.getReturnValue())) return;
		if (MagnetAvailability.isUsable()) return;
		cir.setReturnValue(null);
	}
}
