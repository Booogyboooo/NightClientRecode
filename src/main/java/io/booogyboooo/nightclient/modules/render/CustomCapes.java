package io.booogyboooo.nightclient.modules.render;

import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;

public class CustomCapes extends NightModule {

	public static CustomCapes INSTANCE;
	
	public CustomCapes() {
		super(Type.RENDER, "CustomCapes", "Gay");
		INSTANCE = this;
	}

}
