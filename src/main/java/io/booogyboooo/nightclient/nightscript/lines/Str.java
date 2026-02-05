package io.booogyboooo.nightclient.nightscript.lines;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.nightscript.NightScript;
import io.booogyboooo.nightclient.nightscript.general.LineType;
import io.booogyboooo.nightclient.nightscript.util.DataParser;

public class Str extends LineType {

	public Str() {
		super("STR");
	}
	
	@Override
	public Object execute(String line, NightScript nightscript) {
		line = line.replace(this.getKey(), "").stripLeading();
		if (line.contains("EQUALS")) {
			String[] parts = line.split("EQUALS");
			Object leftData = DataParser.parseData(nightscript, parts[0]);
			Object rightData = DataParser.parseData(nightscript, parts[1]);
			if (leftData.equals(rightData)) {
				return 1;
			} else {
				return 0;
			}
		}
		NightClientRecode.LOGGER.warn("Unknown LOGIC evaluation (" + line + ") returned false (0)");
		return 0; // Return false by default
	}

}