package io.booogyboooo.nightclient.ui;

import org.joml.Matrix3x2fStack;

import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.Window;
import io.booogyboooo.nightclient.util.RenderUtil;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import static io.booogyboooo.nightclient.ui.NightUIData.*;

import java.util.ArrayList;
import java.util.List;

public class NightUI extends Screen {
	
	public List<Window> windows = new ArrayList<>();
	public boolean auto = false;

	public NightUI() {
		super(Text.of("NightUI"));
		int i = 0;
		for (Type type : List.of(Type.values())) {
			windows.add(new Window(type, i));
			i++;
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		windows.forEach(window -> {
	    	window.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
	    });
	    return true;
	}
	
	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		Matrix3x2fStack matrices = drawContext.getMatrices();
		matrices.pushMatrix();
		matrices.scale(ZOOM, ZOOM, matrices);
		windows.forEach(window -> {
			window.render(drawContext);
		});
		renderScale(drawContext);
    	matrices.popMatrix();
	}
	
	@Override
	public boolean mouseClicked(Click click, boolean doubled) {
		double mouseX = click.x();
		double mouseY = click.y();
		int button = click.button();
		int wdth = (int) (WIDTH * 0.33f);
		int heght = (int) (HEIGHT * 0.33f);
		int x = (int) (this.width * (1/ZOOM)) - wdth - 3;
		int y = (int) (this.height * (1/ZOOM)) - heght - 3;
		double mx = mouseX * (1/ZOOM);
		double my = mouseY * (1/ZOOM);
		if (x < mx && x + wdth > mx && y < my && y + heght > my) {
			if (auto) {
				auto = false;
				ZOOM = 2;
				return true;
			}
			ZOOM -= 0.25;
			if (ZOOM <= 0.5) {
				ZOOM = (float) this.width/650;
				auto = true;
			}
			return true;
		}
		windows.forEach(window -> {
			window.click(mouseX * (1/ZOOM), mouseY * (1/ZOOM), button);
		});
		return true;
	}
	
	public void renderScale(DrawContext drawContext) {
		int wdth = (int) (WIDTH * 0.33f);
		int heght = (int) (HEIGHT * 0.33f);
		int x = (int) (this.width * (1/ZOOM)) - wdth - 3;
		int y = (int) (this.height * (1/ZOOM)) - heght - 3;
		drawContext.fill(x, y, x + wdth, y + heght, BG_COLOR);
		RenderUtil.drawBorder(drawContext, x, y, wdth, heght, BORDER_COLOR, 1);
		drawContext.drawText(textRenderer, "Scale: ", x + 3, y + (heght/2 - textRenderer.fontHeight/2), TITLE_COLOR, false);
		String text = (auto ? "auto" : String.valueOf(ZOOM) + "x");
		drawContext.drawText(textRenderer, text, x + wdth - 3 - textRenderer.getWidth(text), y + (heght/2 - textRenderer.fontHeight/2), TITLE_COLOR, false);
	}

}
