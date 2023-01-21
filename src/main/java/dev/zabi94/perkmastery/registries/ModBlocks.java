package dev.zabi94.perkmastery.registries;

import dev.zabi94.perkmastery.blocks.LevellingShrine;
import dev.zabi94.perkmastery.utils.LibMod;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {

	public static Block levellingShrine = new LevellingShrine(Settings.of(Material.STONE).strength(3f, 3f).requiresTool());
	
	public static void init() {
		
		Registry.register(Registries.BLOCK, LibMod.id("levelling_shrine"), levellingShrine);
		
	}
	
}
