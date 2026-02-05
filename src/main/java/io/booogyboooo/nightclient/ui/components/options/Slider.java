package io.booogyboooo.nightclient.ui.components.options;

import java.text.DecimalFormat;

import io.booogyboooo.nightclient.ui.components.Option;
import net.minecraft.client.gui.DrawContext;
import static io.booogyboooo.nightclient.ui.NightUIData.*;

public class Slider extends Option {
	
	private double step;
	private double max;
	private double min;
	private double value;
	private String title;
	private DecimalFormat format = new DecimalFormat("0.##");

	public Slider(String title, String mode, double value, double min, double max, double step) {
		super(value, mode, title);
		this.title = title;
		this.value = value;
		this.min = min;
		this.max = max;
		this.step = step;
	}
	
	@Override
	public void scroll(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		if (!this.box.withinButton(mouseX, mouseY)) {return;}
		this.value += this.step * verticalAmount;
		if (this.value >= this.max) {this.value = this.max;}
		if (this.value <= this.min) {this.value = this.min;}
	}
	
	@Override
	public void draw(DrawContext drawContext, int x, int y, int width, int height) {
		int ty = (int) (((y + 6 + (HEIGHT/2) - TEXT_RENDERER.fontHeight/2)) * RSCALE) - (int) (HEIGHT * RSCALE * 3) - 4;
		drawContext.drawText(TEXT_RENDERER, this.title.toUpperCase() + ":", x + 3, ty, OCOLOR, false);
		this.box.update(x, ty - 2, (int) (x + (WIDTH * RSCALE)), ty + (int) ((OHEIGHT - 3) * RSCALE));
		drawContext.drawText(TEXT_RENDERER, String.valueOf(format.format(this.value)), x + (int) (WIDTH * RSCALE) - 2 - TEXT_RENDERER.getWidth(String.valueOf(format.format(this.value))), ty, OCOLOR, false);
	}
	
	@Override
	public void setValue(Object value) {
		if (value instanceof String) {
			this.value = Double.valueOf((String) value);
		} else {
			this.value = (double) value;
		}
		
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}
	
	public double getDouble() {
		return this.value;
	}
	
	public int getInteger() {
		return ((Double) this.value).intValue();
	}
	
}