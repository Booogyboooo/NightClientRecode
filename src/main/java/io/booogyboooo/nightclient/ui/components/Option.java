package io.booogyboooo.nightclient.ui.components;

import net.minecraft.client.gui.DrawContext;

public class Option {
	private Object defaultValue;
	private Object value;
	private String mode;
	public BoundingBox box;
	private String name;
	
	public Option(Object defaultValue, String mode, String name) {
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.mode = mode;
		this.name = name;
		this.box = BoundingBox.EMPTY(this);
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public void setBounds(int x1, int y1, int x2, int y2) {
		this.box.update(x1, y1, x2, y2);
	}
	
	public boolean withinOption(double mouseX, double mouseY) {
		if (this.box.withinButton(mouseX, mouseY)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getMode() {
		return this.mode;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void draw(DrawContext drawContext, int x1, int y1, int width, int height) {
		
	}
	
	public void run(double mouseX, double mouseY, int button) {
		
	}
	
	public void scroll(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		
	}

}
