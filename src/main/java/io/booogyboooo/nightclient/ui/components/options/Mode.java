package io.booogyboooo.nightclient.ui.components.options;

import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.ui.components.Option;
import net.minecraft.client.gui.DrawContext;
import static io.booogyboooo.nightclient.ui.NightUIData.*;

public class Mode extends Option {
	private NightModule module;

	public Mode(NightModule module) {
		super(module.getMode(), module.getMode(), module.getMode());
		this.module = module;
	}
	
	@Override
	public void run(double mouseX, double mouseY, int button) {
		if (this.box.withinButton(mouseX, mouseY)) {
			this.module.nextMode();
		}
	}
	
	@Override
	public void draw(DrawContext drawContext, int x, int y, int width, int height) {
		y -= (7 * OHEIGHT);
		int ty = (int) (((y + 5 + (HEIGHT/2) - TEXT_RENDERER.fontHeight/2) + WIDTH) * RSCALE) - (int) (HEIGHT * RSCALE * 3) - 4;
		this.box.update(x, ty - 2,  (int) (x + (WIDTH * RSCALE)), ty + (int) ((OHEIGHT - 3) * RSCALE));
		drawContext.drawText(TEXT_RENDERER,"MODE: " + this.module.getMode(), x + 3, ty, OCOLOR, false);
	}
}