package io.booogyboooo.nightclient.modules.render;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;

public class NoOverlay extends NightModule {

	public NoOverlay() {
		super(Type.RENDER, "NoOverlay", "Default");
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "onOverlay");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "onOverlay");
	}
	
	@Event(eventType = EventType.RENDER_OVERLAY)
	public void onOverlay(EventData data) {
		data.cancel(data);
	}

}
