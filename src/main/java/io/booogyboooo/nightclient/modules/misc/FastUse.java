package io.booogyboooo.nightclient.modules.misc;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Slider;
import net.minecraft.client.MinecraftClient;

public class FastUse extends NightModule {
	
	public Slider delay = new Slider("Delay", "Default", 0, 0, 4, 1);
	
	public FastUse() {
		super(Type.MISC, "FastUse", "Default");
		this.addOption(delay);
	}
	
	public void onEnable() {
		this.registerEvent(this, "preTick");
	}
	
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData event) {
		if (MinecraftClient.getInstance().itemUseCooldown > delay.getInteger()) {
			MinecraftClient.getInstance().itemUseCooldown = delay.getInteger();
		}
	}

}
