package io.booogyboooo.nightclient.nightscript.general;

import java.util.ArrayList;
import java.util.List;

import io.booogyboooo.nightclient.nightscript.NightScript;
import io.booogyboooo.nightclient.nightscript.lines.Define;
import io.booogyboooo.nightclient.nightscript.lines.Str;
import io.booogyboooo.nightclient.nightscript.lines.Update;
import io.booogyboooo.nightclient.nightscript.lines.Velo;
import io.booogyboooo.nightclient.nightscript.lines.Console;

public class LineType {
	
	public static List<LineType> types = new ArrayList<>();
	private String key;

	public LineType(String key) {
		this.key = key;
	}
	
	public Object execute(String line, NightScript nightscript) {
		System.out.println("Called unkown type (" + line + ")");
		return null;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public boolean isOf(String line) {
		return line.stripLeading().startsWith(this.key);
	}
	
	public static void init() {
		types.add(new LineType("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));
		types.add(new Define());
		types.add(new Console());
		types.add(new Update());
		types.add(new Str());
		types.add(new Velo());
	}
	
	public static LineType getType(String line) {
		LineType type = types.getFirst();
		for (LineType lineType : types) {
			if (lineType.isOf(line)) {
				type = lineType;
			}
		}
		return type;
	}
	
}