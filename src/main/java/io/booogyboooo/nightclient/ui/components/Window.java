package io.booogyboooo.nightclient.ui.components;

import io.booogyboooo.nightclient.module.ModuleList;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.util.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static io.booogyboooo.nightclient.ui.NightUIData.*;
import java.util.ArrayList;
import java.util.List;

public class Window {
	public List<Button> buttons = new ArrayList<>();
	public Type type;
	public int index;
	
	public Window(Type type, int index) {
		this.type = type;
		this.index = index;
		ModuleList.getList().forEach(module -> {
			if (module.getType() == this.type) {
				buttons.add(new Button(module));
			}
		});
	}
	
	public void render(DrawContext drawContext) {
		int x = (int) (ISPACING + ((WIDTH + SPACING) * this.index) * RSCALE);
		int y = (int) (ISPACING * RSCALE);
		int x2 = x + (int) (WIDTH * RSCALE);
		int y2 = 5 + y + (int) ((HEIGHT * (this.buttons.size() + 1)) * RSCALE);
		for (Button button : this.buttons) {
			if (button.options()) {
				y2 += (button.optionAmt() * OHEIGHT * RSCALE);
			}
		}
		drawContext.fill(x, y, x2, y2, BG_COLOR);
		RenderUtil.drawBorder(drawContext, x, y, x2 - x, y2 - y, BORDER_COLOR, 1);
		int cx = x + (int) (WIDTH/2 * RSCALE);
		int cy = (int) (((HEIGHT/2) + ISPACING) * RSCALE);
		int tx = cx - TEXT_RENDERER.getWidth(Text.literal(this.type.name().toUpperCase()).formatted(Formatting.BOLD))/2;
		int ty = cy - (int) ((TEXT_RENDERER.fontHeight/2) * RSCALE);
		drawContext.drawText(TEXT_RENDERER, Text.literal(this.type.name().toUpperCase()).formatted(Formatting.BOLD), tx, ty, TITLE_COLOR, false);
		int i = 0;
		int e = 0;
		for (Button button : this.buttons) {
			i++;
			int mx = (int) (ISPACING + ((WIDTH + SPACING) * this.index) * RSCALE);
			int my = (int) (ISPACING * RSCALE) + (i * HEIGHT);
			my += (e * OHEIGHT);
			button.render(drawContext, mx, my, WIDTH, HEIGHT);
			if (button.options()) {
				e += button.optionAmt();
			}
		};
	}
	
	public void click(double mouseX, double mouseY, int button) {
		buttons.forEach(_button -> {
			_button.click((int) mouseX, (int) mouseY, button);
		});
	}
	
	public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
	    buttons.forEach(button -> {
	    	button.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
	    });
	}
	
	public void addButton(Button button) {
		buttons.add(button);
	}
}
