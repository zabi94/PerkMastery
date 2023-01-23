package dev.zabi94.perkmastery.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import dev.zabi94.perkmastery.entity.player.PlayerPerkData;
import dev.zabi94.perkmastery.perks.PerkClass;
import dev.zabi94.perkmastery.perks.PlayerClasses;
import dev.zabi94.perkmastery.screen.LevellingScreenHandler;
import dev.zabi94.perkmastery.utils.LibMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LevellingScreen extends HandledScreen<LevellingScreenHandler> {

	private static final Identifier FRAME_TEXTURE = LibMod.id("textures/gui/levelling/frame.png");
    private static final Identifier STATIC_BACKROUND_TEXTURE = LibMod.id("textures/gui/levelling/background.png");
    private static final Identifier PARALLAX_LOWER_TEXTURE = LibMod.id("textures/gui/levelling/parallax_lower.png");
    private static final Identifier PARALLAX_HIGHER_TEXTURE = LibMod.id("textures/gui/levelling/parallax_higher.png");
    
    private float currentAnimationPositionX = 0;
    private float currentAnimationPositionY = 0;
    
    public LevellingScreen(LevellingScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}
    
    @Override
    protected void init() {
    	super.init();
    	this.backgroundWidth = 256;
    	this.backgroundHeight = 186;
		
    	for (int i = 0; i < 6; i++) {
    		this.addDrawableChild(new LevelButton(i));
    	}
    	
		this.addDrawableChild(new ClassPurchaseButton());
		
    	int j = 0;
    	for (PerkClass perk: PlayerClasses.CLASSES_REGISTRY) {
    		this.addDrawableChild(new ClassNavigateButton(perk, j++));
    	}
    }

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		this.renderBackground(matrices);

		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		
		MinecraftClient mc = MinecraftClient.getInstance();
		int screenWidth = mc.getWindow().getScaledWidth();
		int screenHeight = mc.getWindow().getScaledHeight();
		
		float mousePercentX = (mouseX / ((float) screenWidth)) - 0.5f;
		float mousePercentY = (mouseY / ((float) screenHeight)) - 0.5f;
		
		currentAnimationPositionX += delta/300;
		currentAnimationPositionY += delta/300;
		
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        
        RenderSystem.setShaderTexture(0, STATIC_BACKROUND_TEXTURE);
        this.drawTexture(matrices, i, j, (int) (10 * mousePercentX), 34 + (int) (12 * mousePercentY), this.backgroundWidth, this.backgroundHeight);

        RenderSystem.setShaderTexture(0, PARALLAX_HIGHER_TEXTURE);
        this.drawTexture(matrices, i+1, j+1, (int) (300 * currentAnimationPositionX), 34 + (int) (160*currentAnimationPositionY), this.backgroundWidth-2, this.backgroundHeight-2);
        
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.9f);
        RenderSystem.setShaderTexture(0, PARALLAX_LOWER_TEXTURE);
        this.drawTexture(matrices, i+1, j+1, 4 + (int) (mousePercentX * -16), 34 + (int) (mousePercentY * -16), this.backgroundWidth-2, this.backgroundHeight-2);

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, FRAME_TEXTURE);
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        
        for (Element e: this.children()) {
			if (e instanceof TooltipProvider ttp && e.isMouseOver(mouseX, mouseY)) {
				this.setTooltip(ttp.getTooltip());
			}
		}
	}
	
	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		
		boolean goHomepage = false;
		
		if (button == 1) {
			goHomepage = true;
		}

		if (button == 2 && Screen.hasControlDown()) {
			PlayerPerkData.of(MinecraftClient.getInstance().player).reset();
			goHomepage = true;
		}
		
		if (goHomepage) {
			for (Element e:this.children()) {
				if (e instanceof ClassNavigateButton cb) {
					cb.setVisible(true);
				} else if (e instanceof LevelButton lb) {
					lb.setVisible(false);
				} else if (e instanceof ClassPurchaseButton cp) {
					cp.setVisible(false);
				}
			}
			
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, button);
	}

	public void setPerkClass(PerkClass perkClass) {
		for (Element e:this.children()) {
			if (e instanceof LevelButton lb) {
				lb.setPerkClass(perkClass);
				if (lb.getLevel() != null) {
					lb.setVisible(true);
				} else {
					lb.setVisible(false);
				}
			} else if (e instanceof ClassNavigateButton cb) {
				cb.setVisible(false);
			} else if (e instanceof ClassPurchaseButton cp) {
				cp.setPerkClass(perkClass);
				cp.setVisible(!perkClass.isPurchased(MinecraftClient.getInstance().player));
			}
		}
	}
	
}
