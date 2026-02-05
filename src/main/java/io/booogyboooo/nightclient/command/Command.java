package io.booogyboooo.nightclient.command;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class Command {
	public static MinecraftClient mc = null;
	private String name;
	private String desc;
	
	public Command(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public void run(List<String> args) {
		
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}
	
	public void callError(String error) {
		Command.mc.player.sendMessage(Text.literal(error).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(Color.RED.getRGB()))), false);
	}
}