package dev.zabi94.perkmastery.client.screen;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import dev.zabi94.perkmastery.entity.player.PlayerPerkData;
import dev.zabi94.perkmastery.perks.PerkClass;
import dev.zabi94.perkmastery.perks.PerkLevel;
import dev.zabi94.perkmastery.utils.LibMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class LevelButton implements Element, Drawable, Selectable, Hideable, TooltipProvider {
	
	public static final int TEXTURE_SIZE = 22;
	public static final int ICON_TEXTURE_SIZE = 18;
	
	private static final float TEXTURE_MIN_V = 230;
	private static final float TEXTURE_U_UNAVAILABLE = 2;
	private static final float TEXTURE_U_AVAILABLE = 28;
	private static final float TEXTURE_U_HOVERED = 54;
	private static final float TEXTURE_U_DISABLED = 80;
	private static final float TEXTURE_U_ENABLED = 106;
	
	private final int level, x, y;
	private boolean hidden = true;
	private PerkClass perkClass;
	
	public LevelButton(int level) {
		MinecraftClient mc = MinecraftClient.getInstance();
		int screenWidth = mc.getWindow().getScaledWidth();
		int screenHeight = mc.getWindow().getScaledHeight();
		this.x = ((int) (50 * Math.cos(level/3f*Math.PI)) - TEXTURE_SIZE/2) + (screenWidth / 2);
		this.y = ((int) (50 * Math.sin(level/3f*Math.PI)) - TEXTURE_SIZE/2) + (screenHeight / 2);
		this.level = level;
	}

	@SuppressWarnings("resource")
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (hidden) return;
        RenderSystem.setShaderTexture(0, LibMod.id("textures/gui/levelling/frame.png"));
		
		PlayerEntity player = MinecraftClient.getInstance().player;
        
        LevelButtonState state = getState(player);
        
        if (!isMouseOver(mouseX, mouseY) || state == LevelButtonState.AVAILABLE || state == LevelButtonState.UNAVAILABLE) {
    		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
        	RenderSystem.setShaderColor(0.9f, 0.9f, 0.9f, 1.0f);
        }
		
		float texture_u = 0;
		
		switch (state) {
			case AVAILABLE:
				if (isMouseOver(mouseX, mouseY)) {
					texture_u = TEXTURE_U_HOVERED;
				} else {
					texture_u = TEXTURE_U_AVAILABLE;
				}
				break;
			case DISABLED:
				texture_u = TEXTURE_U_DISABLED;
				break;
			case ENABLED:
				texture_u = TEXTURE_U_ENABLED;
				break;
			case UNAVAILABLE:
			default:
				texture_u = TEXTURE_U_UNAVAILABLE;
				break;
		}
		
		DrawableHelper.drawTexture(matrices, x, y, texture_u, TEXTURE_MIN_V, TEXTURE_SIZE, TEXTURE_SIZE, 256, 256);
		
		PerkLevel pl = this.getLevel();

		if (pl != null) {
			String textureName = String.format("textures/perkmastery/levels/%s.png", pl.getID().getPath());
			
			RenderSystem.setShaderTexture(0, Identifier.of(pl.getID().getNamespace(), textureName));
			DrawableHelper.drawTexture(matrices, x + 2, y + 2, 0, 0, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if (isMouseOver(mouseX, mouseY) && !hidden && getState(mc.player) != LevelButtonState.UNAVAILABLE) {
			
			PlayerPerkData ppd = PlayerPerkData.of(mc.player);
			
			if (button == 2 && getState(mc.player) == LevelButtonState.AVAILABLE) {
				ppd.purchaseLevel(this.getLevel());
				return true;
			}
			
			if (button == 0) {
				if (getState(mc.player) == LevelButtonState.ENABLED) {
					ppd.setLevelStatus(getLevel(), false);
				} else {
					ppd.setLevelStatus(getLevel(), true);
				}
				return true;
			}
			
		}
		return false;
	}
	
	private LevelButtonState getState(PlayerEntity p) {
		PerkLevel perk = this.getLevel();
		
		if (perk == null || !perk.isUnlocked(p)) {
			return LevelButtonState.UNAVAILABLE;
		}
		
		if (!perk.isPurchased(p)) {
			return LevelButtonState.AVAILABLE;
		}
		
		if (perk.isActive(p)) {
			return LevelButtonState.ENABLED;
		} else {
			return LevelButtonState.DISABLED;
		}
	}
	
	public static enum LevelButtonState {
		UNAVAILABLE, AVAILABLE, ENABLED, DISABLED 
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder var1) {
		
	}

	@Override
	public SelectionType getType() {
		return SelectionType.NONE;
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return !hidden && mouseX > x && mouseX < x + TEXTURE_SIZE && mouseY > y && mouseY < y + TEXTURE_SIZE;
	}

	@Override
	public void toggleHide() {
		this.hidden = !this.hidden;
	}
	
	public void setPerkClass(PerkClass perkClass) {
		this.perkClass = perkClass;
	}
	
	public PerkLevel getLevel() {
		if (this.perkClass == null) {
			return null;
		}
		
		return this.perkClass.getLevel(level);
	}

	@Override
	public List<OrderedText> getTooltip() {
		PerkLevel pl = this.getLevel();
		if (pl == null) return Lists.newArrayList();
		return Lists.newArrayList(
				pl.getText().formatted(Formatting.AQUA).asOrderedText(),
				Text.literal("").asOrderedText(),
				pl.getDescription().formatted(Formatting.DARK_GRAY).asOrderedText()
			);
	}

	@Override
	public void setVisible(boolean visible) {
		this.hidden = !visible;
	}

}
