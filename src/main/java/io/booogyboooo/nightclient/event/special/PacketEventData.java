package io.booogyboooo.nightclient.event.special;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import net.minecraft.network.packet.Packet;

public class PacketEventData extends EventData {

	private Packet<?> packet;

	public PacketEventData(Packet<?> packet) {
		super(true, EventType.PACKET);
		this.packet = packet;
	}
	
	public Packet<?> getPacket() {
		return this.packet;
	}

}