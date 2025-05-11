package com.notcharrow.tnttweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.notcharrow.tnttweaks.TNTTweaks;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final File CONFIG_FILE = new File("config", "tnt-tweaks.json");

	public static TNTTweaksConfig config;

	public static void loadConfig() {
		if (!CONFIG_FILE.exists()) {
			config = new TNTTweaksConfig();
			saveConfig();
			return;
		}
		try (FileReader reader = new FileReader(CONFIG_FILE)) {
			config = GSON.fromJson(reader, TNTTweaksConfig.class);
		} catch (IOException e) {
			e.printStackTrace();
			config = new TNTTweaksConfig();
		}
	}

	public static void saveConfig() {
		try {
			CONFIG_FILE.getParentFile().mkdirs();
			try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
				GSON.toJson(config, writer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}