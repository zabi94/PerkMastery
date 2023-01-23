package dev.zabi94.perkmastery.entity.player;

import java.util.List;

import dev.zabi94.perkmastery.perks.PerkClass;
import dev.zabi94.perkmastery.perks.PerkLevel;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerPerkData {

	public List<PerkClass> getPerkClasses();
	
	public List<PerkLevel> getPerkLevels();
	
	public boolean isClassPurchased(PerkClass perkClass);
	
	public boolean isLevelUnlocked(PerkLevel perkLevel);
	
	public void unlockClass(PerkClass perkClass);

	public boolean isLevelPurchaseable(PerkLevel perkLevel);
	
	public boolean isLevelPurchased(PerkLevel perkLevel);

	public void purchaseLevel(PerkLevel perkLevel);
	
	public void setLevelStatus(PerkLevel perkLevel, boolean active);
	
	public boolean getLevelStatus(PerkLevel perkLevel);
	
	public static PlayerPerkData of(PlayerEntity p) {
		return (PlayerPerkData) p;
	}
	
	public void reset();
	
}
