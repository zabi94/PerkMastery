package dev.zabi94.perkmastery.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import dev.zabi94.perkmastery.utils.LibMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;

public class LevelButton implements Element, Drawable, Selectable, Hideable {
	
	public static final int TEXTURE_SIZE = 22;
	
	private static final float TEXTURE_MIN_V = 230;
	private static final float TEXTURE_U_UNAVAILABLE = 2;
	private static final float TEXTURE_U_AVAILABLE = 28;
	private static final float TEXTURE_U_HOVERED = 54;
	private static final float TEXTURE_U_DISABLED = 80;
	private static final float TEXTURE_U_ENABLED = 106;
	
	private final int level, x, y;
	private LevelButtonState state = LevelButtonState.UNAVAILABLE;
	private boolean hidden = true;
	
	public LevelButton(int level) {
		MinecraftClient mc = MinecraftClient.getInstance();
		int screenWidth = mc.getWindow().getScaledWidth();
		int screenHeight = mc.getWindow().getScaledHeight();
		this.x = ((int) (50 * Math.cos(level/3f*Math.PI)) - TEXTURE_SIZE/2) + (screenWidth / 2);
		this.y = ((int) (50 * Math.sin(level/3f*Math.PI)) - TEXTURE_SIZE/2) + (screenHeight / 2);
		this.level = level;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (hidden) return;
        RenderSystem.setShaderTexture(0, LibMod.id("textures/gui/levelling/frame.png"));
        
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
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (isMouseOver(mouseX, mouseY) && !hidden) {
			state = LevelButtonState.values()[(state.ordinal() + 1) % LevelButtonState.values().length];
		}
		return false;
	}
	
	public void setState(LevelButtonState state) {
		this.state = state;
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

}
