package com.notcharrow.tnttweaks.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class TNTTweaksModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return this::createConfigScreen;
	}

	private Screen createConfigScreen(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(Text.of("TNT Tweaks Config"));

		ConfigCategory general = builder.getOrCreateCategory(Text.of("General Settings"));

		addBoolean(general, "Mod Enabled", "Overall toggle for all mod functionality",
				ConfigManager.config.modEnabled,
				value -> ConfigManager.config.modEnabled = value);

		addBoolean(general, "Auto Ignite", "Should TNT Auto Ignite when placed",
				ConfigManager.config.autoIgnite,
				value -> ConfigManager.config.autoIgnite = value);

		addIntField(general, "Fuse Time (ticks)", "Fuse time for lit TNT; only works with auto ignite",
				ConfigManager.config.fuseTime,
				value -> ConfigManager.config.fuseTime = value, 0, 1200);

		addBoolean(general, "Break Blocks", "Should TNT break blocks",
				ConfigManager.config.breakBlocks,
				value -> ConfigManager.config.breakBlocks = value);

		addBoolean(general, "Ignore Blast Resistance", "Should TNT ignore block blast resistance",
				ConfigManager.config.ignoreBlastResistance,
				value -> ConfigManager.config.ignoreBlastResistance = value);

		addBoolean(general, "Modify Knockback", "Should TNT deal modified knockback",
				ConfigManager.config.modifyKnockback,
				value -> ConfigManager.config.modifyKnockback = value);

		addFloatField(general, "Knockback Modifier Blocks", "Should TNT break blocks",
				ConfigManager.config.knockbackModifier,
				value -> ConfigManager.config.knockbackModifier = value, 0.0f, 100.0f);

		addBoolean(general, "Placer Immunity", "Should the placer be given immunity from TNT damage",
				ConfigManager.config.placerImmunity,
				value -> ConfigManager.config.placerImmunity = value);

		return builder.build();
	}

	private void addBoolean(ConfigCategory category, String label, String tooltip, boolean currentValue, Consumer<Boolean> onSave) {
		category.addEntry(
				ConfigBuilder.create().entryBuilder().startBooleanToggle(Text.of(label), currentValue)
						.setTooltip(Text.of(tooltip))
						.setDefaultValue(currentValue)
						.setSaveConsumer(onSave)
						.build()
		);
	}

	private void addIntField(ConfigCategory category, String label, String tooltip, int currentValue, Consumer<Integer> onSave, int min, int max) {
		category.addEntry(
				ConfigBuilder.create().entryBuilder().startIntField(Text.of(label), currentValue)
						.setTooltip(Text.of(tooltip))
						.setDefaultValue(currentValue)
						.setSaveConsumer(newValue -> {
							if (newValue < min) newValue = min;
							if (newValue > max) newValue = max;
							onSave.accept(newValue);
							ConfigManager.saveConfig();
						})
						.build()
		);
	}

	private void addFloatField(ConfigCategory category, String label, String tooltip, float currentValue, Consumer<Float> onSave, float min, float max) {
		category.addEntry(
				ConfigBuilder.create().entryBuilder().startFloatField(Text.of(label), currentValue)
						.setTooltip(Text.of(tooltip))
						.setDefaultValue(currentValue)
						.setSaveConsumer(newValue -> {
							if (newValue < min) newValue = min;
							if (newValue > max) newValue = max;
							onSave.accept(newValue);
							ConfigManager.saveConfig();
						})
						.build()
		);
	}
}
