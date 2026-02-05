package io.booogyboooo.nightclient.modules.movement;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Box;

public class Sprint extends NightModule {
	
	public Box requireMovement = new Box("Movement", "Custom", false);
	public Box ignoreSlowdowns = new Box("No Slow", "Custom", false);
	
	public Sprint() {
		super(Type.MOVEMENT, "Sprint", "Vannila");
		this.addMode("Grim");
		this.addMode("Custom");
		this.addOption(requireMovement);
		this.addOption(ignoreSlowdowns);
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		if (this.getMode().equals("Vannila")) {
			mc.player.setSprinting(true);
		} else if (this.getMode().equals("Grim") && mc.player.getVelocity().x != 0 && mc.player.getVelocity().z != 0 && !mc.player.shouldSlowDown()) {
			mc.player.setSprinting(true);
		} else if (this.getMode().equals("Custom")) {
			if (requireMovement.checked()) {
				if (mc.player.getVelocity().x == 0 && mc.player.getVelocity().z == 0) {
					return;
				}
			}
			if (!ignoreSlowdowns.checked() && mc.player.shouldSlowDown()) {
				return;
			}
			mc.player.setSprinting(true);
		}
	}

}