package dev.zabi94.perkmastery.mixin;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import dev.zabi94.perkmastery.entity.player.PlayerPerkData;
import dev.zabi94.perkmastery.perks.PerkClass;
import dev.zabi94.perkmastery.perks.PerkLevel;
import dev.zabi94.perkmastery.perks.PlayerClasses;
import dev.zabi94.perkmastery.perks.PlayerLevels;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityExtras extends LivingEntity implements PlayerPerkData {
	
	private final List<PerkClass> perkClasses = Lists.newArrayList();
	private final Map<PerkLevel, Boolean> perkLevels = Maps.newHashMap();

	protected PlayerEntityExtras(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(
			method = "readCustomDataFromNbt",
			at = @At("RETURN")
		)
	public void perkmastery_readNbt(NbtCompound nbt, CallbackInfo cbinfo) {
		NbtCompound classes = nbt.getCompound("perkmastery_classes");
		for (String classId: classes.getKeys()) {
			PerkClass pc = PlayerClasses.CLASSES_REGISTRY.get(new Identifier(classId));
			if (pc != null) {
				perkClasses.add(pc);
			}
		}
		
		NbtCompound levels = nbt.getCompound("perkmastery_levels");
		for (String levelId: levels.getKeys()) {
			PerkLevel pl = PlayerLevels.LEVELS_REGISTRY.get(new Identifier(levelId));
			if (pl != null) {
				boolean active = levels.getBoolean(levelId);
				perkLevels.put(pl, active);
			}
		}
	}
	
	@Inject(
			method = "writeCustomDataToNbt",
			at = @At("RETURN")
		)
	public void perkmastery_writeNbt(NbtCompound nbt, CallbackInfo cbinfo) {
		NbtCompound classes = new NbtCompound();
		for (PerkClass pc: perkClasses) {
			classes.putBoolean(pc.getID().toString(), true);
		}
		nbt.put("perkmastery_classes", classes);
		
		NbtCompound levels = new NbtCompound();
		for (Entry<PerkLevel, Boolean> pl: perkLevels.entrySet()) {
			levels.putBoolean(pl.getKey().getID().toString(), pl.getValue());
		}
		nbt.put("perkmastery_levels", levels);
	}

	@Override
	public List<PerkClass> getPerkClasses() {
		return ImmutableList.copyOf(perkClasses);
	}

	@Override
	public List<PerkLevel> getPerkLevels() {
		return ImmutableList.copyOf(perkLevels.keySet());
	}

	@Override
	public boolean isClassPurchased(PerkClass perkClass) {
		return perkClasses.contains(perkClass);
	}

	@Override
	public boolean isLevelUnlocked(PerkLevel perkLevel) {
		return isClassPurchased(perkLevel.getPerkClass()) && isLevelPurchaseable(perkLevel);
	}

	@Override
	public void unlockClass(PerkClass perkClass) {
		perkClasses.add(perkClass);
	}

	@Override
	public void setLevelStatus(PerkLevel perkLevel, boolean active) {
		perkLevels.put(perkLevel, active);
	}
	
	@Override
	public boolean getLevelStatus(PerkLevel perkLevel) {
		Boolean b = perkLevels.get(perkLevel);
		return b != null && b == true;
	}
	
	@Override
	public void purchaseLevel(PerkLevel perkLevel) {
		perkLevels.put(perkLevel, false);
	}
	
	@Override
	public boolean isLevelPurchased(PerkLevel perkLevel) {
		return perkLevels.get(perkLevel) != null;
	}
	
	@Override
	public boolean isLevelPurchaseable(PerkLevel perkLevel) {
		return true;
	}
	
	@Override
	public void reset() {
		perkClasses.clear();
		perkLevels.clear();
	}

}
