package io.booogyboooo.nightclient.modules.combat;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.util.MoveUtil;
import net.minecraft.util.hit.EntityHitResult;

public class Criticals extends NightModule {

	public Criticals() {
		super(Type.COMBAT, "Criticals", "Packet");
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "onLeftClick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "onLeftClick");
	}
	
	@Event(eventType = EventType.LEFT_CLICK)
	public void onLeftClick(EventData data) {
		if (!(this.isToggled())) {
			return;
		}
		
		if(!(mc.crosshairTarget instanceof EntityHitResult)) {
			return;
		}
		
		if (this.getMode().equals("Packet")) {
			MoveUtil.move(mc.player.getX(), mc.player.getY() + 0.0625, mc.player.getZ(), true);
			MoveUtil.move(mc.player.getX(), mc.player.getY() + 0, mc.player.getZ(), false);
			MoveUtil.move(mc.player.getX(), mc.player.getY() + 1.1e-5, mc.player.getZ(), false);
			MoveUtil.move(mc.player.getX(), mc.player.getY() + 0, mc.player.getZ(), false);
		}
	}

}
