package dev.zabi94.perkmastery.perks;

import dev.zabi94.perkmastery.utils.LibMod;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Formatting;

public class PlayerClasses {
	
	public static final SimpleRegistry<PerkClass> CLASSES_REGISTRY = FabricRegistryBuilder
												.createSimple(PerkClass.class, LibMod.id("player_classes"))
												.attribute(RegistryAttribute.SYNCED)
												.buildAndRegister();
	
	public static final PerkClass ENCHANTER = new PerkClass(Formatting.DARK_PURPLE);	
	public static final PerkClass WARRIOR = new PerkClass(Formatting.GOLD);	
	public static final PerkClass ROGUE = new PerkClass(Formatting.DARK_GRAY);	
	public static final PerkClass EXPLORER = new PerkClass(Formatting.DARK_GREEN);	
	public static final PerkClass BUILDER = new PerkClass(Formatting.BLUE);
	public static final PerkClass MINER = new PerkClass(Formatting.AQUA);
//	public static final PerkClass TEST = new PerkClass(Formatting.RED);
//	public static final PerkClass TEST1 = new PerkClass(Formatting.RED);
//	public static final PerkClass TEST2 = new PerkClass(Formatting.RED);
//	public static final PerkClass TEST3 = new PerkClass(Formatting.RED);
//	public static final PerkClass TEST4 = new PerkClass(Formatting.RED);
	
	public static void init() {
		Registry.register(CLASSES_REGISTRY, LibMod.id("enchanter"), ENCHANTER);
		Registry.register(CLASSES_REGISTRY, LibMod.id("warrior"), WARRIOR);
		Registry.register(CLASSES_REGISTRY, LibMod.id("rogue"), ROGUE);
		Registry.register(CLASSES_REGISTRY, LibMod.id("explorer"), EXPLORER);
		Registry.register(CLASSES_REGISTRY, LibMod.id("builder"), BUILDER);
		Registry.register(CLASSES_REGISTRY, LibMod.id("miner"), MINER);
//		Registry.register(CLASSES_REGISTRY, LibMod.id("test"), TEST);
//		Registry.register(CLASSES_REGISTRY, LibMod.id("test1"), TEST1);
//		Registry.register(CLASSES_REGISTRY, LibMod.id("test2"), TEST2);
//		Registry.register(CLASSES_REGISTRY, LibMod.id("test3"), TEST3);
//		Registry.register(CLASSES_REGISTRY, LibMod.id("test4"), TEST4);
	}
	
}
