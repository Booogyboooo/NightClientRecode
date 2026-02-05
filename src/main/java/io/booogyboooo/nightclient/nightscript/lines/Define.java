package io.booogyboooo.nightclient.nightscript.lines;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.nightscript.NightScript;
import io.booogyboooo.nightclient.nightscript.general.LineType;
import io.booogyboooo.nightclient.nightscript.util.DataParser;

public class Define extends LineType {

	public Define() {
		super("DEFINE");
	}
	
	@Override
	public Object execute(String line, NightScript nightscript) {
		line = line.replace(this.getKey(), "").stripLeading();
		String[] parts = line.split("AS");
		Object data = DataParser.parseData(nightscript, parts[1]);
		if (data != null) {
			nightscript.define(parts[0].strip(), data);
		} else {
			NightClientRecode.LOGGER.error("Could not define variable (" + line + ")");
		}
		return null;
	}

}