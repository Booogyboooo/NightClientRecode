package io.booogyboooo.nightclient.module;

import java.util.ArrayList;
import java.util.List;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.ui.components.Option;
import net.minecraft.client.MinecraftClient;

public class NightModule {
	public static MinecraftClient mc = null;
	private boolean isToggled = false;
	private Type type;
	private String name;
	private List<Option> options = new ArrayList<>();
	private List<String> modes = new ArrayList<>();
	private String currMode;
	private int keybind;
	private Runnable onEnable;
	private Runnable onDisable;
	
	public NightModule(Type type, String name, String firstMode) {
		this.type = type;
		this.name = name;
		this.currMode = firstMode;
		this.addMode(firstMode);
	}
	
	public List<String> getModes() {
		return this.modes;
	}
	
	public void setMode(String mode) {
		this.currMode = mode;
	}
	
	public void setBind(int keybind) {
		this.keybind = keybind;
	}
	
	public int getBind() {
		return this.keybind;
	}
	
	public void toggle() {
		if (!isToggled) {
			this.isToggled = true;
			onEnable();
		} else {
			this.isToggled = false;
			onDisable();
		}
	}
	
	public void toggle(Boolean enable) {
		if (enable) {
			this.isToggled = true;
			onEnable();
		} else {
			this.isToggled = false;
			onDisable();
		}
	}
	
	public void onEnable() {
		if (onEnable != null) {
			onEnable.run();
		}
	}
	
	public void setOnEnable(Runnable onEnable) {
		this.onEnable = onEnable;
	}
	
	public void onDisable() {
		if (onDisable != null) {
			onDisable.run();
		}
	}
	
	public void setOnDisable(Runnable onDisable) {
		this.onDisable = onDisable;
	}
	
	public void modeChange(String mode) {
		
	}
	
	public boolean isToggled() {
		return this.isToggled;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void addMode(String name) {
		modes.add(name);
	}
	
	public void removeMode(String name) {
		modes.remove(name);
	}
	
	public String getMode() {
		return currMode;
	}
	
	public void nextMode() {
		if (modes.getLast().equals(currMode)) {
			currMode = modes.getFirst();
			modeChange(currMode);
			return;
		}
		currMode = modes.get(modes.indexOf(currMode) + 1);
		modeChange(currMode);
	}
	
	public void addOption(Option option) {
		this.options.add(option);
	}
	
	public List<Option> getOptions(String mode) {
		List<Option> temp = new ArrayList<>();
		for (Option option : this.options) {
			if (option.getMode().equals(this.getMode())) {
				temp.add(option);
			} else if (option.getMode().equals("*")) {
				temp.add(option);
			}
		}
		return temp;
	}
	
	public List<Option> getOptions() {
		return this.options;
	}
	
	public final void registerEvent(Object target, String methodName) {
		NightClientRecode.getEventManager().registerEvent(target, methodName);
	}
	
	public final void unregisterEvent(Object target, String methodName) {
		NightClientRecode.getEventManager().unregisterEvent(target, methodName);
	}
}