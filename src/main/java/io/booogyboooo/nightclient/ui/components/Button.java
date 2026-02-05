package io.booogyboooo.nightclient.ui.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.ui.bind.NightModuleBind;
import io.booogyboooo.nightclient.ui.components.options.Mode;

import static io.booogyboooo.nightclient.ui.NightUIData.*;

import java.util.ArrayList;
import java.util.List;

public class Button {
	public NightModule module;
	private boolean option = false;
	private List<Option> options = new ArrayList<>();
	public BoundingBox box;
	
	public Button(NightModule module) {
		this.module = module;
		this.box = BoundingBox.EMPTY(this);
	}
	
	public boolean options() {
		return this.option;
	}
	
	public void render(DrawContext drawContext, int x, int y, int width, int height) {
		this.box.update(x, (int) (y * RSCALE), (int) (x + (WIDTH * RSCALE)), (int) ((y + HEIGHT) * RSCALE));
		int color = this.module.isToggled() ? MENABLED : MDISABLED;
		int tx = (int) (x + (WIDTH/2 * RSCALE) - TEXT_RENDERER.getWidth(this.module.getName())/2);
		int ty = (int) ((y + 5 + (HEIGHT/2) - TEXT_RENDERER.fontHeight/2) * RSCALE);
		drawContext.drawText(TEXT_RENDERER, this.module.getName(), tx, ty, color, false);
		int i = 0;
		if (this.options()) {
			refreshOptions();
			y += (OHEIGHT * 4);
			for (Option option : this.options) {
				i++;
				option.draw(drawContext, x, y + (int) (HEIGHT * RSCALE) + (int) (OHEIGHT * i), WIDTH, HEIGHT);
			}
		}
	}

	public void click(int mouseX, int mouseY, int button) {
		if (this.box.withinButton(mouseX, mouseY)) {
			if (button == 0) {
				this.module.toggle();
			} else if (button == 1) {
				this.option = !this.option;
			} else if (button == 2) {
				MinecraftClient.getInstance().setScreen(new NightModuleBind(this.module));
			}
		} else if (this.options()) {
			this.options.forEach(option -> {
				option.run(mouseX, mouseY, button);
			});
		}
	}
	
	public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
	    this.options.forEach(option -> {
	    	option.scroll(mouseX, mouseY, horizontalAmount, verticalAmount);
	    });
	}
	
	public void refreshOptions() {
		List<Option> temp = new ArrayList<Option>();
		temp.add(new Mode(this.module));
		temp.addAll(this.module.getOptions(this.module.getMode()));
		this.options = temp;
	}
	
	public int optionAmt() {
		return this.module.getOptions(this.module.getMode()).size() + 1; // +1 for mode
	}
	
	public boolean hasOptions() {
		return !(this.module.getOptions(this.module.getMode()).size() == 0);
	}
}
