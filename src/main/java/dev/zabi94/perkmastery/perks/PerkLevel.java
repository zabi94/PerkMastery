package dev.zabi94.perkmastery.perks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class PerkLevel {

	private final PerkClass perkClass;
	
	public PerkLevel(PerkClass perkClass) {
		this.perkClass = perkClass;
		perkClass.addLevel(this);
	}
	
	public PerkLevel(Identifier perkClassId) {
		this.perkClass = PlayerClasses.CLASSES_REGISTRY.get(perkClassId);
		
		if (this.perkClass == null) {
			throw new IllegalStateException("Can't find player class for ID "+perkClassId.toString());
		}
		
		perkClass.addLevel(this);
	}
	
	public PerkClass getPerkClass() {
		return perkClass;
	}

	public boolean isActive(PlayerEntity player) {
		return false;
	}

	public boolean isUnlocked(PlayerEntity player) {
		return false;
	}

	public Identifier getID() {
		return PlayerLevels.LEVELS_REGISTRY.getId(this);
	}
	
}
