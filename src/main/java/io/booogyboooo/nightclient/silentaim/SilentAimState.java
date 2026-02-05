package io.booogyboooo.nightclient.silentaim;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class SilentAimState {
	private boolean enabled;
	private float yaw;
	private float pitch;
	
	public SilentAimState() {
		this.enabled = false;
		this.yaw = 0;
		this.pitch = 0;
	}
	
	public void init() {
		NightClientRecode.getEventManager().registerEvent(this, "_listener");
	}
	
	public void enable() {
		this.enabled = true;
		SilentFlags.AIMING = true;
	}
	
	public void disable() {
		this.enabled = false;
		SilentFlags.AIMING = false;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void look(float pitch, float yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	@Event(eventType = EventType.POST_TICK)
	public void _listener(EventData data) {
		if (this.enabled && MinecraftClient.getInstance().player != null) {
			MinecraftClient.getInstance().player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(this.yaw, this.pitch, MinecraftClient.getInstance().player.isOnGround(), MinecraftClient.getInstance().player.horizontalCollision));
		}
	}
}