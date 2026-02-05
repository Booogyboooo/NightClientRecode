package io.booogyboooo.nightclient.event;

import io.booogyboooo.nightclient.NightClientRecode;

public class EventData {
	public Boolean isCancelable;
	public EventType eventType;
	public Boolean isCanceled = false;
	
	public EventData(Boolean cancelable, EventType eventType) {
		this.isCancelable = cancelable;
		this.eventType = eventType;
	}
	
	public void cancel(EventData event) {
		if (isCanceled) {
			NightClientRecode.LOGGER.warn("Tried to cancel an already canceled event!");
			return;
		}
		if (!isCancelable) {
			NightClientRecode.LOGGER.warn("Tried to cancel an uncancelable event!");
			return;
		}
		event.isCanceled = true;
	}
	
	public EventType getEventType() {
		return eventType;
	}
}