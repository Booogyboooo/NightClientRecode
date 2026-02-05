package io.booogyboooo.nightclient.module;

import java.util.ArrayList;
import java.util.List;

import io.booogyboooo.nightclient.ui.NightUIData;

public class ModuleList {
	private static List<NightModule> modules = new ArrayList<>();
	
	public static void register(NightModule module) {
		modules.add(module);
	}
	
	public static void unregister(NightModule module) {
		modules.remove(module);
	}
	
	public static List<NightModule> getList() {
		return modules;
	}
	
	public static void sort() {
		modules.sort((m1, m2) -> Integer.compare(NightUIData.TEXT_RENDERER.getWidth(m2.getName() + " (" + m2.getMode() + ")"),NightUIData.TEXT_RENDERER.getWidth(m1.getName() + " (" + m1.getMode() + ")")));
	}
}