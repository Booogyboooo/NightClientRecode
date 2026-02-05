package io.booogyboooo.nightclient.event.special;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class RenderHUDEventData extends EventData {

	private DrawContext drawContext;
	private RenderTickCounter tickDelta;

	public RenderHUDEventData(DrawContext drawContext, RenderTickCounter tickDelta) {
		super(false, EventType.RENDER_HUD);
		this.drawContext = drawContext;
		this.tickDelta = tickDelta;
	}
	
	public DrawContext getContext() {
		return this.drawContext;
	}
	
	public RenderTickCounter getDelta() {
		return this.tickDelta;
	}

}