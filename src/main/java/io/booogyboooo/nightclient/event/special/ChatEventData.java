package io.booogyboooo.nightclient.event.special;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;

public class ChatEventData extends EventData {

	private String message;

	public ChatEventData(String message) {
		super(true, EventType.CHAT);
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}