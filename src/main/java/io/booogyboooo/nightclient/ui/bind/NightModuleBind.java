package io.booogyboooo.nightclient.ui.bind;

import org.joml.Matrix3x2fStack;

import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.ui.NightUI;
import io.booogyboooo.nightclient.ui.NightUIData;
import io.booogyboooo.nightclient.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;

public class NightModuleBind extends Screen {

	private NightModule module;

	public NightModuleBind(NightModule module) {
		super(Text.of("Binding " + module.getName()));
		this.module = module;
	}
	
	@Override
	public boolean keyPressed(KeyInput keyInput) {
		int keyCode = keyInput.getKeycode();
		if (keyCode != 256) {
			this.module.setBind(keyCode);
		}
		return super.keyPressed(keyInput);
	}
	
	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		int x1 = (this.width/2) - 100;
		int y1 = (this.height/2) - 47;
		int x2 = x1 + 200;
		int y2 = y1 + 95;
		drawContext.fill(x1, y1, x2 - 10, y2, NightUIData.BG_COLOR);
		RenderUtil.drawBorder(drawContext, x1, y1, x2 - x1 - 10, y2 - y1, NightUIData.BORDER_COLOR, 1);
		Matrix3x2fStack matrices = drawContext.getMatrices();
		matrices.pushMatrix();
		matrices.scale(3, 3, matrices);
		drawContext.drawText(NightUIData.TEXT_RENDERER, module.getName(), x1/3 + ((x2/3 - x1/3)/2 - NightUIData.TEXT_RENDERER.getWidth(module.getName())/2), y1/3 + NightUIData.TEXT_RENDERER.fontHeight, NightUIData.TITLE_COLOR, false);
		matrices.popMatrix();
		matrices.pushMatrix();
		matrices.scale(2, 2, matrices);
		drawContext.drawText(NightUIData.TEXT_RENDERER, "Binding: " + (char) module.getBind(), x1/2 + ((x2/2 - x1/2)/2 - NightUIData.TEXT_RENDERER.getWidth("Binding: " + (char) module.getBind())/2), y1/2 + NightUIData.TEXT_RENDERER.fontHeight * 3, NightUIData.OCOLOR, false);
		matrices.popMatrix();
	}
	
	@Override
	public void close() {
		MinecraftClient.getInstance().setScreen(new NightUI());
	}

}