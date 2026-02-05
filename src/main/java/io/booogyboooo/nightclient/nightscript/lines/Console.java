package io.booogyboooo.nightclient.nightscript.lines;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.nightscript.NightScript;
import io.booogyboooo.nightclient.nightscript.general.LineType;
import io.booogyboooo.nightclient.nightscript.util.DataParser;

public class Console extends LineType {

	public Console() {
		super("CONSOLE");
	}
	
	@Override
	public Object execute(String line, NightScript nightscript) {
		line = line.replace(this.getKey(), "").stripLeading();
		Object data = DataParser.parseData(nightscript, line);
		if (data != null) {
			NightClientRecode.LOGGER.info("NightScript: " + data);
		}
		return null;
	}
	
}