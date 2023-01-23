package dev.zabi94.perkmastery.client.screen;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import dev.zabi94.perkmastery.entity.player.PlayerPerkData;
import dev.zabi94.perkmastery.perks.PerkClass;
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

public class ClassPurchaseButton implements Element, Drawable, Selectable, Hideable, TooltipProvider {
	
	public static final int BACKGROUND_TEXTURE_SIZE = 26;
	public static final int ICON_TEXTURE_SIZE = 18;
	
	private static final float TEXTURE_MIN_V = 202;
	private static final float TEXTURE_U_UNAVAILABLE = 0;
	private static final float TEXTURE_U_AVAILABLE = 26;
	private static final float TEXTURE_U_HOVERED = 52;
	
	private final int x, y;
	private boolean hidden = true;
	private PerkClass perkClass;
			
	public ClassPurchaseButton() {
		
		this.perkClass = null;
		
		MinecraftClient mc = MinecraftClient.getInstance();
		int screenWidth = mc.getWindow().getScaledWidth();
		int screenHeight = mc.getWindow().getScaledHeight();
		
		this.x = (- BACKGROUND_TEXTURE_SIZE/2) + (screenWidth / 2);
		this.y = (- BACKGROUND_TEXTURE_SIZE/2) + (screenHeight / 2);
	}

	@SuppressWarnings("resource")
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		PlayerEntity p = MinecraftClient.getInstance().player;
		
		if (hidden) return;
		
        RenderSystem.setShaderTexture(0, LibMod.id("textures/gui/levelling/frame.png"));
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		float texture_u = 0;
		
		if (perkClass.isLocked(p)) {
			texture_u = TEXTURE_U_UNAVAILABLE;
		} else {
			if (isMouseOver(mouseX, mouseY)) {
				texture_u = TEXTURE_U_HOVERED;
			} else {
				texture_u = TEXTURE_U_AVAILABLE;
			}
		}
		
		DrawableHelper.drawTexture(matrices, x, y, texture_u, TEXTURE_MIN_V, BACKGROUND_TEXTURE_SIZE, BACKGROUND_TEXTURE_SIZE, 256, 256);
		
		String textureName = String.format("textures/perkmastery/classes/%s.png", perkClass.getID().getPath());
		
		RenderSystem.setShaderTexture(0, Identifier.of(perkClass.getID().getNamespace(), textureName));
		DrawableHelper.drawTexture(matrices, x + 4, y + 4, 0, 0, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE, ICON_TEXTURE_SIZE);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		
		MinecraftClient mc = MinecraftClient.getInstance();
		if (isMouseOver(mouseX, mouseY) && !hidden && !perkClass.isLocked(mc.player)) {
			if (button == 0) {
				PlayerPerkData.of(mc.player).unlockClass(perkClass);
				this.setVisible(false);
				return true;
			}
		}
		
		return false;
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
				Text.translatable("perkmastery.unlock_class.message").formatted(Formatting.GOLD).asOrderedText(),
				Text.literal("").asOrderedText(),
				Text.translatable("perkmastery.unlock_class.cost", "3").formatted(Formatting.GREEN).asOrderedText()
			);
	}

	@Override
	public void setVisible(boolean visible) {
		this.hidden = !visible;
	}

	public void setPerkClass(PerkClass perkClass) {
		this.perkClass = perkClass;
		if (perkClass.isPurchased(MinecraftClient.getInstance().player)) {
			this.setVisible(false);
		}
	}
}
