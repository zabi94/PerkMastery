package dev.zabi94.perkmastery.screen;

import java.lang.ref.WeakReference;

import dev.zabi94.perkmastery.registries.ScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class LevellingScreenHandler extends ScreenHandler {
	
	@SuppressWarnings("unused")
	private WeakReference<PlayerEntity> player;
	
	public LevellingScreenHandler(int syncId, PlayerEntity entity) {
		super(ScreenHandlers.LEVELLING_SCREEN_HANDLER, syncId);
		this.player = new WeakReference<PlayerEntity>(entity);
	}

	@Override
	public ItemStack quickMove(PlayerEntity var1, int var2) {
		return null;
	}

	@Override
	public boolean canUse(PlayerEntity var1) {
		return true;
	}
	
	@Override
	public ScreenHandlerType<?> getType() {
		return ScreenHandlers.LEVELLING_SCREEN_HANDLER;
	}
	
}
