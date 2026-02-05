package io.booogyboooo.nightclient.modules.misc;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.util.BlockPlaceUtil;
import io.booogyboooo.nightclient.util.MoveUtil;
import io.booogyboooo.nightclient.util.PlayerPosUtil;

public class NoFall extends NightModule {

	public NoFall() {
		super(Type.MISC, "NoFall", "Vannila");
		this.addMode("AirPlace");
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
	}
	
	@Event(eventType = EventType.PRE_TICK, priority = -1)
	public void preTick(EventData data) {
		if (this.getMode().equals("Vannila")) {
			MoveUtil.onGround(true);
			return;
		} else if (this.getMode().equals("AirPlace")) {
			if (mc.player.fallDistance > 2.5) {
				BlockPlaceUtil.placeBlock(PlayerPosUtil.getBlockUnderPlayer(true));
			}
		}
	}

}