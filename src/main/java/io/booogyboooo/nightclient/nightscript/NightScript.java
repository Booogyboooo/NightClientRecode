package io.booogyboooo.nightclient.nightscript;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.module.NightModule;

public class NightScript {
	private Map<String, Object> env = new HashMap<>();
	private ScriptMeta meta;
	private int depth = 0;
	private List<String> lines = new ArrayList<>();
	private List<String> script = new ArrayList<>();
	private NightModule module;
	
	public NightScript(File nightscript) {
		try { this.lines = Files.readAllLines(nightscript.toPath()); } catch (IOException e) { e.printStackTrace(); }
		this.meta = new ScriptMeta(this.lines);
	}
	
	public io.booogyboooo.nightclient.nightscript.ScriptMeta.ScriptType getType() {
		return this.meta.getType();
	}
	
	public String getName() {
		return this.meta.getName();
	}
	
	public String getData(String key) {
		return this.meta.getData(key);
	}
	
	public List<String> getScript() {
		return this.script != null ? this.script : getScript(this.lines);
	}
	
	public void define(String name, Object value) {
		this.env.put(name, value);
	}
	
	public Object retrive(String name) {
		return this.env.get(name);
	}
	
	public void clear() {
		this.env.clear();
	}
	
	public int depth() {
		return this.depth;
	}
	
	public void up() {
		this.depth -= 1;
	}
	
	public void down() {
		this.depth -= 1;
	}
	
	public void init() {
		this.script = getScript(this.lines);
		this.env.clear();
		this.env.put("true", 1d);
		this.env.put("false", 0d);
		this.env.put("GLOBAL-INTEGER_MAX", (double) Integer.MAX_VALUE);
		this.env.put("GLOBAL-INTEGER_MIN", (double) Integer.MIN_VALUE);
		this.env.put("GLOBAL-MODULE_MODE", "");
		this.env.put("GLOBAL-MODULE_ENABLED", 0);
	}
	
	private List<String> getScript(List<String> nightscript) {
		List<String> scriptLines = new ArrayList<>();
		boolean readingScript = false;
		for (String line : nightscript) {
			if (readingScript) {
				scriptLines.add(line);
			} else {
				if (line.startsWith(">> SCRIPT")) {
					readingScript = true;
				} else if (line.startsWith(">> META")) {
					readingScript = false;
				}
			}
		}
		return scriptLines;
	}

	public void setModule(NightModule module) {
		if (this.getType() != io.booogyboooo.nightclient.nightscript.ScriptMeta.ScriptType.MODULE) {
			NightClientRecode.LOGGER.error("Cant bind module to script for a non module script");
			return;
		}
		this.module = module;
	}
	
	public NightModule getModule() {
		if (this.getType() != io.booogyboooo.nightclient.nightscript.ScriptMeta.ScriptType.MODULE) {
			NightClientRecode.LOGGER.error("Cant get module binded to script for a non module script");
			return null;
		}
		return this.module;
	}
	
}

class ScriptMeta {
	private List<String> metaChunk;
	private String name;
	private ScriptType type;

	public ScriptMeta(List<String> nightscript) {
		this.metaChunk = getMetaChunk(nightscript);
		this.name = getName(this.metaChunk);
		this.type = getType(this.metaChunk);
	}
	
	public ScriptType getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getData(String key) {
		String value = "";
		for(String line : metaChunk) {
			if (line.startsWith(key + ":")) {
				value = line.replace(key + ":", "").strip();
			}
		}
		return value;
	}
	
	private List<String> getMetaChunk(List<String> nightscript) {
		List<String> metaLines = new ArrayList<>();
		boolean readingMeta = false;
		for (String line : nightscript) {
			if (readingMeta) {
				metaLines.add(line);
			} else {
				if (line.startsWith(">> META")) {
					readingMeta = true;
				} else if (line.startsWith(">> SCRIPT")) {
					readingMeta = false;
				}
			}
		}
		return metaLines;
	}
	
	private String getName(List<String> metaChunk) {
		String name = "";
		for(String line : metaChunk) {
			if (line.startsWith("NAME:")) {
				name = line.replace("NAME:", "").strip();
			}
		}
		return name;
	}
	
	private ScriptType getType(List<String> metaChunk) {
		ScriptType type = ScriptType.UNKNOWN;
		for(String line : metaChunk) {
			if (line.startsWith("TYPE:")) {
				type = ScriptType.from(line.replace("TYPE:", "").strip());
			}
		}
		return type;
	}

	enum ScriptType {
		EXECUTABLE, MODULE, UNKNOWN;
		
		public static ScriptType from(String type) {
			if (type.equals("EXECUTABLE")) {
				return ScriptType.EXECUTABLE;
			} else if (type.equals("MODULE")) {
				return ScriptType.MODULE;
			} else {
				return ScriptType.UNKNOWN;
			}
		}
	}
} 