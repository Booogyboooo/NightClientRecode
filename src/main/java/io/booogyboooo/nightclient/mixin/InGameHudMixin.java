package io.booogyboooo.nightclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.special.RenderHUDEventData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	
	@Inject(method = "render", at = @At("HEAD"))
    private void onRender(DrawContext drawContext, RenderTickCounter tickDelta, CallbackInfo ci) {
		try {
			NightClientRecode.getEventManager().fireEvent(EventType.RENDER_HUD, new RenderHUDEventData(drawContext, tickDelta));
		} catch (Exception e) {

		}
	}
	
}
