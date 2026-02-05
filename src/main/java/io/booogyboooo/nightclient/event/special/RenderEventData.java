package io.booogyboooo.nightclient.event.special;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;

public class RenderEventData extends EventData {

	private WorldRenderContext context;

	public RenderEventData(WorldRenderContext context) {
		super(false, EventType.RENDER);
		this.context = context;
	}
	
	public WorldRenderContext getContext() {
		return this.context;
	}
	
}
