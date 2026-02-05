package io.booogyboooo.nightclient.modules.misc;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Slider;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class FastBreak extends NightModule {

	public Slider hasteLevel = new Slider("level", "Custom", 1, 1, 10, 1);
	public int lvl = 0;
	
	public FastBreak() {
		super(Type.MISC, "FastBreak", "Haste I");
		this.addMode("Haste II");
		this.addMode("Custom");
		this.addOption(hasteLevel);
	}
	
	@Override
	public void onEnable() {
		updateLvl();
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		updateLvl();
		mc.player.removeStatusEffect(StatusEffects.HASTE);
		this.unregisterEvent(this, "preTick");
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		if (this.getMode().equals("Custom")) {
			lvl = ((Double) hasteLevel.getDouble()).intValue() - 1;
		}
		mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, Integer.MAX_VALUE, lvl));
	}
	
	@Override
	public void modeChange(String mode) {
		mc.player.removeStatusEffect(StatusEffects.HASTE);
		updateLvl();
	}
	
	private void updateLvl() {
		if (this.getMode().equals("Haste I")) {
			lvl = 0;
		} else if (this.getMode().equals("Haste II")) {
			lvl = 1;
		}
	}

}
