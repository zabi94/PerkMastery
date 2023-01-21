package dev.zabi94.perkmastery.registries;

import dev.zabi94.perkmastery.utils.LibMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
	
	public static ItemGroup perkMasteryGroup;
	
	public static Item levelling_shrine = new BlockItem(ModBlocks.levellingShrine, new FabricItemSettings().rarity(Rarity.RARE));
	
	public static void init() {
		
		Registry.register(Registries.ITEM, LibMod.id("levelling_shrine"), levelling_shrine);
		
		perkMasteryGroup = FabricItemGroup.builder(new Identifier(LibMod.MOD_ID, "all"))
				.displayName(Text.translatable("itemGroup.perkmastery.all"))
				.icon(() -> new ItemStack(levelling_shrine))
				.entries(ModItems::getGroupItems)
				.build();
		
	}
	
	
	private static void getGroupItems(FeatureSet features, ItemGroup.Entries entries, boolean enabled) {
		entries.add(levelling_shrine);
	}

}
