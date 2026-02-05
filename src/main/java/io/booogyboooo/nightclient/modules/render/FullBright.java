package io.booogyboooo.nightclient.modules.render;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class FullBright extends NightModule {

	public FullBright() {
		super(Type.RENDER, "FullBright", "Effect");
	}
	
	@Override
	public void onEnable() {
		mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2147483647, 255));
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
		this.unregisterEvent(this, "preTick");
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2147483647, 255));
	}

}
