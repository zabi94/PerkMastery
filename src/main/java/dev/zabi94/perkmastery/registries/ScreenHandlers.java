package dev.zabi94.perkmastery.registries;

import dev.zabi94.perkmastery.screen.LevellingScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlers {

	public static final ScreenHandlerType<LevellingScreenHandler> LEVELLING_SCREEN_HANDLER = new ScreenHandlerType<LevellingScreenHandler>((syncId, playerInventory) -> new LevellingScreenHandler(syncId, playerInventory.player));

	public static void init() {
		//No-op
	}
	
}
