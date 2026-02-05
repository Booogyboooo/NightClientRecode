package io.booogyboooo.nightclient.command;

import java.util.ArrayList;
import java.util.List;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.event.special.ChatEventData;

public class CommandManager {
	private List<Command> commands = new ArrayList<Command>();
	
	public void init() {
		NightClientRecode.getEventManager().registerEvent(this, "onChat");
	}
	
	public void registerCmd(Command cmd) {
		commands.add(cmd);
	}
	
	public List<Command> getCommands() {
		return commands;
	}
	
	public Command getCommand(String name) {
		Command target = null;
		for (Command command : commands) {
			if (("." + command.getName()).equals(name)) {
				target = command;
			}
		}
		return target;
	}
	
	@Event(eventType = EventType.CHAT)
	public void onChat(EventData data) {
		ChatEventData dat = (ChatEventData) data;
		if (!dat.getMessage().startsWith(".")) {
			return;
		}
		List<String> args = List.of(dat.getMessage().split(" "));
		String name = args.removeFirst();
		getCommand(name).run(args);
		dat.cancel(dat);
	}
}