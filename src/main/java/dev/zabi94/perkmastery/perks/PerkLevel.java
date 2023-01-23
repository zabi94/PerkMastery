package dev.zabi94.perkmastery.perks;

import dev.zabi94.perkmastery.entity.player.PlayerPerkData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
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
		return PlayerPerkData.of(player).getLevelStatus(this);
	}

	public boolean isUnlocked(PlayerEntity player) {
		return PlayerPerkData.of(player).isLevelUnlocked(this);
	}
	
	public boolean isPurchased(PlayerEntity player) {
		return PlayerPerkData.of(player).isLevelPurchased(this);
	}

	public Identifier getID() {
		return PlayerLevels.LEVELS_REGISTRY.getId(this);
	}

	public MutableText getText() {
		return Text.translatable(this.getID().toTranslationKey("perklevel.title"));
	}

	public MutableText getDescription() {
		return Text.translatable(this.getID().toTranslationKey("perklevel.description"));
	}
	
}
