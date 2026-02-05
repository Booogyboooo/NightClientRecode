package io.booogyboooo.nightclient.hud;

import static io.booogyboooo.nightclient.ui.NightUIData.ZOOM;

import org.joml.Matrix3x2fStack;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.event.special.RenderHUDEventData;
import io.booogyboooo.nightclient.module.ModuleList;
import io.booogyboooo.nightclient.ui.NightUIData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class NightHUD {
	
	private int x = 5;
	private int y = 5;
	private int xpadding = 10;
	private int ypadding = 8;
	private boolean first = true;
	private int lwidth = 0;
	
	public void init() {
		NightClientRecode.getEventManager().registerEvent(this, "_listener");
	}
	
	@Event(eventType = EventType.RENDER_HUD)
	public void _listener(EventData data) {
		if (MinecraftClient.getInstance().currentScreen != null) {
			return;
		}
		RenderHUDEventData rdata = (RenderHUDEventData) data;
		DrawContext context = rdata.getContext();
		Matrix3x2fStack matrices = context.getMatrices();
		matrices.pushMatrix();
		matrices.scale(ZOOM, ZOOM, matrices);
		y = 5;
		first = true;
		lwidth = 0;
		ModuleList.sort();
		ModuleList.getList().forEach(module -> {
			String text = module.getName() + " (" + module.getMode() + ")";
			int width = xpadding + NightUIData.TEXT_RENDERER.getWidth(text);
			int height = ypadding + NightUIData.TEXT_RENDERER.fontHeight;
			if (!module.isToggled()) {
				return;
			}
			if (first) {
				context.fill(x - 1, y, x + width + 1, y - 1, NightUIData.BORDER_COLOR);
				first = false;
			}
			context.fill(x, y, x + width, y + height, NightUIData.BG_COLOR);
			context.fill(x - 1, y, x, y + height, NightUIData.BORDER_COLOR);
			context.fill(x + width, y, x + width + 1, y + height, NightUIData.BORDER_COLOR);
			context.drawText(NightUIData.TEXT_RENDERER, text, x + xpadding/2, y + 5, NightUIData.TITLE_COLOR, false);
			if (lwidth != 0) {
				context.fill(x + width, y, x + lwidth + 1, y + 1, NightUIData.BORDER_COLOR);
			}
			y += height;
			lwidth = width;
		});
		if (lwidth != 0) {
			context.fill(x - 1, y, x + lwidth + 1, y + 1, NightUIData.BORDER_COLOR);
		}
		matrices.popMatrix();
	}

}