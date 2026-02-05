package io.booogyboooo.nightclient.modules.movement;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Slider;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class JumpBoost extends NightModule {

	public Slider jumpBoostLevel = new Slider("level", "Effect", 1, 1, 10, 1);
	
	public JumpBoost() {
		super(Type.MOVEMENT, "JumpBoost", "Effect");
		this.addOption(jumpBoostLevel);
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
		mc.player.removeStatusEffect(StatusEffects.JUMP_BOOST);
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		Double level = jumpBoostLevel.getDouble();
		mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,Integer.MAX_VALUE, level.intValue() - 1));
	}

}
