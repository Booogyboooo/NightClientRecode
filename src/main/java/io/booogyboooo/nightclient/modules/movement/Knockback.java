package io.booogyboooo.nightclient.modules.movement;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.event.special.KnockbackEventData;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Slider;

public class Knockback extends NightModule {

	public Slider multi = new Slider("Multi", "Custom", 1, 0, 10, 0.05);
	
	public Knockback() {
		super(Type.MOVEMENT, "Knockback", "0x");
		this.addMode("Custom");
		this.addOption(multi);
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "onKnockback");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "onKnockback");
	}
	
	@Event(eventType = EventType.KNOCKBACK)
	public void onKnockback(EventData data) {
		KnockbackEventData kb = (KnockbackEventData) data;
		if (this.getMode().equals("0x")) {
			kb.cancel(kb);
		} else if (this.getMode().equals("Custom")) {
			kb.setKnockback(kb, kb.getKnockback().multiply(multi.getDouble()));
		}
	}
	
}