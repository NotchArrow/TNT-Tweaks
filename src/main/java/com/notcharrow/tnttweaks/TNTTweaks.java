package com.notcharrow.tnttweaks;

import com.notcharrow.tnttweaks.config.ConfigManager;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;

public class TNTTweaks implements ModInitializer {
	@Override
	public void onInitialize() {
		ConfigManager.loadConfig();
	}
}