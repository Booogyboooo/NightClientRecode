package io.booogyboooo.nightclient.nightscript.lines;

import io.booogyboooo.nightclient.nightscript.NightScript;
import io.booogyboooo.nightclient.nightscript.general.LineType;

public class Update extends LineType {

	public Update() {
		super("UPDATE");
	}
	
	@Override
	public Object execute(String line, NightScript nightscript) {
		line = line.replace(this.getKey(), "").stripLeading();
		if (line.strip().equals("GLOBAL-MODULE_MODE")) {
			nightscript.define("GLOBAL-MODULE_MODE", nightscript.getModule().getMode());
		} else if (line.strip().equals("GLOBAL-MODULE_ENABLED")) {
			nightscript.define("GLOBAL-MODULE_ENABLED", nightscript.getModule().isToggled() ? 1 : 0);
		}
		return null;
	}

}