package dev.zabi94.perkmastery.client.screen;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import dev.zabi94.perkmastery.entity.player.PlayerPerkData;
import dev.zabi94.perkmastery.perks.PerkClass;
import dev.zabi94.perkmastery.perks.PlayerClasses;
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

public class ClassNavigateButton implements Element, Drawable, Selectable, Hideable, TooltipProvider {
	
	public static final int BACKGROUND_TEXTURE_SIZE = 26;
	public static final int ICON_TEXTURE_SIZE = 18;
	
	private static final float TEXTURE_MIN_V = 202;
	private static final float TEXTURE_U_UNAVAILABLE = 0;
	private static final float TEXTURE_U_AVAILABLE = 26;
	private static final float TEXTURE_U_HOVERED = 52;
	private static final float TEXTURE_U_UNLOCKED = 78;
	
	private final int x, y;
	private boolean hidden = false;
	private final PerkClass perkClass;
			
	public ClassNavigateButton(PerkClass perkClass, int classOrder) {
		
		this.perkClass = perkClass;
		
		MinecraftClient mc = MinecraftClient.getInstance();
		int screenWidth = mc.getWindow().getScaledWidth();
		int screenHeight = mc.getWindow().getScaledHeight();
		
		double halfClasses = PlayerClasses.CLASSES_REGISTRY.size() / 2d;
		
		int tlX = (int) (55 * Math.cos(classOrder/halfClasses*Math.PI - Math.PI/2));
		int tlY = (int) (55 * Math.sin(classOrder/halfClasses*Math.PI - Math.PI/2));

		this.x = (tlX - BACKGROUND_TEXTURE_SIZE/2) + (screenWidth / 2);
		this.y = (tlY - BACKGROUND_TEXTURE_SIZE/2) + (screenHeight / 2);
	}

	@SuppressWarnings("resource")
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (hidden) return;
        RenderSystem.setShaderTexture(0, LibMod.id("textures/gui/levelling/frame.png"));
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		float texture_u = 0;
		
		PlayerEntity p = MinecraftClient.getInstance().player;
		
		switch (getState(p)) {
			case AVAILABLE:
				if (isMouseOver(mouseX, mouseY)) {
					texture_u = TEXTURE_U_HOVERED;
				} else {
					texture_u = TEXTURE_U_AVAILABLE;
				}
				break;
			case UNLOCKED:
				texture_u = TEXTURE_U_UNLOCKED;
				break;
			case UNAVAILABLE:
			default:
				texture_u = TEXTURE_U_UNAVAILABLE;
				break;
		}
		
		DrawableHelper.drawTexture(matrices, x, y, texture_u, TEXTURE_MIN_V, BACKGROUND_TEXTURE_SIZE, BACKGROUND_TEXTURE_SIZE, 256, 256);
		
		String textureName = String.format("textures/perkmastery/classes/%s.png", perkClass.getID().getPath());
		
		RenderSystem.setShaderTexture(0, Identifier.of(perkClass.getID().getNamespace(), textureName));
		DrawableHelper.drawTexture(matrices, x + 4, y + 4, 0, 0, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE);
	}
	
	private ClassButtonState getState(PlayerEntity p) {
		
		if (perkClass.isPurchased(p)) return ClassButtonState.UNLOCKED;
		
		if (!perkClass.isLocked(p)) return ClassButtonState.AVAILABLE;
				
		return ClassButtonState.UNAVAILABLE;
		
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		
		MinecraftClient mc = MinecraftClient.getInstance();
		if (isMouseOver(mouseX, mouseY) && !hidden) {
			if (mc.currentScreen instanceof LevellingScreen ls) {
				ls.setPerkClass(this.perkClass);
				return true;
			}
		}
		
		return false;
	}
	
	public static enum ClassButtonState {
		UNAVAILABLE, AVAILABLE, UNLOCKED 
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
		return !hidden && mouseX > x && mouseX < x + BACKGROUND_TEXTURE_SIZE && mouseY > y && mouseY < y + BACKGROUND_TEXTURE_SIZE;
	}

	@Override
	public void toggleHide() {
		this.hidden = !this.hidden;
	}

	@Override
	public List<OrderedText> getTooltip() {
		return Lists.newArrayList(
				this.perkClass.getText().asOrderedText(),
				Text.literal("").asOrderedText(),
				this.perkClass.getDescription().formatted(Formatting.DARK_GRAY).asOrderedText()
			);
	}

	@Override
	public void setVisible(boolean visible) {
		this.hidden = !visible;
	}

}
