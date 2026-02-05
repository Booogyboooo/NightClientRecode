package io.booogyboooo.nightclient.modules.render;

import java.awt.Color;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.event.special.RenderHUDEventData;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.NightUIData;
import io.booogyboooo.nightclient.util.RayTraceUtil;
import io.booogyboooo.nightclient.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class EntityInfo extends NightModule {

	public EntityInfo() {
		super(Type.RENDER, "EntityInfo", "Default");
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "onHUDRender");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "onHUDRender");
	}
	
	@Event(eventType = EventType.RENDER_HUD)
	public void onHUDRender(EventData data) {
		RenderHUDEventData rdata = (RenderHUDEventData) data;
		Entity crosshair = RayTraceUtil.getEntityUnderCrosshair();
		if (crosshair != null) {
			if (!(crosshair instanceof LivingEntity)) {
				return;
			}
			LivingEntity entity = (LivingEntity) crosshair;
			DrawContext context = rdata.getContext();
			int cx = MinecraftClient.getInstance().getWindow().getScaledWidth()/2;
			int cy = MinecraftClient.getInstance().getWindow().getScaledHeight()/2 - 32;
			if (this.getMode().equals("Right")) {
				cy = 0;
				cx = MinecraftClient.getInstance().getWindow().getScaledWidth() - 130;
			}
			context.fill(cx + 5, cy + 5, cx + 125, cy + 27, NightUIData.BG_COLOR);
			RenderUtil.drawBorder(context, cx + 5, cy + 5, 120, 22, Color.BLACK.getRGB(), 1);
			context.drawText(NightUIData.TEXT_RENDERER == null ? MinecraftClient.getInstance().textRenderer : NightUIData.TEXT_RENDERER, Text.literal("HEALTH: ").formatted(Formatting.BOLD).append(String.format("%.1f", entity.getHealth()) + "/" + String.format("%.1f", entity.getMaxHealth())), cx + 7, cy + 7, Color.WHITE.getRGB(), false);
			context.drawText(NightUIData.TEXT_RENDERER == null ? MinecraftClient.getInstance().textRenderer : NightUIData.TEXT_RENDERER, Text.literal("TYPE: ").formatted(Formatting.BOLD).append(crosshair.getType().getName()), cx + 7, cy + 18, Color.WHITE.getRGB(), false);
		}
	}

}