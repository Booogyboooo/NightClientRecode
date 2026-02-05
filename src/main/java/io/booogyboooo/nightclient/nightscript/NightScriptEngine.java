package io.booogyboooo.nightclient.nightscript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.booogyboooo.nightclient.module.ModuleList;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.nightscript.ScriptMeta.ScriptType;
import io.booogyboooo.nightclient.nightscript.general.LineType;
import io.booogyboooo.nightclient.nightscript.general.LineWrapper;
import io.booogyboooo.nightclient.nightscript.util.NightModuleScript;

public class NightScriptEngine {
	public void load(File nightscript) {
		NightScript script = new NightScript(nightscript);
		script.init();
		process(script);
	}
	
	public void process(NightScript nightscript) {
		if (nightscript.getType() == ScriptType.MODULE) {
			moduleEval(nightscript);
		} else if (nightscript.getType() == ScriptType.EXECUTABLE) {
			scriptEval(nightscript.getScript());
		}
	}
	
	public void eval(NightScript script, List<String> lines) {
		List<LineWrapper> plines = new ArrayList<>();
		
		int sdepth = 0;
		for (String line : lines) {
			line = line.stripLeading().stripTrailing();
			if (line.startsWith(">>") || line.startsWith("$")) {
				continue;
			}
			if (line.startsWith("}")) {
				sdepth--;
			}
			if (line.endsWith("{")) {
				sdepth++;
			}
			plines.add(new LineWrapper(line, sdepth));
		}

		for (LineWrapper line : plines) {
			LineType lineType = line.getLineType();
			String rline = line.getLine();
			if (line.getDepth() >= script.depth()) {
				if (line.getLine().strip() == "") {
					continue;
				}
				lineType.execute(rline, script);
			}
			if (rline.startsWith("}")) {
				script.down();
			}
			if (rline.endsWith("{")) {
				script.up();
			}
		}
	}
	
	private void scriptEval(List<String> lines) {
		
	}
	
	private void moduleEval(NightScript nightscript) {
		List<String> modes = List.of(nightscript.getData("MODULE_MODES").replace("[", "").replace("]", "").strip().split(","));
		Type moduleType = Type.valueOf(nightscript.getData("MODULE_TYPE"));
		String moduleName = nightscript.getData("MODULE_NAME");
		List<String> events = List.of(nightscript.getData("MODULE_EVENTS").replace("[", "").replace("]", "").strip().split(","));
		NightModule module = new NightModule(moduleType, moduleName, modes.getFirst());
		for (String mode : modes) {
			if (modes.indexOf(mode) == 0) {
				continue;
			}
			module.addMode(mode.strip());
		};
		for (String event : events) {
			List<String> eventScript = NightModuleScript.getEvent(nightscript, event.strip());
			String type = eventScript.removeFirst();
			NightModuleScript.registerEvent(nightscript, module, type, eventScript);
		}
		ModuleList.register(module);
		nightscript.setModule(module);
		nightscript.define("GLOBAL-MODULE_MODE", module.getMode());
	}

}
