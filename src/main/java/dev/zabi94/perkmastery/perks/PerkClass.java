package dev.zabi94.perkmastery.perks;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PerkClass {

	private final List<PerkLevel> perks = Lists.newArrayList();
	private final Formatting[] formatting;
	
	public PerkClass(Formatting... formatting) {
		this.formatting = formatting;
	}
	
	public Text getText() {
		return Text.translatable(this.getID().toTranslationKey("perkclass")).formatted(formatting);
	}
	
	PerkClass addLevel(PerkLevel level) {
		
		if (!level.getPerkClass().equals(this)) {
			throw new IllegalStateException("Perk level "+level.getID().toString()+" is already bound to class " + level.getPerkClass().getID().toString()+", can't bind it to "+this.getID().toString());
		}
		
		perks.add(level);
		return this;
	}
	
	public PerkLevel getLevel(int level) {
		return perks.size() <= level ? null : perks.get(level);
	}
	
	public boolean isLocked(PlayerEntity player) {
		return false;
	}
	
	public boolean isPurchased(PlayerEntity player) {
		return false;
	}
	
	public Identifier getID() {
		return PlayerClasses.CLASSES_REGISTRY.getId(this);
	}
	
}
