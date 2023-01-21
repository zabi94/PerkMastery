package dev.zabi94.perkmastery.client;

import dev.zabi94.perkmastery.client.screen.LevellingScreen;
import dev.zabi94.perkmastery.registries.ScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class PerkMasteryClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HandledScreens.register(ScreenHandlers.LEVELLING_SCREEN_HANDLER, LevellingScreen::new);
	}

}
