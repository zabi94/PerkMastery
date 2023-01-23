package dev.zabi94.perkmastery.perks;

import dev.zabi94.perkmastery.utils.LibMod;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;

public class PlayerLevels {

	
	public static final SimpleRegistry<PerkLevel> LEVELS_REGISTRY = FabricRegistryBuilder
												.createSimple(PerkLevel.class, LibMod.id("player_levels"))
												.attribute(RegistryAttribute.SYNCED)
												.buildAndRegister();
	
	public static void init() {
		Registry.register(LEVELS_REGISTRY, LibMod.id("disenchanting"), new PerkLevel(PlayerClasses.ENCHANTER));
		Registry.register(LEVELS_REGISTRY, LibMod.id("steady_hand"), new PerkLevel(PlayerClasses.ROGUE));
	}
	
}
