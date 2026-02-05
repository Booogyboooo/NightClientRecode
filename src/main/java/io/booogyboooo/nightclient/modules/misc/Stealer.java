package io.booogyboooo.nightclient.modules.misc;

import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Slider;

public class Stealer extends NightModule {
	
	public static Stealer INSTANCE;
	public Slider min = new Slider("Speed", "Custom", 1, 0, 9, 1);
	public Slider rand = new Slider("Random", "Custom", 0, 0, 9, 1);
	
	public Stealer() {
		super(Type.MISC, "Stealer", "Grim");
		this.addMode("Custom");
		this.addOption(min);
		this.addOption(rand);
		INSTANCE = this;
	}
	
	public Integer getDelay() {
		if (Stealer.INSTANCE.getMode().equals("Grim")) {
			return Integer.valueOf((int) Math.round(Math.random() * 4) + 3) * 50;
		} else if (Stealer.INSTANCE.getMode().equals("Custom")) {
			return Integer.valueOf((int) Math.round(Math.random() * Stealer.INSTANCE.rand.getInteger()) + Stealer.INSTANCE.min.getInteger()) * 50;
		} else {
			return Integer.valueOf((int) Math.round(Math.random() * 4) + 3) * 50;
		}
	}
}
