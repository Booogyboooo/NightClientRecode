package io.booogyboooo.nightclient.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.mojang.authlib.GameProfile;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.special.KnockbackEventData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Override
	public void setVelocityClient(Vec3d vec) {
		KnockbackEventData data = new KnockbackEventData(vec.x, vec.y, vec.z);
		try {
			NightClientRecode.getEventManager().fireEvent(EventType.KNOCKBACK, data);
		} catch (Exception e) {

		}
		if (data.isCanceled) {
			return;
		}
		Vec3d kb = data.getKnockback();
		super.setVelocityClient(new Vec3d(kb.x, kb.y, kb.z));
	}

}