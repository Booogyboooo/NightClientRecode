package io.booogyboooo.nightclient.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.special.PacketEventData;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
	
	@Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;)V", cancellable = true)
	private void onSend(Packet<?> packet, @Nullable ChannelFutureListener callback, CallbackInfo ci) {
		PacketEventData data = new PacketEventData(packet);
        try {
			NightClientRecode.getEventManager().fireEvent(EventType.PACKET, data);
		} catch (Exception e) {

		}
        if (data.isCanceled) {
            ci.cancel();
        }
	}
    
}