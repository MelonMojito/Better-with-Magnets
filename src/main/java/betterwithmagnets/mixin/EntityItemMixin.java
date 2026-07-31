package betterwithmagnets.mixin;

import betterwithmagnets.MagnetAvailability;
import betterwithmagnets.MagnetizedItem;
import betterwithmagnets.Magnets;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityItem.class, remap = false)
public abstract class EntityItemMixin extends Entity implements MagnetizedItem {

	@Unique
	private boolean magnetized = false;

	//ignore, constructor is useless
	public EntityItemMixin(@Nullable World world) {
		super(world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	void tick(CallbackInfo ci){
		//Both sides run this. The server stays authoritative, but a client running the mod
		//predicts the same pull locally instead of waiting for the next position packet, so
		//the item glides at framerate rather than stepping between server updates.
		//The client only predicts once the server has confirmed it runs magnets, otherwise
		//it would drag items the server never moves, and they would rubber-band back.
		if(!MagnetAvailability.isActive(this.world)) {
			magnetized = false;
			return;
		}

		Player closestPlayer = Magnets.closestPlayerWithMagnetToItem(this.world, this);
		if(closestPlayer != null && closestPlayer.distanceTo(this) < Magnets.RANGE){
			//magnet stuff
			Vec3 item = Vec3.getTempVec3(this.x, this.y, this.z);
			Vec3 target = Vec3.getTempVec3(closestPlayer.x, closestPlayer.y + closestPlayer.getHeadHeight() - 1.05, closestPlayer.z);

			Vec3 normal = item.vectorTo(target).normalize().scale(Magnets.STRENGTH).add(0,0.032,0);
			this.xd += normal.x;
			this.yd += normal.y;
			this.zd += normal.z;
			magnetized = true;
		} else {
			magnetized = false;
		}
	}

	@Override
	public boolean isMagnetized() {
		return magnetized;
	}

	@Override
	public void setMagnetized(boolean magnetized) {
		this.magnetized = magnetized;
	}
}
