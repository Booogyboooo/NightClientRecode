package io.booogyboooo.nightclient.modules.misc;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;

public class BoxEscape extends NightModule {

	public BoxEscape() {
		super(Type.MISC, "BoxEscape", "Vulcan");
	}
	
	@Override
	public void onEnable() {
		mc.currentScreen.close();
		mc.player.addVelocity(0, 10, 0);
		this.registerEvent(this, "postTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "postTick");
	}
	
	@Event(eventType = EventType.POST_TICK)
	public void postTick(EventData data) {
		if (mc.player.getVelocity().y < 0) {
			mc.player.setPos(mc.player.getX(), mc.player.getY() + 8, mc.player.getZ());
			this.toggle(false);
		}
	}
	
}