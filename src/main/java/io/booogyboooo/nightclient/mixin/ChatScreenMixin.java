package io.booogyboooo.nightclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.special.ChatEventData;
import net.minecraft.client.gui.screen.ChatScreen;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
	@Inject(at = @At("HEAD"), method = "sendMessage(Ljava/lang/String;Z)V", cancellable = true)
	public void onSendMessage(String message, boolean addToHistory, CallbackInfo ci) {
		ChatEventData chatEvent = new ChatEventData(message);
		try {
			NightClientRecode.getEventManager().fireEvent(EventType.CHAT, chatEvent);
		} catch (Exception e) {}
		if (chatEvent.isCanceled) {
			ci.cancel();
			return;
		}
	}
}