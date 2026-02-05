package io.booogyboooo.nightclient.modules.movement;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.util.MoveUtil;

public class Flight extends NightModule {
	
	private boolean canFlyFirst = false;
	public int fJumps;
	private int yLevel;
	
	public Flight() {
		super(Type.MOVEMENT, "Flight", "Vannila");
		this.addMode("Fireball");
		this.addMode("AirHop");
	}
	
	@Override
	public void onEnable() {
		this.canFlyFirst = mc.player.getAbilities().allowFlying;
		yLevel = mc.player.getBlockY();
		fJumps = 0;
		this.registerEvent(this, "postTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "postTick");
		mc.player.getAbilities().allowFlying = this.canFlyFirst;
	}
	
	@Event(eventType = EventType.POST_TICK)
	public void postTick(EventData data) {
		if (this.getMode().equals("Vannila")) {
			mc.player.getAbilities().allowFlying = true;
			return;
		} else {
			mc.player.getAbilities().allowFlying = this.canFlyFirst;
		}
		if (this.getMode().equals("Fireball")) {
			if(mc.player.getVelocity().getY() < -0.55) {
				MoveUtil.onGround(true);
				mc.player.jump();
				this.fJumps++;
				if (this.fJumps > 2) {
					this.toggle();
				}
				mc.player.setVelocity(mc.player.getVelocity().getX() * 0.7, mc.player.getVelocity().getY(), mc.player.getVelocity().getZ() * 0.7);
			}
			return;
		} else if (this.getMode().equals("AirHop")) {
			if(mc.player.getY() < yLevel + 0.05) {
				MoveUtil.onGround(true);
				mc.player.jump();
			}
		}
	}

}
