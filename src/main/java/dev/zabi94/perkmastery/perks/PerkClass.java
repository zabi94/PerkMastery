package dev.zabi94.perkmastery.perks;

import java.util.List;

import com.google.common.collect.Lists;

import dev.zabi94.perkmastery.entity.player.PlayerPerkData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PerkClass {

	private final List<PerkLevel> perks = Lists.newArrayList();
	private final Formatting[] formatting;
	
	public PerkClass(Formatting... formatting) {
		this.formatting = formatting;
	}
	
	public MutableText getText() {
		return Text.translatable(this.getID().toTranslationKey("perkclass.title")).formatted(formatting);
	}

	public MutableText getDescription() {
		return Text.translatable(this.getID().toTranslationKey("perkclass.description"));
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
		return PlayerPerkData.of(player).getPerkClasses().size() > 0;
	}
	
	public boolean isPurchased(PlayerEntity player) {
		return PlayerPerkData.of(player).isClassPurchased(this);
	}
	
	public Identifier getID() {
		return PlayerClasses.CLASSES_REGISTRY.getId(this);
	}
	
}
