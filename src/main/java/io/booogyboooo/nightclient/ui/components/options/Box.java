package io.booogyboooo.nightclient.ui.components.options;

import io.booogyboooo.nightclient.ui.components.Option;
import io.booogyboooo.nightclient.util.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import static io.booogyboooo.nightclient.ui.NightUIData.*;

public class Box extends Option {
	
	private String title;
	private boolean enabled;
	
	public Box(String title, String mode, boolean enabled) {
		super(enabled, mode, title);
		this.title = title;
		this.enabled = enabled;
	}
	
	public boolean checked() {
		return this.enabled;
	}
	
	public void checked(boolean checked) {
		this.enabled = checked;
	}
	
	@Override
	public Object getValue() {
		return this.enabled;
	}
	
	@Override
	public void setValue(Object value) {
		if (value instanceof String) {
			this.enabled = Boolean.valueOf((String) value);
		} else {
			super.setValue(value);
		}
	}
	
	@Override
	public void draw(DrawContext drawContext, int x, int y, int width, int height) {
		int ty = (int) (((y + 6 + (HEIGHT/2) - TEXT_RENDERER.fontHeight/2)) * RSCALE) - (int) (HEIGHT * RSCALE * 3) - 4;
		drawContext.drawText(TEXT_RENDERER, this.title.toUpperCase() + ":", x + 3, ty, OCOLOR, false);
		RenderUtil.drawBorder(drawContext, x + (int) (WIDTH * RSCALE) - 11, ty + (TEXT_RENDERER.fontHeight/2) - 5, 8, 8, BCOLOR, 1);
		this.box.update(x, ty - 2, (int) (x + (WIDTH * RSCALE)), ty + (int) ((OHEIGHT - 3) * RSCALE));
		if (this.enabled) {
			drawContext.fill(x + (int) (WIDTH * RSCALE) - 9, ty + (TEXT_RENDERER.fontHeight/2) - 3, x + (int) (WIDTH * RSCALE) - 5, ty + (TEXT_RENDERER.fontHeight/2) + 1, ECOLOR);
		}
	}
	
	@Override
	public void run(double mouseX, double mouseY, int button) {
		if (this.box.withinButton(mouseX, mouseY)) {
			if (button == 0) {
				this.enabled = !this.enabled;
			}
		}
	}
}