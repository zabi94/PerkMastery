package dev.zabi94.perkmastery;

import dev.zabi94.perkmastery.perks.PlayerClasses;
import dev.zabi94.perkmastery.perks.PlayerLevels;
import dev.zabi94.perkmastery.registries.ModBlocks;
import dev.zabi94.perkmastery.registries.ModItems;
import dev.zabi94.perkmastery.registries.ScreenHandlers;
import net.fabricmc.api.ModInitializer;

public class PerkMastery implements ModInitializer {

	@Override
	public void onInitialize() {
		
		PlayerClasses.init();
		PlayerLevels.init();
		ModBlocks.init();
		ModItems.init();
		ScreenHandlers.init();
		
	}

}
