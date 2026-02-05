package io.booogyboooo.nightclient.event.special;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;

public class KeyEventData extends EventData {

	private int key;
	private int modifiers;

	public KeyEventData(int key, int modifiers) {
		super(true, EventType.KEYPRESS);
		this.key = key;
		this.modifiers = modifiers;
	}
	
	public int getKeyCode() {
		return key;
	}
	
	public char getKey() {
		return (char) key;
	}
	
	public int getModifiers() {
		return modifiers;
	}

}