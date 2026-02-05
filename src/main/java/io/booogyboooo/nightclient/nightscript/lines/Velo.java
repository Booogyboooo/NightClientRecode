package io.booogyboooo.nightclient.nightscript.lines;

import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.nightscript.NightScript;
import io.booogyboooo.nightclient.nightscript.general.LineType;
import io.booogyboooo.nightclient.nightscript.util.DataParser;
import net.minecraft.entity.player.PlayerEntity;

public class Velo extends LineType {
	
	public Velo() {
		super("VELO");
	}

	@Override
	public Object execute(String line, NightScript nightscript) {
		line = line.replace(this.getKey(), "").stripLeading().stripTrailing();
		PlayerEntity player = NightModule.mc.player;
		if (player == null) {
			return null;
		}
		if (line.contains("ADD")) {
			line = line.replace("ADD", "").stripLeading().stripTrailing();
			String[] parts = line.split(" ");
			Object xData = DataParser.parseData(nightscript, parts[0]);
			Object yData = DataParser.parseData(nightscript, parts[1]);
			Object zData = DataParser.parseData(nightscript, parts[2]);
			player.addVelocity((Double) xData, (Double) yData, (Double) zData);
		} else if (line.contains("SET")) {
			line = line.replace("SET", "").stripLeading().stripTrailing();
			String[] parts = line.split(" ");
			Object xData = DataParser.parseData(nightscript, parts[0]);
			Object yData = DataParser.parseData(nightscript, parts[1]);
			Object zData = DataParser.parseData(nightscript, parts[2]);
			player.setVelocity((Double) xData, (Double) yData, (Double) zData);
		} else if (line.contains("GET")) {
			line = line.replace("GET", "").stripLeading().stripTrailing();
			if (line.contains("X")) {
				return player.getVelocity().getX();
			} else if (line.contains("Y")) {
				return player.getVelocity().getY();
			} else if (line.contains("Z")) {
				return player.getVelocity().getZ();
			}
		}
		return null;
	}
}