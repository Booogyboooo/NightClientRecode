package io.booogyboooo.nightclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    @Inject(method = "renderOverlays", at = @At("HEAD"), cancellable = true)
    private void onRenderOverlays(boolean sleeping, float tickProgress, OrderedRenderCommandQueue queue, CallbackInfo ci) {
    	EventData data = new EventData(true, EventType.RENDER_OVERLAY);
    	try {
			NightClientRecode.getEventManager().fireEvent(EventType.RENDER_OVERLAY, data);
		} catch (Exception e) {

		}
    	if (data.isCanceled) {
        	ci.cancel();
    	}
    }
}