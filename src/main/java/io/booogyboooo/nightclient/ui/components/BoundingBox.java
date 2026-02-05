package io.booogyboooo.nightclient.ui.components;

import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class BoundingBox {

	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private Object data;
	
	public BoundingBox(int sx, int sy, int ex, int ey, Object odata) {
		this.startX = sx;
		this.startY = sy;
		this.endX = ex;
		this.endY = ey;
		this.data = odata;
	}
	
	public boolean withinButton(double mouseX, double mouseY) {
		if (this.startX < mouseX && this.endX > mouseX && this.startY < mouseY && this.endY > mouseY) {
			return true;
		} else {
			return false;
		}
	}
	
	public Object getData() {
		return this.data;
	}
	
	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}
	
	public static BoundingBox EMPTY() {
		return new BoundingBox(-1, -1, -1, -1, -1);
	}
	
	public static BoundingBox EMPTY(Object data) {
		return new BoundingBox(-1, -1, -1, -1, data);
	}

	public void update(int x, int y, int x2, int y2) {
		this.startX = x;
		this.startY = y;
		this.endX = x2;
		this.endY = y2;
	}
	
	public void debug(DrawContext drawContext) {
		drawContext.fill(startX, startY, endX, endY, Color.RED.getRGB());
	}
}