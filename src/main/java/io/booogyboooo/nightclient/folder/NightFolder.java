package io.booogyboooo.nightclient.folder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import io.booogyboooo.nightclient.module.ModuleList;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.ui.components.Option;
import net.minecraft.client.MinecraftClient;

public class NightFolder {
	
	private static String path = MinecraftClient.getInstance().runDirectory.getPath() + "/NightClient";
	private static File folder = new File(path);
	private static File config = new File(path + "/config.txt");
	
	public static void init() {
		try {
			folder.mkdirs();
			config.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		load();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			save();
		}));
	}
	
	private static void load() {
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(config));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		String line;
		try {
			while ((line = fileReader.readLine()) != null) {
				for (NightModule module : ModuleList.getList()) {
					if(module.getName().equals(line.split(":")[0].strip())) {
						module.setMode(line.split(":")[1].replaceAll("\n", ""));
						for (Option option : module.getOptions()) {
							String temp2 = line.split(":")[2];
							temp2 = temp2.replace("{", "");
							temp2 = temp2.replace("}", "");
							String[] temp3 = temp2.split("%");
							for (int i = 0; i < temp3.length; i++) {
								if (option.getName().strip().equals(temp3[i].split("!")[0])) {
									option.setValue(temp3[i].split("!")[1]);
								}
							}
						}
					}
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void save() {
		FileWriter writer;
		try {
			writer = new FileWriter(config);
		} catch (IOException e) {
			return;
		}
		ModuleList.getList().forEach(module -> {
			String temp = "";
			temp += (module.getName() + ":" + module.getMode());
			temp += ":{";
			for (Option option : module.getOptions()) {
				temp += option.getName().strip();
				temp += "!";
				temp += option.getValue().toString();
				if (!module.getOptions().getLast().equals(option)) {
					temp += "%";
				}
			}
			temp += "}";
			try {
				writer.write(temp);
				writer.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}