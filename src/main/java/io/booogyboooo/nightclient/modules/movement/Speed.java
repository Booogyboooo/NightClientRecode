package io.booogyboooo.nightclient.modules.movement;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Box;
import io.booogyboooo.nightclient.ui.components.options.Slider;

public class Speed extends NightModule {

	public Box shouldSprint = new Box("Sprint", "Custom", false);
	public Box shouldJump = new Box("Jump", "Custom", false);
	public Box shouldAccelerate = new Box("Accelarate", "Custom", false);
	public Slider accelerateSpeed = new Slider("Accel Amt", "Custom", 0.1, 0, 1, 0.05);
	public Slider maxSpeed = new Slider("Max Speed", "Custom", 1, 0, 5, 0.1);
	
	public Speed() {
		super(Type.MOVEMENT, "Speed", "Legit");
		this.addMode("Vannila");
		this.addMode("Vannila 2");
		this.addMode("Custom");
		this.addOption(shouldSprint);
		this.addOption(shouldJump);
		this.addOption(shouldAccelerate);
		this.addOption(accelerateSpeed);
		this.addOption(maxSpeed);
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "postTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "postTick");
	}
	
	@Event(eventType=EventType.POST_TICK)
	public void postTick(EventData data) {
		if (this.getMode().equals("Vannila")) {
			if (mc.player.getVelocity().getX() > 2 || mc.player.getVelocity().getX() < -2 || mc.player.getVelocity().getZ() > 2 || mc.player.getVelocity().getZ() < -2 && !mc.player.isOnGround()) {
				return;
			}
			mc.player.setVelocity(mc.player.getVelocity().getX() * 1.2, mc.player.getVelocity().getY(), mc.player.getVelocity().getZ() * 1.2);
		} else if (this.getMode().equals("Legit")) {
			if (mc.player.isOnGround()) {
				mc.player.jump();
			}
			mc.player.setSprinting(true);
		} else if (this.getMode().equals("Vannila 2")) {
			mc.player.setSprinting(true);
			if (mc.player.isOnGround()) {
				mc.player.jump();
				return;
			}
			double xVelo = mc.player.getVelocity().getX() / 5;
			double zVelo = mc.player.getVelocity().getZ() / 5;
			mc.player.setVelocity(mc.player.getVelocity().add(xVelo, -0.2, zVelo));
		} else if (this.getMode().equals("Custom")) {
			if (Math.sqrt((mc.player.getVelocity().getX() * mc.player.getVelocity().getX()) + (mc.player.getVelocity().getZ() * mc.player.getVelocity().getZ())) >= maxSpeed.getDouble()) {
				return;
			}
			mc.player.setSprinting(shouldSprint.checked());
			if (mc.player.isOnGround() && shouldJump.checked()) {
				mc.player.jump();
			}
			if (shouldAccelerate.checked()) {
				mc.player.setVelocity(mc.player.getVelocity().getX() * (1 + (accelerateSpeed.getDouble())), mc.player.getVelocity().getY(), mc.player.getVelocity().getZ() * (1 + accelerateSpeed.getDouble()));
			}
		}
	}

}
