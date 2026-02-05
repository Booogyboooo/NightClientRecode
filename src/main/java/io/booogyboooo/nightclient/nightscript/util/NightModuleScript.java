package io.booogyboooo.nightclient.nightscript.util;

import java.util.ArrayList;
import java.util.List;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.nightscript.NightScript;

public class NightModuleScript {
	
	public static List<String> getEvent(NightScript nightscript, String event) {
		List<String> eventLines = new ArrayList<>();
		boolean readingEvent = false;
		for (String line : nightscript.getScript()) {
			if (readingEvent) {
				eventLines.add(line.stripLeading());
				if (line.startsWith("$ EVENT_END")) {
					readingEvent = false;
				}
			} else {
				if (line.startsWith("$ " + event)) {
					eventLines.add(line.replace("$ " + event, "").strip());
					readingEvent = true;
				}
			}
		}
		return eventLines;
	}
	
	public static void registerEvent(NightScript script, NightModule module, String type, List<String> lines) {
		lines.removeLast();
		if (type.equals("ON_ENABLE")) {
			module.setOnEnable(() -> {
				NightClientRecode.getScriptEngine().eval(script, lines);
			});
		} else if (type.equals("ON_DISABLE")) {
			module.setOnDisable(() -> {
				NightClientRecode.getScriptEngine().eval(script, lines);
			});
		}
	}

}